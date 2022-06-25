package com.example.shoppingapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.CategoryListItemBinding
import com.example.shoppingapp.databinding.ProductItemBinding
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.models.category
import com.example.shoppingapp.utils.ItemActivity

class ProductAdapter() : RecyclerView.Adapter<ProductViewHolder>() {

        private lateinit var productsList:ArrayList<Product>
        private lateinit var context: Context
        constructor(context: Context,productsList:ArrayList<Product>) : this() {
            this.context=context
            this.productsList=productsList
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            var view1: View = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false)
            return ProductViewHolder(view1)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            var productitem: Product =productsList.get(position)
            holder.bindingItemProduct.productName.setText(productitem.getNameOfProduct())
            holder.bindingItemProduct.currencyview.setText("Rs: "+productitem.getPriceOfProduct())

            Glide.with(context).load(productitem.getImageOfProduct()).into(holder.bindingItemProduct.imageView4)
//            holder.bindingItemCategory.imageView2.setBackgroundColor(Color.parseColor(categoryitem.getColor()))

            holder.bindingItemProduct.item.setOnClickListener {
Log.e("productAdapter","ok")

                var intent:Intent=Intent(context,ItemActivity::class.java)
                intent.putExtra("Id",productitem.getIdOfProduct())
                intent.putExtra("Name",productitem.getNameOfProduct())
                intent.putExtra("Image",productitem.getImageOfProduct())
                intent.putExtra("Price",productitem.getPriceOfProduct())
                context.startActivity(intent)
            }
        }
        override fun getItemCount(): Int {
            return productsList.size
        }
}
class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    var bindingItemProduct: ProductItemBinding = ProductItemBinding.bind(itemView)
}