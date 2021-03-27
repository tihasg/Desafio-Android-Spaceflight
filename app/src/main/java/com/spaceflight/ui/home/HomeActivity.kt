package com.spaceflight.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.spaceflight.R
import com.spaceflight.databinding.ActivityHomeBinding
import com.spaceflight.ui.fragment.NewsFragment
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), HomeListener {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initViewModel()
    }

    private fun initViewModel() {
        binding.viewmodel = viewModel
        viewModel.listener = this
        viewModel.getNews()
    }

    override fun apiSuccess() {
        val frameLayout = NewsFragment()
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.containerID, frameLayout)
        transition.commit()
    }

    override fun apiError(message: String) {

    }
}