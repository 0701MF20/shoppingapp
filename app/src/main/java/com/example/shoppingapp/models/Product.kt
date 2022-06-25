package com.example.shoppingapp.models

import com.hishd.tinycart.model.Item
import java.math.BigInteger
import java.io.Serializable
import java.math.BigDecimal

class Product(): Item, Serializable {
    private lateinit var nameOfProduct:String
    private lateinit var imageOfProduct:String
    private lateinit var statusOfProduct:String
    private var priceOfProduct:Double?=null
    private var discountOnProduct:Double?=null
    private var stockOfProduct:Int?=null
    private var idOfProduct:Int?=null
    private var quantityOfProduct:Int?=null
    constructor(
        nameOfProduct:String, imageOfProduct:String, statusOfProduct:String, priceOfProduct:Double?, discountOnProduct:Double?,
        stockOfProduct:Int, idOfProduct:Int?) : this() {
        this.nameOfProduct=nameOfProduct
        this.imageOfProduct=imageOfProduct
        this.statusOfProduct=statusOfProduct
        this.priceOfProduct=priceOfProduct
        this.discountOnProduct=discountOnProduct
        this.stockOfProduct=stockOfProduct
        this.idOfProduct=idOfProduct
    }
    fun getNameOfProduct():String
    {
         return nameOfProduct
    }
    fun setNameOfProduct(nameOfProduct:String)
    {
        this.nameOfProduct=nameOfProduct
    }
    fun getImageOfProduct():String
    {
        return imageOfProduct
    }
    fun getImageOfProduct(imageOfProduct:String)
    {
        this.imageOfProduct=imageOfProduct
    }
    fun getStatusOfProduct():String
    {
        return statusOfProduct
    }
    fun getStatusOfProduct(statusOfProduct:String)
    {
        this.statusOfProduct=statusOfProduct
    }
    fun getPriceOfProduct(): Double?
    {
        return priceOfProduct
    }
    fun getPriceOfProduct(priceOfProduct:Double)
    {
        this.priceOfProduct=priceOfProduct
    }
    fun getDiscountOnProduct():Double?
    {
        return discountOnProduct
    }
    fun getDiscountOnProduct(discountOnProduct:Double)
    {
        this.discountOnProduct=discountOnProduct
    }
    fun getStockOfProduct():Int?
    {
        return stockOfProduct
    }
    fun getStockOfProduct(stockOfProduct:Int)
    {
        this.stockOfProduct=stockOfProduct
    }
    fun getIdOfProduct():Int?
    {
        return idOfProduct
    }
    fun getIdOfProduct(idOfProduct:Int)
    {
        this.idOfProduct=idOfProduct
    }

    override fun getItemPrice(): BigDecimal {
        return BigDecimal.valueOf(priceOfProduct!!)
    }

    override fun getItemName(): String {
        return nameOfProduct
    }
    fun getQuantityOfProduct():Int?
    {
        return quantityOfProduct
    }
    fun setQuantityOfProduct(quantityOfProduct:Int)
    {
       this.quantityOfProduct=quantityOfProduct
    }
}