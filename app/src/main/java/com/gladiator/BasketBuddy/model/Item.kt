package com.gladiator.BasketBuddy.model


data class Item(
    val itemName:String="",
    val itemDescription: String,
    val quantity: Int=0,
    val listId:Int=0) {
}