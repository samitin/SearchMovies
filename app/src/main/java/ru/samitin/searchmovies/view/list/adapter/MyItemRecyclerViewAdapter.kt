package ru.samitin.searchmovies.view.list.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import coil.api.load
import ru.samitin.searchmovies.R
import ru.samitin.searchmovies.databinding.FragmentItemBinding
import ru.samitin.searchmovies.entities.CardMovie
import ru.samitin.searchmovies.entities.Movie

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */

class MyItemRecyclerViewAdapter(var onItemClick:(cardMovie:CardMovie) -> Unit) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private var values: List<CardMovie> = arrayListOf()

    fun setMovies(list : List<CardMovie>){
        notifyDataSetChanged()
        values =list
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.binding.apply {
            name.text = item.name
            date.text = item.date
            rating.text = item.rating.toString()
            image.load(item.image)
            image.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root)

}
