package com.roguskip.roguskiwarehouse.model

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.roguskip.roguskiwarehouse.R
import com.roguskip.roguskiwarehouse.database.MyInternalStorage
import com.roguskip.roguskiwarehouse.database.MyInternalStorage.isServerReachable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.text.NumberFormat


class ProductViewListAdapter(private val activity: Activity,
                             productViewList: List<ProductView>,
                             val context: Context) : BaseAdapter() {

    private var productList = ArrayList<ProductView>()
    val client by lazy { ProductViewApiClient.create() }

    init {
        this.productList = productViewList as ArrayList<ProductView>
        this.refreshProducts()
    }

    override fun getCount(): Int {
        return this.productList.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("InflateParams", "ViewHolder", "SetTextI18n")
    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        val vi: View
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vi = inflater.inflate(R.layout.product, null)
        val manufacturerTitle = vi.findViewById<TextView>(R.id.manufacturerName)
        val productName = vi.findViewById<TextView>(R.id.productName)
        val currencyPrice = vi.findViewById<TextView>(R.id.currencyPrice)
        val quantity = vi.findViewById<TextView>(R.id.quantity)
        manufacturerTitle.text = this.productList[i].manufacturerName
        productName.text = this.productList[i].productName
        currencyPrice.text = NumberFormat.getCurrencyInstance().format(this.productList[i].price)
        quantity.text = "Quantity: " + this.productList[i].quantity.toString()
        return vi
    }

    fun refreshProducts() {
        if ( MyInternalStorage.isServerReachable(context)) {
            client.getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    MyInternalStorage.writeObject(context, "productList", result)

                    productList.clear()
                    productList.addAll(result)
                    notifyDataSetChanged()
                }, { error ->
                    Toast.makeText(context, "Refresh error: ${error.message}", Toast.LENGTH_LONG).show()
                    Log.e("ERRORS", error.message)
                })
        } else {
            val localProducts = MyInternalStorage.readObject(context, "productList")
            if (localProducts is ArrayList<*>) {
                productList.clear()
                productList.addAll(localProducts as ArrayList<ProductView>)
                notifyDataSetChanged()
            }
        }
    }



}