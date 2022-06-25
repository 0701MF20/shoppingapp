package com.example.shoppingapp.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.shoppingapp.CartActivity
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ActivityItemBinding
import com.example.shoppingapp.models.Product
import com.hishd.tinycart.model.Cart
import com.hishd.tinycart.util.TinyCartHelper
import org.json.JSONException
import org.json.JSONObject

class ItemActivity : AppCompatActivity() {
    private lateinit var bindingItemActivity: ActivityItemBinding
private lateinit var currentProduct:Product
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingItemActivity= ActivityItemBinding.inflate(layoutInflater)
        setContentView( bindingItemActivity.root)
        var nameOfItem: String? =intent.getStringExtra("Name")
        var IdOfItem: Int =intent.getIntExtra("Id",0)
        var imageOfItem: String? =intent.getStringExtra("Image")
        var priceOfItem: Double =intent.getDoubleExtra("Price",0.0)
         Glide.with(this).load(imageOfItem).into(bindingItemActivity.imageView3)
        getItemDetails(IdOfItem)
//        supportActionBar.setTitle(nameOfItem)
        actionBar?.setTitle(nameOfItem)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        var cart: Cart =TinyCartHelper.getCart()
        bindingItemActivity.addtocartbutton.setOnClickListener(object: View.OnClickListener
        {
            override fun onClick(p0: View?) {
cart.addItem(currentProduct,1)
            }

        })

    }
fun getItemDetails(id:Int)
{

// Instantiate the RequestQueue.
    val queue = Volley.newRequestQueue(this)
    val url = Constants.GET_PRODUCT_DETAILS_URL+id


// Request a string response from the provided URL.
    val stringRequest = StringRequest(
        Request.Method.GET, url,
        { response ->
            try{
                var stringResponse:String=response.toString()
                Log.e("MAIN",stringResponse)
                var objectJSON: JSONObject = JSONObject(stringResponse)
                if(objectJSON.getString("status").equals("success"))
                {
                    var JSONObject: JSONObject =objectJSON.getJSONObject("product")
                    var brief:String=JSONObject.getString("description")
                    Log.e("MAIN",brief)
                    bindingItemActivity.textView3.setText(Html.fromHtml(brief))
                    currentProduct=Product(JSONObject.getString("name"),
                            Constants.PRODUCTS_IMAGE_URL+JSONObject.getString("image"),
                            JSONObject.getString("status"),
                            JSONObject.getDouble("price"),
                            JSONObject.getDouble("price_discount"),
                            JSONObject.getInt("stock"),
                            JSONObject.getInt("id"))

                }

            }catch (e: JSONException)
            {
                e.printStackTrace()
            }
        },
        Response.ErrorListener {

        })

// Add the request to the RequestQueue.
    queue.add(stringRequest)

}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       //If error shown them remove R import statement
        menuInflater.inflate(R.menu.addtocartmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addtocartItem ->
            {
                startActivity(Intent(this,CartActivity::class.java))
//                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
                return true
            }

            else->return false
        }
    }
}
