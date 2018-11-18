package com.roguskip.roguskiwarehouse

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.roguskip.roguskiwarehouse.model.Product

class ChangeProductQuantityActivity : AppCompatActivity() {
    var product : Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product)

        this.product = intent.getSerializableExtra("product") as Product?


        val textView = findViewById<TextView>(R.id.textView)
        textView.text = this.product!!.productName
    }
}