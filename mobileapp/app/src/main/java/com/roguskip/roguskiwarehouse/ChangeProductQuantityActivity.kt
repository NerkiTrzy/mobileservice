package com.roguskip.roguskiwarehouse

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.roguskip.roguskiwarehouse.model.ProductApiClient
import com.roguskip.roguskiwarehouse.model.ProductView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
        this.productClient
            .changeQuantity(this.productView!!.productId,
                findViewById<TextView>(R.id.changingQuantity).text.toString().toInt())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.setResult(Activity.RESULT_OK, null)
                this.finish()
            },{ error ->
                Log.e("ERRORS", error.message)
            })
    }

    fun goBack(view: View) {
        this.finish()
    }

    fun deleteProduct(view: View) {
        this.productClient.deleteProduct(this.productView!!.productId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.setResult(Activity.RESULT_OK, null)
                this.finish()
            }, { error ->
                Log.e("ERRORS", error.message)
            })
    }
}