package com.example.shoppingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.shoppingapp.adapters.ProductAdapter
import com.example.shoppingapp.databinding.ActivitySearchBinding
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.utils.Constants
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SearchActivity : AppCompatActivity() {
    lateinit var searchBinding:ActivitySearchBinding
    //For Product
    lateinit var productAdapter: ProductAdapter
    lateinit var products:ArrayList<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchBinding= ActivitySearchBinding.inflate(layoutInflater)
        setContentView(searchBinding.root)
        actionBar?.setTitle("Search Result:")
        actionBar?.setDisplayHomeAsUpEnabled(true)

        products= ArrayList<Product>()
        productAdapter= ProductAdapter(this, products)

        var query: String? =intent.getStringExtra("query")
        getProducts(query.toString())
        var gridlayoutManager: GridLayoutManager = GridLayoutManager(this, 2)
        searchBinding.searchRecyclerView.layoutManager=gridlayoutManager
        searchBinding.searchRecyclerView.adapter=productAdapter
    }
    fun getProducts(query:String)
    {


// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = Constants.GET_PRODUCTS_URL+"?q="+query

// Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try{
                    var stringResponse:String=response.toString()
                    var objectJSON: JSONObject = JSONObject(stringResponse)
                    if(objectJSON.getString("status").equals("success"))
                    {
                        var arrayJSON: JSONArray =objectJSON.getJSONArray("products")
                        for(i in 0 until arrayJSON.length())
                        {
                            var jsonChildOfArray: JSONObject =arrayJSON.getJSONObject(i)
                            var product:Product=Product(jsonChildOfArray.getString("name"),
                                Constants.PRODUCTS_IMAGE_URL+jsonChildOfArray.getString("image"), jsonChildOfArray.getString("status"),jsonChildOfArray.getDouble("price"),jsonChildOfArray.getDouble("price_discount"),jsonChildOfArray.getInt("stock"),jsonChildOfArray.getInt("id"))
                            products.add(product)

                        }
                        productAdapter.notifyDataSetChanged()
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
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}