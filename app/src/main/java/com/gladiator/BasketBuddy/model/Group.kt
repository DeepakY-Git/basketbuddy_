package com.gladiator.BasketBuddy.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Group(
    var groupCode: String = "",
    var groupName: String = "",
    var ownerId: Int = 0
) {
    constructor() : this("", "", 0)
}
