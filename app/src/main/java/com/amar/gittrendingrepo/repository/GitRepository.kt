package com.amar.gittrendingrepo.repository

import com.amar.gittrendingrepo.api.ApiService
import com.amar.gittrendingrepo.model.Repository
import javax.inject.Inject

class GitRepository @Inject constructor(
    private val apiService: ApiService) {

    suspend fun getTrendingRepositories() : List<Repository> {
        return apiService.getTrendingRepositories()
    }

}