package com.gladiator.BasketBuddy.model

data class User(
    val userId:Int=0,
    val username:String="",
    val password:String="",
    val email:String="",
    val groupCode: String=""
) {
}