package com.example.shoppingapp

import android.content.Intent
import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.adapters.CartAdapter
import com.example.shoppingapp.databinding.ActivityCartBinding
import com.example.shoppingapp.databinding.ActivityHomeBinding
import com.example.shoppingapp.databinding.ProductItemBinding
import com.example.shoppingapp.models.Product
import com.hishd.tinycart.model.Cart
import com.hishd.tinycart.model.Item
import com.hishd.tinycart.util.TinyCartHelper
import kotlin.collections.Map.Entry

class CartActivity : AppCompatActivity() {
    lateinit var bindingCart:ActivityCartBinding
    lateinit var adapterOfCart:CartAdapter
    lateinit var productList:ArrayList<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingCart= ActivityCartBinding.inflate(layoutInflater)
        setContentView(bindingCart.root)
        actionBar?.setTitle("Cart")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        productList= ArrayList<Product>()
//        Cart cart = TinyCartHelper.getCart();
       var cart: Cart =TinyCartHelper.getCart()
       for(item in cart.allItemsWithQty.entries)
       {
           var product:Product=item.key as Product
           product.setQuantityOfProduct(item.value)
           productList.add(product)

       }



        adapterOfCart= CartAdapter(this,productList)
        var layoutManager:LinearLayoutManager=LinearLayoutManager(this)
        bindingCart.recyclerView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
        bindingCart.recyclerView.layoutManager=layoutManager
        bindingCart.recyclerView.adapter=adapterOfCart
        bindingCart.TextViewpriceId.setText(String.format("Rs %.2f",cart.totalPrice))
        bindingCart.ordernowbutton.setOnClickListener {
    Log.e("P","ol")
    startActivity(Intent(this@CartActivity, CheckoutActivity::class.java)) }

    }
}