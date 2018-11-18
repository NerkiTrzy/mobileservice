package com.roguskip.roguskiwarehouse

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import java.util.*

class MainActivity : AppCompatActivity() {

    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLoginFacebook = (findViewById<Button>(R.id.login_button) as LoginButton)
        btnLoginFacebook.setReadPermissions(Arrays.asList("email"))
        btnLoginFacebook.setOnClickListener {
            // Login
            callbackManager = CallbackManager.Factory.create()

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                        startActivity(Intent(applicationContext, ProductListActivity::class.java))
                    }

                    override fun onCancel() {
                        Log.d("MainActivity", "Facebook onCancel.")
                    }

                    override fun onError(error: FacebookException) {
                        Log.d("MainActivity", "Facebook onError.")
                    }
                })
        }
    }

    fun moveToView(view: View) {
        startActivity(Intent(this, ProductListActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

//    fun toastMe(view: View) {
//        val myToast = Toast.makeText(this, "Hello Toast!", Toast.LENGTH_SHORT)
//        myToast.show()
//    }
//
//    fun countMe(view: View) {
//        val showCountTextView = findViewById<TextView>(R.id.textView)
//
//        val countString = showCountTextView.text.toString()
//
//        var count: Int = Integer.parseInt(countString)
//        count++
//
//        showCountTextView.text = count.toString()
//    }
//
//    fun randomMe(view: View) {
//        val randomIntent = Intent(this, SecondActivity::class.java)
//
//        val countString = textView.text.toString()
//
//        val count = Integer.parseInt(countString)
//
//        randomIntent.putExtra(SecondActivity.TOTAL_COUNT, count)
//
//        startActivity(randomIntent)
//    }
}
