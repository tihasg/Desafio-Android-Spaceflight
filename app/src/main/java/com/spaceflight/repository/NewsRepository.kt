package com.spaceflight.repository

import com.orhanobut.hawk.Hawk
import com.spaceflight.network.ApiService
import com.spaceflight.network.response.NewsResponse


class NewsRepository(private val apiService: ApiService) {
    suspend fun getNews(limit: Int, start: Int) = apiService.getNews(limit, start).await()

    fun saveNews(news: List<NewsResponse?>) {
        Hawk.put("news", news)
    }

    fun getNews(): List<NewsResponse> {
        return Hawk.get("news")
    }

    fun saveClick(newsResponse: NewsResponse) {
        Hawk.put("click", newsResponse)
    }

    fun getClick(): NewsResponse {
        return Hawk.get("click")
    }
}