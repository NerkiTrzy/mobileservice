package com.roguskip.roguskiwarehouse.database

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.roguskip.roguskiwarehouse.model.Product
import com.roguskip.roguskiwarehouse.model.ProductApiClient
import com.roguskip.roguskiwarehouse.model.ProductView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


object MyInternalStorage {

    private val productClient by lazy { ProductApiClient.create() }

    @Throws(IOException::class)
    fun writeObject(context: Context, key: String, `object`: Any) {
        val fos = context.openFileOutput(key, Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(`object`)
        oos.close()
        fos.close()
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    fun readObject(context: Context, key: String): Any {
        val fis = context.openFileInput(key)
        val ois = ObjectInputStream(fis)
        return ois.readObject()
    }

    fun isServerReachable(context: Context): Boolean {
        var isServerActive = false
        val thread = Thread(Runnable {
            try {
                isServerActive = MyInternalStorage.isServerReachableCheck(context)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        thread.start()
        thread.join()

        if (isServerActive) {
            makeSynchronization(context)
        }
        Thread.sleep(100)
        return isServerActive
    }

    fun makeSynchronization(context: Context) {
        val addingOperations: ArrayList<ProductView> = try {
            MyInternalStorage.readObject(context, "addingOperations") as ArrayList<ProductView>
        } catch (e: Exception) {
            ArrayList()
        }

        val updateOperations: HashMap<String, Int> = try {
            MyInternalStorage.readObject(context, "updateOperations") as HashMap<String, Int>
        } catch (e: Exception) {
            HashMap()
        }

        val deleteOperations: ArrayList<String> = try {
            MyInternalStorage.readObject(context, "deleteOperations") as ArrayList<String>
        } catch (e: Exception) {
            ArrayList()
        }

        addingOperations.forEach {
            productClient.addProduct(it.manufacturerId, Product(it.productName, it.productId, it.currency, it.price, it.quantity, it.productGUID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, { throwable ->
                    Toast.makeText(context , "Add error: ${throwable.message}", Toast.LENGTH_LONG).show()
                })
        }

        Thread.sleep(100)

        updateOperations.forEach {
            productClient.changeQuantity(it.key, it.value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, { throwable ->
                    Toast.makeText(context , "Add error: ${throwable.message}", Toast.LENGTH_LONG).show()
                })
        }

        Thread.sleep(100)
        deleteOperations.forEach {
            productClient.deleteProduct(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, { throwable ->
                    Toast.makeText(context , "Add error: ${throwable.message}", Toast.LENGTH_LONG).show()
                })
        }

        Thread.sleep(100)
        addingOperations.clear()
        MyInternalStorage.writeObject(context, "addingOperations", addingOperations)
        updateOperations.clear()
        MyInternalStorage.writeObject(context, "updateOperations", updateOperations)
        deleteOperations.clear()
        MyInternalStorage.writeObject(context, "deleteOperations", deleteOperations)
    }

    private fun isServerReachableCheck(context: Context) : Boolean {
        val connMan = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connMan.activeNetworkInfo
        if (netInfo != null && netInfo.isConnected) {
            return try {
                val urlServer = URL("http://10.0.2.2:8080")
                val urlConn = urlServer.openConnection() as HttpURLConnection
                urlConn.connectTimeout = 500 //<- 3Seconds Timeout
                urlConn.connect()
                urlConn.responseCode === 200
            } catch (e1: MalformedURLException) {
                false
            } catch (e: IOException) {
                false
            }

        }
        return false
    }
}