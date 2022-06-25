package com.example.shoppingapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ActivityCartBinding
import com.example.shoppingapp.databinding.CartItemBinding
import com.example.shoppingapp.databinding.QuantitydialogBinding

import com.example.shoppingapp.models.Product
import com.hishd.tinycart.model.Cart
import com.hishd.tinycart.util.TinyCartHelper

class CartAdapter(): RecyclerView.Adapter<CartViewHolder>() {
    private lateinit var listOfProducts:ArrayList<Product>
    private lateinit var context: Context
//    lateinit var cartlistener:CartListener
//    interface CartListener
//    {
//        fun onQuantityUpdate()
//    }
    lateinit var cart:Cart
    constructor(context: Context,listOfProducts:ArrayList<Product>/*,cartListener: CartListener*/) : this() {
        this.context=context
        this.listOfProducts=listOfProducts
//        this.cartlistener=cartlistener
        cart=TinyCartHelper.getCart()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        var view1: View = LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false)
        return CartViewHolder(view1)


    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        ///For cart whole activity
        var cartBinding:ActivityCartBinding= ActivityCartBinding.inflate(LayoutInflater.from(context))




        var productItem:Product=listOfProducts.get(position)
        Glide.with(context).load(productItem.getImageOfProduct()).into(holder.bindingCart.itemimageId)
        holder.bindingCart.PriceTextview.setText("Rs: "+productItem.getPriceOfProduct())
holder.bindingCart.itemNameTextView.setText(productItem.getNameOfProduct())
        holder.bindingCart.QuantityIdItem.setText("Quantity:"+productItem.getQuantityOfProduct())

holder.itemView.setOnClickListener(object:View.OnClickListener
{
    override fun onClick(p0: View?) {
        var quantitydialogBinding:QuantitydialogBinding= QuantitydialogBinding.inflate(LayoutInflater.from(context))
        var dialog:AlertDialog=AlertDialog.Builder(context).setView(quantitydialogBinding.root).create()
//        dialog.window.setBackgroundDrawable(ColorDrawable(android.R.color.transparent))
     quantitydialogBinding.textView9.setText(productItem.getNameOfProduct())
        quantitydialogBinding.textView10.setText(""+productItem.getStockOfProduct())
        var stock: Int? =productItem.getStockOfProduct()
        quantitydialogBinding.onetextview.setText(""+productItem.getQuantityOfProduct())
        quantitydialogBinding.minustextview.setOnClickListener(object :View.OnClickListener
        {
            override fun onClick(p0: View?) {
                var quantity=productItem.getQuantityOfProduct()
                if(quantity!! >1)
                {
                    quantity-=1
                }
                productItem.setQuantityOfProduct(quantity)
                quantitydialogBinding.onetextview.setText(""+quantity)

//                if (quantity != null) {
//                    if(quantity>1) {
//                        quantity -= 1
//                    }
//                    productItem.setQuantityOfProduct(quantity)
//                    quantitydialogBinding.onetextview.setText(quantity)
//
//                }
                cart.updateItem(productItem, productItem.getQuantityOfProduct()!!)
                cartBinding.TextViewpriceId.setText("Rs: "+cart.totalPrice)
//                holder.bindingCart.PriceTextview.setText("Rs: "+cart.totalPrice)
                notifyDataSetChanged()

//                holder.bindingCart.QuantityIdItem.setText("Quantity"+productItem.getQuantityOfProduct())

            }

        })
        quantitydialogBinding.plustextview.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {

                var quantity=productItem.getQuantityOfProduct()
//                if (quantity != null) {
//                        quantity += 1
//                    if(quantity>= productItem.getStockOfProduct()!!)
//                    {
//                        Toast.makeText(context,"This quantity is unavailable",Toast.LENGTH_SHORT).show()
//                    return
//                    }
//                    else{
//                        productItem.setQuantityOfProduct(quantity)
//                        quantitydialogBinding.onetextview.setText(quantity)
//                    }
//                }
                quantity = quantity?.plus(1)
                if(quantity!! > productItem.getStockOfProduct()!!)
                {
                                            Toast.makeText(context,"This quantity is unavailable",Toast.LENGTH_SHORT).show()
return
                }
                else
                {
                    productItem.setQuantityOfProduct(quantity)
                        quantitydialogBinding.onetextview.setText(""+productItem.getQuantityOfProduct())
                }
                cart.updateItem(productItem, productItem.getQuantityOfProduct()!!)
                cartBinding.TextViewpriceId.setText("Rs: "+cart.totalPrice)

//                holder.bindingCart.PriceTextview.setText("Rs: "+cart.totalPrice)
                notifyDataSetChanged()
//                holder.bindingCart.QuantityIdItem.setText(""+productItem.getQuantityOfProduct())


            }

        })
        quantitydialogBinding.button5.setOnClickListener(object:View.OnClickListener
        {
            override fun onClick(p0: View?) {
//                holder.bindingCart.PriceTextview.setText("Rs: "+cart.totalPrice)

                Log.e("CartAdapter",""+cart.totalPrice)
                dialog.dismiss()
//                notifyDataSetChanged()
//                cartlistener.onQuantityUpdate()
            }

        })





        dialog.show()


    }
})
    }

    override fun getItemCount(): Int {
        return listOfProducts.size
    }

}
//correct
class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    var bindingCart:CartItemBinding= CartItemBinding.bind(itemView)
}