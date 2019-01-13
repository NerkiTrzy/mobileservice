package com.roguskip.roguskiwarehouse.database

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import com.roguskip.roguskiwarehouse.model.Operation
import com.roguskip.roguskiwarehouse.model.ProductApiClient
import com.roguskip.roguskiwarehouse.model.ProductView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


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

        val operationList: java.util.ArrayList<Operation> = try {
            MyInternalStorage.readObject(context, "operationList") as java.util.ArrayList<Operation>
        } catch (e: Exception) {
            ArrayList()
        }

        productClient.doOperations(operationList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                MyInternalStorage.writeObject(context, "operationList", ArrayList<Operation>())
            }, { throwable ->
                Log.e("ERRORS", throwable.message)
            })
    }

    private fun isServerReachableCheck(context: Context) : Boolean {
        val connMan = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connMan.activeNetworkInfo
        if (netInfo != null && netInfo.isConnected) {
            return try {
                val urlServer = URL("http://10.0.2.2:8080")
                val urlConn = urlServer.openConnection() as HttpURLConnection
                urlConn.connectTimeout = 500
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