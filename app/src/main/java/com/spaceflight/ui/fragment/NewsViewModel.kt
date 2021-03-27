package com.spaceflight.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spaceflight.network.response.NewsResponse
import com.spaceflight.repository.NewsRepository

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    var listener: NewsListener? = null

    private val _newsList = MutableLiveData<List<NewsResponse>>()
    val newList: LiveData<List<NewsResponse>>
        get() = _newsList

    fun initViewModel() {
        _newsList.postValue(repository.getNews())

        listener!!.onSearch()
    }

    fun saveClick(newsResponse: NewsResponse){
        repository.saveClick(newsResponse)
    }

}