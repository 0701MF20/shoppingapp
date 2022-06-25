//For slider we can use the viewpager but for efficent usage we are usig  ReadyMADE LIBRARY


@file:Suppress("LossyEncoding")

package com.example.shoppingapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shoppingapp.adapters.CategoryAdapter
import com.example.shoppingapp.adapters.ProductAdapter
import com.example.shoppingapp.databinding.ActivityHomeBinding
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.models.category
import com.example.shoppingapp.utils.Constants
import com.mancj.materialsearchbar.MaterialSearchBar
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity() {
    //For Category of Product
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var categories:ArrayList<category>
    //For Product
    lateinit var productAdapter: ProductAdapter
    lateinit var products:ArrayList<Product>
    private lateinit var bindingHomeActivity: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingHomeActivity= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bindingHomeActivity.root)
        bindingHomeActivity.searchBar.setOnSearchActionListener(object:MaterialSearchBar.OnSearchActionListener
        {
            override fun onSearchStateChanged(enabled: Boolean) {

            }

            override fun onSearchConfirmed(text: CharSequence?) {
                var intentToSearchActivity:Intent= Intent(this@HomeActivity,SearchActivity::class.java)
                intentToSearchActivity.putExtra("query",text)
                startActivity(intentToSearchActivity)
            }

            override fun onButtonClicked(buttonCode: Int) {
              
            }

        })

        initiateCategories()
        initializeProducts()
        initializeCarousel()


    }
    fun getCategories()
    {
// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url=Constants.GET_CATEGORIES_URL

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
            val resString:String=response.toString()
                var objectJSON:JSONObject= JSONObject(resString)
                if(objectJSON.getString("status").equals("success"))
                {
                    var arrayJson:JSONArray=objectJSON.getJSONArray("categories")
                    for(i in 0 until arrayJson.length())
                    {
                        var singleJsonObj:JSONObject=arrayJson.getJSONObject(i)
                        var category1:category= category(singleJsonObj.getString("name"),Constants.CATEGORIES_IMAGE_URL+singleJsonObj.getString("icon"),singleJsonObj.getString("color"),singleJsonObj.getString("brief"),singleJsonObj.getInt("id"))
                    categories.add(category1)
                    }
                    categoryAdapter.notifyDataSetChanged()
                }
            },
            Response.ErrorListener {

            })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
    fun getRecentProducts()
    {


// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = Constants.GET_PRODUCTS_URL+"?count=8"

// Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
            try{
               var stringResponse:String=response.toString()
                var objectJSON:JSONObject= JSONObject(stringResponse)
                if(objectJSON.getString("status").equals("success"))
                {
                    var arrayJSON:JSONArray=objectJSON.getJSONArray("products")
                    for(i in 0 until arrayJSON.length())
                    {
                        var jsonChildOfArray:JSONObject=arrayJSON.getJSONObject(i)
                        var product:Product=Product(jsonChildOfArray.getString("name"),Constants.PRODUCTS_IMAGE_URL+jsonChildOfArray.getString("image"), jsonChildOfArray.getString("status"),jsonChildOfArray.getDouble("price"),jsonChildOfArray.getDouble("price_discount"),jsonChildOfArray.getInt("stock"),jsonChildOfArray.getInt("id"))
                        products.add(product)

                    }
                    productAdapter.notifyDataSetChanged()
                }

            }catch (e:JSONException)
            {
                e.printStackTrace()
            }
            },
            Response.ErrorListener {

                 })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
    fun initializeCarousel()
    {
//        val imageList = ArrayList<SlideModel>() // Create image list
//        imageList.add(
//            SlideModel(
//                "https://bit.ly/2YoJ77H",
//                "The animal population decreased by 58 percent in 42 years."
//            )
//        )
//        imageList.add(
//            SlideModel(
//                "https://bit.ly/2BteuF2",
//                "Elephants and tigers may become extinct."
//            )
//        )
//        imageList.add(SlideModel("https://bit.ly/3fLJf72", "And people do that."))
//
//        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
//        imageSlider.setImageList(imageList)
//        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP) // for all images
        getRecentOffer()
    }
    fun getRecentOffer()
    {

        val imageList = ArrayList<SlideModel>() // Create image list
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url=Constants.GET_OFFERS_URL

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                val resString:String=response.toString()
                var objectJSON:JSONObject= JSONObject(resString)
                if(objectJSON.getString("status").equals("success"))
                {
                    var offerArrayJson:JSONArray=objectJSON.getJSONArray("news_infos")
                    for(i in 0 until offerArrayJson.length())
                    {
                        var singleJsonObj:JSONObject=offerArrayJson.getJSONObject(i)

                        imageList.add(SlideModel(Constants.NEWS_IMAGE_URL+singleJsonObj.getString("image"),singleJsonObj.getString("title")))
                    }
                    bindingHomeActivity.imageSlider.setImageList(imageList)
                }
            },
            Response.ErrorListener {

            })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
    fun initiateCategories()
    {
        categories= ArrayList<category>()
        categoryAdapter= CategoryAdapter(this, categories)

//        categories.add(
//            category(
//                "Electronics",
//                "https://tutorials.mianasad.com/ecommerce/uploads/category/1655901113927.png",
//                "#f4b016",
//                "some description",
//                1
//            )
//        )
//        categories.add(
//            category(
//                "Electronics",
//                "https://tutorials.mianasad.com/ecommerce/uploads/category/1655901113927.png",
//                "#f4b016",
//                "some description",
//                2
//            )
//        )
//        categories.add(
//            category(
//                "Electronics",
//                "https://tutorials.mianasad.com/ecommerce/uploads/category/1655901113927.png",
//                "#f4b016",
//                "some description",
//                3
//            )
//        )
//        categories.add(
//            category(
//                "Electronics",
//                "https://tutorials.mianasad.com/ecommerce/uploads/category/1655901113927.png",
//                "#f4b016",
//                "some description",
//                4
//            )
//        )
//        categories.add(
//            category(
//                "Electronics",
//                "https://tutorials.mianasad.com/ecommerce/uploads/category/1655901113927.png",
//                "#f4b016",
//                "some description",
//                5
//            )
//        )
//        categories.add(
//            category(
//                "Electronics",
//                "https://tutorials.mianasad.com/ecommerce/uploads/category/1655901113927.png",
//                "#f4b016",
//                "some description",
//                6
//            )
//        )
//        categories.add(
//            category(
//                "Electronics",
//                "https://tutorials.mianasad.com/ecommerce/uploads/category/1655901113927.png",
//                "#f4b016",
//                "some description",
//                7
//            )
//        )
//        categories.add(
//            category(
//                "Electronics",
//                "https://tutorials.mianasad.com/ecommerce/uploads/category/1655901113927.png",
//                "#f4b016",
//                "some description",
//                1
//            )
//        )

        getCategories()
        var gridlayoutManager:GridLayoutManager= GridLayoutManager(this, 4)
        bindingHomeActivity.categoryrecyclerView.layoutManager=gridlayoutManager
        bindingHomeActivity.categoryrecyclerView.adapter=categoryAdapter
    }
    fun initializeProducts()
    {
        products= ArrayList<Product>()
        productAdapter= ProductAdapter(this, products)

//        products.add(
//            Product(
//                "PC Height Proformance CPU i9",
//                "https://tutorials.mianasad.com/ecommerce/uploads/product/1655585730611.jpg",
//                "READY STOCK",
//
//                    7000.0
//                ,
//                550.0,
//                700,
//                1
//            )
//        )
//        products.add(
//            Product(
//                "PC Height Proformance CPU i9",
//                "https://tutorials.mianasad.com/ecommerce/uploads/product/1655585730611.jpg",
//                "READY STOCK",
//                7000.0,
//                550.0,
//                700,
//                1
//            )
//        )
//        products.add(
//            Product(
//                "PC Height Proformance CPU i9",
//                "https://tutorials.mianasad.com/ecommerce/uploads/product/1655585730611.jpg",
//                "READY STOCK",
//                    7000.0,
//                550.0,
//                700,
//                1
//            )
//        )
//        products.add(
//            Product(
//                "PC Height Proformance CPU i9",
//                "https://tutorials.mianasad.com/ecommerce/uploads/product/1655585730611.jpg",
//                "READY STOCK",
//
//                    7000.0,
//                550.0,
//                700,
//                1
//            )
//        )
//        products.add(
//            Product(
//                "PC Height Proformance CPU i9",
//                "https://tutorials.mianasad.com/ecommerce/uploads/product/1655585730611.jpg",
//                "READY STOCK",
//                7000.0,
//                550.0,
//                700,
//                1
//            )
//        )
//        products.add(
//            Product(
//                "PC Height Proformance CPU i9",
//                "https://tutorials.mianasad.com/ecommerce/uploads/product/1655585730611.jpg",
//                "READY STOCK",
//                7000.0,
//                550.0,
//                700,
//                1
//            )
//        )

getRecentProducts()
        var gridlayoutManager:GridLayoutManager= GridLayoutManager(this, 2)
        bindingHomeActivity.recyclerviewForProduct.layoutManager=gridlayoutManager
        bindingHomeActivity.recyclerviewForProduct.adapter=productAdapter
    }

}