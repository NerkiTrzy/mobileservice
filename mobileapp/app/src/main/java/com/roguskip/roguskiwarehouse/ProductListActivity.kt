package com.roguskip.roguskiwarehouse

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import com.roguskip.roguskiwarehouse.model.ProductView
import com.roguskip.roguskiwarehouse.model.ProductViewListAdapter
import java.util.*

class ProductListActivity : AppCompatActivity() {

    var listView: ListView? = null
    var productList = ArrayList<ProductView>()
    var adapterView: ProductViewListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_list)

        listView = findViewById(R.id.productListView)
        adapterView = ProductViewListAdapter(this, productList, this.applicationContext)
        (listView as ListView).adapter = adapterView

         (listView as ListView).setOnItemClickListener  {
             adapterView, view, position, id ->
             val intent = Intent(this, ChangeProductQuantityActivity::class.java)
             intent.putExtra("productView", productList.get(position))
             startActivityForResult(intent, 1)
         }


    }

    fun createProduct(view: View) {
        val intent = Intent(this, AddProductActivity::class.java)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val refresh = Intent(this, ProductListActivity::class.java)
            startActivity(refresh)
            this.finish()
        }
    }

}