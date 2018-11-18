package com.roguskip.roguskiwarehouse

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import com.roguskip.roguskiwarehouse.model.Product
import com.roguskip.roguskiwarehouse.model.ProductListAdapter
import java.util.*

class ProductListActivity : AppCompatActivity() {

    var listView: ListView? = null
    var productList = ArrayList<Product>()
    var adapter: ProductListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_list)

        listView = findViewById(R.id.productListView)
        adapter = ProductListAdapter(this, productList, this.applicationContext)
        (listView as ListView).adapter = adapter

         (listView as ListView).setOnItemClickListener  {
             adapterView, view, position, id ->
             val intent = Intent(this, ChangeProductQuantityActivity::class.java)
             intent.putExtra("product", productList.get(position))
             startActivity(intent)
         }


    }

    fun createProduct(view: View) {
        startActivity(Intent(this, AddProductActivity::class.java))
    }
}