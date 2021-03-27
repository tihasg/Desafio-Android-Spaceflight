package com.spaceflight.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.ViewCompat.canScrollVertically
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spaceflight.databinding.FragmentNewsBinding
import com.spaceflight.network.response.NewsResponse
import com.spaceflight.ui.adapter.NewsRecyclerAdapter
import com.spaceflight.ui.dialog.DialogDetails
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment : Fragment(), NewsListener {

    private val viewModel: NewsViewModel by viewModel()
    lateinit var binding: FragmentNewsBinding

    private var adapterNews = NewsRecyclerAdapter(this::getNews)

    private var numPage = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        binding.newList.layoutManager = LinearLayoutManager(context)
        binding.newList.adapter = adapterNews

        binding.newList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!canScrollVertically(
                        recyclerView,
                        1
                    ) && newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    numPage += 15
                    viewModel.getNewsPage(numPage)
                }
            }
        })
    }

    private fun initViewModel() {
        binding.viewmodel = viewModel
        viewModel.listener = this
        viewModel.initViewModel()

        viewModel.newList.observe(requireActivity(), Observer<List<NewsResponse>> {
            adapterNews.items = it
            adapterNews.newList = it
        })

    }

    private fun getNews(obj: NewsResponse) {
        viewModel.saveClick(obj)
        DialogDetails().show(requireActivity().supportFragmentManager, "Details")
    }

    override fun onSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapterNews.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterNews.filter.filter(newText)
                return true
            }

        })
    }
}