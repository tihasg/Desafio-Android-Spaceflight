package com.spaceflight.ui.home

import androidx.lifecycle.ViewModel
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


    fun getNews() {
        launch {
            try {
             val response = repository.getNews(15, 1)
               if (response.isSuccessful){
                   repository.saveNews(response.body()!!)
                   listener!!.apiSuccess()

               } else {
                   listener!!.apiError("Error Carregar Lista")
               }
            } catch (e: Exception) {
                listener!!.apiError("Error Carregar Lista")
            }
        }
    }
}