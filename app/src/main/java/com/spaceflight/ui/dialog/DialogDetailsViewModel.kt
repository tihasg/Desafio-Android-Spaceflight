package com.spaceflight.ui.dialog

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spaceflight.network.response.NewsResponse
import com.spaceflight.repository.NewsRepository

class DialogDetailsViewModel(private val repository: NewsRepository) : ViewModel() {
    var listener: DialogListener? = null

    private val _new = MutableLiveData<NewsResponse>()
    val news: LiveData<NewsResponse>
        get() = _new

    fun getClick() {
       _new.postValue(repository.getClick())
    }

    fun onClose(view: View) {
        listener!!.onClose()
    }
}
