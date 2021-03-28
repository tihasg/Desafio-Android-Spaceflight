package com.spaceflight.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spaceflight.network.response.NewsResponse
import com.spaceflight.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class HomeViewModel(private val repository: NewsRepository) : ViewModel(), CoroutineScope {
    var listener: HomeListener? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job


    private val _newsList = MutableLiveData<List<NewsResponse>>()
    val newList: LiveData<List<NewsResponse>>
        get() = _newsList

    fun getNews() {
        launch {
            try {
             val response = repository.getNews(15, 1)
               if (response.isSuccessful){
                   _newsList.postValue(response.body())
                   repository.saveNews(response.body()!!)
                   listener!!.apiSuccess()

               } else {
                   _newsList.postValue(arrayListOf())
                   listener!!.apiError("Error Carregar Lista")
               }
            } catch (e: Exception) {
                _newsList.postValue(arrayListOf())
                listener!!.apiError("Error Carregar Lista")
            }
        }
    }
}