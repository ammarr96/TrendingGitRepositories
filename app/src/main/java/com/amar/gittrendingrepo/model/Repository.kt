package com.amar.gittrendingrepo.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Repository (

    @SerializedName("rank")
    var rank: Int,

    @SerializedName("repositoryName")
    var repositoryName: String,

    @SerializedName("username")
    var username: String,

    @SerializedName("created_at")
    var createdAt: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("forks")
    var forkCount: Int,

    @SerializedName("totalStars")
    var startsCount: Int,

    @SerializedName("language")
    var language: String,

    @SerializedName("languageColor")
    var languageColor: String,

    @SerializedName("url")
    var url: String,

    @SerializedName("builtBy")
    var users: List<Owner>

) : Serializable, Comparable<Repository> {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun compareTo(other: Repository): Int {
        if (rank > other.rank) {
            return -1
        }
        else {
            return 1
        }
    }
}