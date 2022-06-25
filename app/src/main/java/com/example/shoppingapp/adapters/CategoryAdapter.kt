package com.example.shoppingapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.CategoryActivity
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.CategoryListItemBinding
import com.example.shoppingapp.models.category

class CategoryAdapter() : RecyclerView.Adapter<CategoryViewHolder>() {
    private lateinit var categories:ArrayList<category>
    private lateinit var context:Context
    constructor(context: Context,categories:ArrayList<category>) : this() {
        this.context=context
    this.categories=categories
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        var view1: View = LayoutInflater.from(context).inflate(R.layout.category_list_item,parent,false)
        return CategoryViewHolder(view1)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        var categoryitem:category=categories.get(position)
        holder.bindingItemCategory.textView2.setText(Html.fromHtml(categoryitem.getName()))
        Glide.with(context).load(categoryitem.getIcon()).into(holder.bindingItemCategory.imageView2)
        holder.bindingItemCategory.imageView2.setBackgroundColor(Color.parseColor(categoryitem.getColor()))
        holder.itemView.setOnClickListener{
            var intentToSpecificProducts:Intent=Intent(context,CategoryActivity::class.java)
            intentToSpecificProducts.putExtra("category_name",categoryitem.getName())
            intentToSpecificProducts.putExtra("category_id",categoryitem.getId())
            context.startActivity(intentToSpecificProducts)

        }

        }

    override fun getItemCount(): Int {
        return categories.size
    }
}

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var bindingItemCategory:CategoryListItemBinding= CategoryListItemBinding.bind(itemView)
    }
