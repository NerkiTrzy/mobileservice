package com.roguskip.roguskiwarehouse

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.roguskip.roguskiwarehouse.database.MyInternalStorage
import com.roguskip.roguskiwarehouse.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.*


class AddProductActivity : AppCompatActivity(){
    private val manufacturerClient by lazy { ManufacturerApiClient.create() }
    private val warehouseClient by lazy { WarehouseApiClient.create() }
    private val productClient by lazy { ProductApiClient.create() }


    private var manufacturerList: ArrayList<Manufacturer> = ArrayList()
    private var warehousesList: ArrayList<Warehouse> = ArrayList()

    var spinner:Spinner? = null
    var warehouseSpinner:Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product)

        this.setManufacturerSpinner()
    }

    private fun setManufacturerSpinner() {
        this.spinner = findViewById(R.id.manufacturersSpinner)
        this.warehouseSpinner = findViewById(R.id.warehousesSpinner)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, manufacturerList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = adapter

        val warehouseAdapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_item, warehousesList)
        warehouseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        warehouseSpinner!!.adapter = warehouseAdapter

        if ( MyInternalStorage.isServerReachable(applicationContext)) {
            manufacturerClient.getManufacturers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    MyInternalStorage.writeObject(applicationContext, "manufacturerList", result)
                    manufacturerList.clear()
                    manufacturerList.addAll(result)
                    adapter.notifyDataSetChanged()
                }, { error ->
                    Log.e("ERRORS", error.message)
                })
        } else {
            val localManufacturers = MyInternalStorage.readObject(applicationContext, "manufacturerList")
            if (localManufacturers is ArrayList<*>) {
                manufacturerList.clear()
                manufacturerList.addAll(localManufacturers as ArrayList<Manufacturer>)
                adapter.notifyDataSetChanged()
            }
        }
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }

        if ( MyInternalStorage.isServerReachable(applicationContext)) {
            warehouseClient.getWarehouses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    MyInternalStorage.writeObject(applicationContext, "warehousesList", result)
                    warehousesList.clear()
                    warehousesList.addAll(result)
                    warehouseAdapter.notifyDataSetChanged()
                }, { error ->
                    Log.e("ERRORS", error.message)
                })
        } else {
            val localWarehouses = MyInternalStorage.readObject(applicationContext, "warehousesList")
            if (localWarehouses is ArrayList<*>) {
                warehousesList.clear()
                warehousesList.addAll(localWarehouses as ArrayList<Warehouse>)
                warehouseAdapter.notifyDataSetChanged()
            }
        }
        warehouseSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }
    }

    fun addProduct(view: View) {
        val warehouse: Warehouse = Warehouse((this.warehouseSpinner!!.selectedItem as Warehouse).id,
                                            (this.warehouseSpinner!!.selectedItem as Warehouse).name,
                                            (this.warehouseSpinner!!.selectedItem as Warehouse).extraPrice)

        val product: Product = Product(

            findViewById<TextView>(R.id.productName).text.toString(),
            0,
            "PLN",
            findViewById<TextView>(R.id.price).text.toString().toBigDecimal(),
            0,
            UUID.randomUUID().toString(),
            findViewById<TextView>(R.id.color).text.toString(),
            warehouse.id
        )
        val manufacturerId: Int = (this.spinner!!.selectedItem as Manufacturer).id
        val manufacturerName: String = (this.spinner!!.selectedItem as Manufacturer).name

        if ( MyInternalStorage.isServerReachable(applicationContext)) {
            productClient.addProduct(manufacturerId, product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    this.setResult(Activity.RESULT_OK, null)
                    this.finish()
                }, { throwable ->
                    Log.e("ERRORS", throwable.message)
                })
        } else {
            val productView = ProductView(manufacturerName, product.name, manufacturerId, product.price,
                product.quantity, product.id, product.currency, product.uuid, product.color, warehouse.id, warehouse.name)

            val localProducts = MyInternalStorage.readObject(applicationContext, "productList")
            if (localProducts is ArrayList<*>) {
                (localProducts as ArrayList<ProductView>).add(productView)
                MyInternalStorage.writeObject(applicationContext, "productList", localProducts)
            }

            val operationList: ArrayList<Operation> = try {
                MyInternalStorage.readObject(applicationContext, "operationList") as ArrayList<Operation>
            } catch (e: Exception) {
                ArrayList()
            }

            operationList.add(Operation(UUID.randomUUID().toString(), OperationName.INSERT, productView, 0))

            MyInternalStorage.writeObject(applicationContext, "operationList", operationList)

            this.setResult(Activity.RESULT_OK, null)
            this.finish()
        }
    }

    fun goBack(view: View) {
        this.finish()
    }
}