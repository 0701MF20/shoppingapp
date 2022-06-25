package com.example.shoppingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import com.example.shoppingapp.databinding.ActivityCartBinding
import com.example.shoppingapp.databinding.ActivityPaymentBinding
import com.example.shoppingapp.utils.Constants

class PaymentActivity : AppCompatActivity() {
    lateinit var payementbinding:ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        payementbinding= ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(payementbinding.root)
        var code:String= intent.getStringExtra("orderCode").toString()
    payementbinding.webview.setMixedContentAllowed(false)
    payementbinding.webview.loadUrl(Constants.PAYMENT_URL+code)

    }
}