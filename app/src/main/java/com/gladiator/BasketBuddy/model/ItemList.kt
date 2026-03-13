package com.gladiator.BasketBuddy.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ItemList(
    var listId:Int=0,
    var name:String="",
    var groupCode:String=""
) {
    constructor() : this(0, "", "")
}