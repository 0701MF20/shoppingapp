package com.example.shoppingapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.shoppingapp.adapters.CartAdapter
import com.example.shoppingapp.databinding.ActivityCheckoutBinding
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.utils.Constants
import com.hishd.tinycart.model.Cart
import com.hishd.tinycart.util.TinyCartHelper
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CheckoutActivity : AppCompatActivity() {
    lateinit var adapterOfCart: CartAdapter
    lateinit var productList:ArrayList<Product>
    lateinit var bindingcheckout:ActivityCheckoutBinding
    var tax=11
   lateinit var cart: Cart
     var finalCost:Double?=null
    lateinit var progressDialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingcheckout= ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(bindingcheckout.root)
        progressDialog= ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Processing....")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        productList= ArrayList<Product>()
//        Cart cart = TinyCartHelper.getCart();
       cart= TinyCartHelper.getCart()
        for(item in cart.allItemsWithQty.entries)
        {
            var product:Product=item.key as Product
            product.setQuantityOfProduct(item.value)
            productList.add(product)

        }



        adapterOfCart= CartAdapter(this, productList)
        var layoutManager: LinearLayoutManager = LinearLayoutManager(this)
//        bindingCart.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
      bindingcheckout.recyclerViewConfirm.layoutManager=layoutManager
        bindingcheckout.recyclerViewConfirm.adapter=adapterOfCart
bindingcheckout.subtotalcheckoutid.setText("Rs: " + cart.totalPrice)
        finalCost=((cart.totalPrice.toDouble()*11)/100)+cart.totalPrice.toDouble()
        bindingcheckout.totalcheckoutId.setText("Rs: " + finalCost)

bindingcheckout.processCheckout.setOnClickListener{
    proceedCheckOut()
}

    }
    fun proceedCheckOut()
    {
        progressDialog.show();
        // Instantiate the RequestQueue.
        val queue:RequestQueue= Volley.newRequestQueue(this)

        var productOrder:JSONObject=JSONObject()

        var dataObject:JSONObject= JSONObject()
        try {



             productOrder.put("address", bindingcheckout.AddressId.text.toString());
            productOrder.put("buyer", bindingcheckout.nameId.text.toString());
            productOrder.put("comment", bindingcheckout.additionalComment.text.toString());
            productOrder.put("created_at", Calendar.getInstance().getTimeInMillis());
            productOrder.put("last_update", Calendar.getInstance().getTimeInMillis());
            productOrder.put("date_ship", Calendar.getInstance().getTimeInMillis());
            productOrder.put("email", bindingcheckout.emailId.text.toString());
            productOrder.put("phone", bindingcheckout.PhoneId.text.toString());
            productOrder.put("serial", "cab8c1a4e4421a3b");
            productOrder.put("shipping", "");
            productOrder.put("shipping_location", "");
            productOrder.put("shipping_rate", "0.0");
            productOrder.put("status", "WAITING");
            productOrder.put("tax", tax);
            productOrder.put("total_fees", finalCost);
















            var product_order_detail:JSONArray= JSONArray()
            for(item in cart.allItemsWithQty.entries)
            {
                var product:Product=item.key as Product
                product.setQuantityOfProduct(item.value)
                var productObj:JSONObject= JSONObject()

                productObj.put("amount", item.value);
                productObj.put("price_item", product.getPriceOfProduct());
                productObj.put("product_id", product.getIdOfProduct());
                productObj.put("product_name", product.getNameOfProduct());
                product_order_detail.put(productObj)


            }
            dataObject.put("product_order", productOrder);
            dataObject.put("product_order_detail", product_order_detail);

            Log.e("Error", dataObject.toString())
        }catch (e: JSONException)
        {
            e.printStackTrace()
        }

        val jsonRequest:JsonObjectRequest = object : JsonObjectRequest(
                Request.Method.POST, Constants.POST_ORDER_URL, dataObject,

                Response.Listener { response ->
                    Log.e("Status", "Im:" + response.getString("status"))
                    var code: String = response.getJSONObject("data").getString("code")
                    try {
                        if (response.getString("status").equals("success")) {
                            Toast.makeText(this, "Successful order", Toast.LENGTH_SHORT).show()
                            var code: String = response.getJSONObject("data").getString("code")
                            AlertDialog.Builder(this@CheckoutActivity)
                                    .setTitle("Order Successful")
                                    .setCancelable(false)
                                    .setMessage("Your order number is: $code")
                                    .setPositiveButton("Pay Now") { dialogInterface, i ->
                                        val intent = Intent(this@CheckoutActivity, PaymentActivity::class.java)
                                        intent.putExtra("orderCode", code)
                                        startActivity(intent)
                                    }.show()
                            Log.e("err2", response.toString())
                        } else {
                            AlertDialog.Builder(this@CheckoutActivity)
                                    .setTitle("Order Failed")
                                    .setMessage("Something went wrong, please try again.")
                                    .setCancelable(false)
                                    .setPositiveButton("Close") { dialogInterface, i -> }.show()
                            Toast.makeText(this, "Order Failed", Toast.LENGTH_SHORT).show()
                            Log.e("err3", response.toString())

                        }
                        progressDialog.dismiss()
                        Log.e("Status", "Im:" + response.toString())

                    } catch (e: Exception) {

                    }
                },

                Response.ErrorListener { error ->


                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String>  {

                var headers=HashMap<String, String>()
                headers.put("Security", "secure_code")
                return headers

            }

        }

        queue.add(jsonRequest)

    }

//    override fun onApplyThemeResource(theme: Resources.Theme?, resid: Int, first: Boolean) {
//        super.onApplyThemeResource(theme, resid, first)
//    }
}