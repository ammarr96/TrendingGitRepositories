package com.amar.gittrendingrepo.api

import com.amar.gittrendingrepo.model.Repository

import retrofit2.http.GET

interface ApiService {

    @GET("repositories")
    suspend fun getTrendingRepositories(): List<Repository>

}