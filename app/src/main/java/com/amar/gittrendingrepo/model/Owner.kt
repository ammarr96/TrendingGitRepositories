package com.amar.gittrendingrepo.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Owner (

    @SerializedName("username")
    var username: String,

    @SerializedName("avatar")
    var avatarUrl: String,

    @SerializedName("url")
    var url: String

) : Serializable