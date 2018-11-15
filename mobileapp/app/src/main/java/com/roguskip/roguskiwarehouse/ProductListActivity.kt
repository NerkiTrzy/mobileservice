package com.roguskip.roguskiwarehouse

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.roguskip.roguskiwarehouse.model.Product
import com.roguskip.roguskiwarehouse.model.ProductListAdapter
import java.math.BigDecimal
import java.util.*

class ProductListActivity : AppCompatActivity() {

    var listView: ListView? = null
    var productList = ArrayList<Product>()
    var adapter: ProductListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_list)

        listView = findViewById<ListView>(R.id.productListView)
        adapter = ProductListAdapter(this, productList, this.applicationContext)
        (listView as ListView).adapter = adapter

        //prepareProductList()

//        (listView as ListView).onItemClickListener = AdapterView.OnItemClickListener{
//                adapterView,
//                view,
//                i,
//                l -> Toast.makeText(applicationContext, productList?.get(i)?.name,
//            Toast.LENGTH_SHORT).show()
//        }
    }

    private fun prepareProductList() {
        var product = Product("Samsung", "Galaxy", BigDecimal(23.23), 5)
        productList.add(product)
        product = Product("Apple", "S8", BigDecimal(2000.23), 5)
        productList.add(product)
        product = Product("Apple", "S8 PLUS", BigDecimal(2500.23), 3)
        productList.add(product)
        product = Product("Apple", "S8 PLUS", BigDecimal(2500.23), 3)
        productList.add(product)
        product = Product("Apple", "S7 PLUS", BigDecimal(2500.23), 3)
        productList.add(product)
        product = Product("Samsung", "J 5", BigDecimal(2500.23), 3)
        productList.add(product)

        adapter?.notifyDataSetChanged()
    }


}