package com.spaceflight.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.spaceflight.R
import com.spaceflight.databinding.FragmentDetailsBinding
import com.spaceflight.network.response.NewsResponse
import org.koin.android.viewmodel.ext.android.viewModel

class DialogDetails : DialogFragment(), DialogListener {
    lateinit var binding: FragmentDetailsBinding
    private val viewModel: DialogDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniViewModel()
    }

    private fun iniViewModel() {
        viewModel.listener = this
        viewModel.getClick()

        viewModel.news.observe(requireActivity(), Observer<NewsResponse> {
            binding.textView.text = it.summary
            Glide.with(binding.imageView2.context).load(it.imageUrl).into(binding.imageView2)
        })
    }

    override fun onClose() {
        dialog?.dismiss()
    }

}
