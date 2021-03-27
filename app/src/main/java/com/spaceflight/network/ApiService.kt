package com.spaceflight.network

import com.spaceflight.network.response.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

   @GET("/api/v2/articles")
    fun getNews(
       @Query("_limit") limit: Int,
       @Query("_start") start: Int
   ) : Deferred<Response<List<NewsResponse>>>
}