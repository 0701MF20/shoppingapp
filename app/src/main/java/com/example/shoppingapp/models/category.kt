package com.example.shoppingapp.models

import kotlin.properties.Delegates

class category() {
    private lateinit var name:String
    private lateinit var icon:String
    private lateinit var color:String
    private lateinit var brief:String
    private var id:Int?=null
    constructor(name:String,icon:String,color:String,brief:String,id:Int) : this() {
        this.name= name
        this.icon=icon
        this.color=color
        this.brief=brief
        this.id=id
    }
    fun getName():String{
        return name
    }
    fun setName(name:String)
    {
        this.name=name
    }

    fun getIcon():String{
        return icon
    }
    fun setIcon(icon:String)
    {
        this.icon=icon
    }
    fun getColor():String{
        return color
    }
    fun setColor(color:String)
    {
        this.color=color
    }
    fun getBrief():String{
        return brief
    }
    fun setBrief(brief:String)
    {
        this.brief=brief
    }
    fun getId(): Int? {
        return id
    }
    fun setId(id:Int)
    {
        this.id=id
    }

}