package com.roguskip.roguskiwarehouse

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.roguskip.roguskiwarehouse.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AddProductActivity : AppCompatActivity(){
    private val manufacturerClient by lazy { ManufacturerApiClient.create() }
    private val productClient by lazy { ProductApiClient.create() }


    private var manufacturerList: ArrayList<Manufacturer> = ArrayList()

    var spinner:Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product)

        this.setManufacturerSpinner()
    }

    private fun setManufacturerSpinner() {
        this.spinner = findViewById(R.id.manufacturersSpinner)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, manufacturerList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = adapter

        manufacturerClient.getManufacturers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                manufacturerList.clear()
                manufacturerList.addAll(result)
                adapter.notifyDataSetChanged()
            },{ error ->
                Log.e("ERRORS", error.message)
            })

        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                //Toast.makeText(this@AddProductActivity, manufacturerList[p2].name, LENGTH_LONG).show()
            }

        }
    }

    fun addProduct(view: View) {
        val product: Product = Product(

            findViewById<TextView>(R.id.productName).text.toString(),
            0,
            "PLN",
            findViewById<TextView>(R.id.price).text.toString().toBigDecimal(),
            0
        )
        val manufacturerId: Int = (this.spinner!!.selectedItem as Manufacturer).id


        productClient.addProduct(manufacturerId, product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.setResult(Activity.RESULT_OK, null)
                this.finish()
            }, { throwable ->
                Toast.makeText(this.applicationContext, "Add error: ${throwable.message}", Toast.LENGTH_LONG).show()
            })
    }

    fun goBack(view: View) {
        this.finish()
    }
}