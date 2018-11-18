package com.roguskip.roguskiwarehouse

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast.LENGTH_LONG
import com.roguskip.roguskiwarehouse.model.Manufacturer
import com.roguskip.roguskiwarehouse.model.ManufacturerApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.stream.Collectors


class AddProductActivity : AppCompatActivity(), OnItemSelectedListener {
    val manufacturerClient by lazy { ManufacturerApiClient.create() }

    private var manufacturerList: ArrayList<Manufacturer> = ArrayList()

    var spinner:Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product)

        this.setManufacturerSpinner()
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        (parent!!.getChildAt(0) as TextView).setTextColor(Color.BLUE)
        (parent.getChildAt(0) as TextView).textSize = 5f
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private fun setManufacturerSpinner() {
        val spinner : Spinner = findViewById(R.id.manufacturersSpinner)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, manufacturerList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.adapter = adapter

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

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@AddProductActivity, manufacturerList[p2].name, LENGTH_LONG).show()
            }

        }
    }
}