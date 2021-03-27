package com.spaceflight.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spaceflight.R
import com.spaceflight.network.response.NewsResponse
import kotlin.properties.Delegates

class NewsRecyclerAdapter(private val getNews: (NewsResponse) -> Unit) :
    RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>(), Filterable {

    var items: List<NewsResponse> by Delegates.observable(emptyList()) { _, old, new ->
        if (old != new) notifyDataSetChanged()
    }

    var newList: List<NewsResponse> by Delegates.observable(emptyList()) { _, old, new ->
        if (old != new) notifyDataSetChanged()
    }

    var filterList = ArrayList<NewsResponse>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(newsResponse: NewsResponse, getNews: (NewsResponse) -> Unit) {
            val name = itemView.findViewById<TextView>(R.id.textTitle)
            val image = itemView.findViewById<ImageView>(R.id.imageView)

            name.text = newsResponse.title

            Glide.with(image.context).load(newsResponse.imageUrl).into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_space, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsResponse = items[position]
        holder.bind(newsResponse, getNews)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charResult = constraint.toString()
                filterList = if (charResult.isEmpty()) {
                    newList as ArrayList<NewsResponse>
                } else {
                    val resultList = ArrayList<NewsResponse>()
                    for (row in newList) {
                        if (row.title?.toLowerCase()
                                ?.contains(constraint.toString().toLowerCase())!!
                        )
                            resultList.add(row)
                    }
                    resultList
                }

                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                items = results?.values as List<NewsResponse>
                notifyDataSetChanged()
            }

        }
    }
}