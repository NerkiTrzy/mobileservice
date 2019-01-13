package com.roguskip.roguskiwarehouse

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.roguskip.roguskiwarehouse.database.MyInternalStorage
import com.roguskip.roguskiwarehouse.model.Operation
import com.roguskip.roguskiwarehouse.model.OperationName
import com.roguskip.roguskiwarehouse.model.ProductApiClient
import com.roguskip.roguskiwarehouse.model.ProductView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ChangeProductQuantityActivity : AppCompatActivity() {
    var productView : ProductView? = null
    private val productClient by lazy { ProductApiClient.create() }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_product_quantity)

        this.productView = intent.getSerializableExtra("productView") as ProductView?


        findViewById<TextView>(R.id.manufacturerName).text = this.productView!!.manufacturerName
        findViewById<TextView>(R.id.productName).text = this.productView!!.productName
        findViewById<TextView>(R.id.currentPrice).text = this.productView!!.price.toString()
        findViewById<TextView>(R.id.currentQuantity).text = "Current Quantity: " + this.productView!!.quantity.toString()
        findViewById<TextView>(R.id.changingQuantity).text = "0"
    }

    fun decreaseQuantity(view: View) {
        val tmp: Int = findViewById<TextView>(R.id.changingQuantity).text.toString().toInt() - 1
        findViewById<TextView>(R.id.changingQuantity).text = tmp.toString()
    }

    fun increaseQuantity(view: View) {
        val tmp: Int = findViewById<TextView>(R.id.changingQuantity).text.toString().toInt() + 1
        findViewById<TextView>(R.id.changingQuantity).text = tmp.toString()
    }

    fun save(view: View) {
        if ( MyInternalStorage.isServerReachable(applicationContext)) {
            this.productClient
                .changeQuantity(
                    this.productView!!.productGUID,
                    findViewById<TextView>(R.id.changingQuantity).text.toString().toInt()
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    this.setResult(Activity.RESULT_OK, null)
                    this.finish()
                }, { error ->
                    Log.e("ERRORS", error.message)
                })
        } else {
            val localProducts = MyInternalStorage.readObject(applicationContext, "productList")
            val productView = ProductView(
                this.productView!!.manufacturerName,
                this.productView!!.productName,
                this.productView!!.manufacturerId,
                this.productView!!.price,
                this.productView!!.quantity + findViewById<TextView>(R.id.changingQuantity).text.toString().toInt(),
                this.productView!!.productId,
                this.productView!!.currency,
                this.productView!!.productGUID
            )


            for (i in 0 until (localProducts as ArrayList<ProductView>).size) {
                if (localProducts[i].productGUID == this.productView!!.productGUID) {
                    localProducts[i] = productView
                    break
                }
            }

            MyInternalStorage.writeObject(applicationContext, "productList", localProducts)

            val operationList: java.util.ArrayList<Operation> = try {
                MyInternalStorage.readObject(applicationContext, "operationList") as java.util.ArrayList<Operation>
            } catch (e: Exception) {
                java.util.ArrayList()
            }

            operationList.add(Operation(UUID.randomUUID().toString(), OperationName.UPDATE, productView, findViewById<TextView>(R.id.changingQuantity).text.toString().toInt()))

            MyInternalStorage.writeObject(applicationContext, "operationList", operationList)

            this.setResult(Activity.RESULT_OK, null)
            this.finish()
        }
    }

    fun goBack(view: View) {
        this.finish()
    }

    fun deleteProduct(view: View) {
        if ( MyInternalStorage.isServerReachable(applicationContext)) {
            this.productClient.deleteProduct(this.productView!!.productGUID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    this.setResult(Activity.RESULT_OK, null)
                    this.finish()
                }, { error ->
                    Log.e("ERRORS", error.message)
                })
        }   else {
                val localProducts = MyInternalStorage.readObject(applicationContext, "productList") as ArrayList<ProductView>

                localProducts.remove(this.productView!!)

                MyInternalStorage.writeObject(applicationContext, "productList", localProducts)

                val operationList: java.util.ArrayList<Operation> = try {
                    MyInternalStorage.readObject(applicationContext, "operationList") as java.util.ArrayList<Operation>
                } catch (e: Exception) {
                    java.util.ArrayList()
                }

                operationList.add(Operation(UUID.randomUUID().toString(), OperationName.DELETE, this.productView!!, 0))

                MyInternalStorage.writeObject(applicationContext, "operationList", operationList)


                this.setResult(Activity.RESULT_OK, null)
                this.finish()
            }
    }
}