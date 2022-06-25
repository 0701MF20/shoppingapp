package com.example.shoppingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.shoppingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMainActivity:ActivityMainBinding
   var SPLASH_TIME_OUT:Long=4000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity= ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)
Handler().postDelayed(Runnable() {
    var intent: Intent =Intent(this@MainActivity,HomeActivity::class.java)
    startActivity(intent)
finish()
},SPLASH_TIME_OUT)
    }
}