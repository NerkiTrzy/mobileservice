package com.roguskip.roguskiwarehouse.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.roguskip.roguskiwarehouse.model.ProductView
import org.jetbrains.anko.db.*

val PRODUCT_TABLE = "Product"
val PRODUCT_ID = "_id"
val PRODUCT_NAME = "name"
val PRODUCT_CURRENCY = "currency"
val PRODUCT_PRICE = "price"
val PRODUCT_QUANTITY = "quantity"
val PRODUCT_MANUFACTURER_ID = "manufacturer_id"

class MySqlHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "mydb") {

    companion object {
        private var instance: MySqlHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MySqlHelper {
            if (instance == null) {
                instance = MySqlHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("Manufacturer", true,
            "_id" to INTEGER + PRIMARY_KEY,
            "name" to TEXT)

        db.createTable(PRODUCT_TABLE, true,
            PRODUCT_ID to INTEGER + PRIMARY_KEY,
            PRODUCT_NAME to TEXT,
            PRODUCT_CURRENCY to TEXT,
            PRODUCT_PRICE to REAL,
            PRODUCT_QUANTITY to INTEGER,
            PRODUCT_MANUFACTURER_ID to INTEGER
            )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun insertProduct(product : ProductView) {
        val db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(PRODUCT_NAME, product.productName)
        contentValues.put(PRODUCT_CURRENCY, product.currency)
        contentValues.put(PRODUCT_PRICE, product.price.toDouble())
        contentValues.put(PRODUCT_QUANTITY, product.quantity)
        contentValues.put(PRODUCT_MANUFACTURER_ID, product.manufacturerId)
        var result = db.insert(PRODUCT_TABLE, null, contentValues)
        if (result == -1.toLong())
            Toast.makeText(null, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(null, "Success", Toast.LENGTH_SHORT).show()
    }

}

// Access property for Context
val Context.database: MySqlHelper
    get() = MySqlHelper.getInstance(applicationContext)