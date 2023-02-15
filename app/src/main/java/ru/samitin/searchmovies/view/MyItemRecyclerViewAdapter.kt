package ru.samitin.searchmovies.view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.samitin.searchmovies.R
import ru.samitin.searchmovies.databinding.FragmentItemBinding
import ru.samitin.searchmovies.entities.Movie

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */

class MyItemRecyclerViewAdapter(var onItemClick:(movie: Movie) -> Unit) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private var values: List<Movie> = arrayListOf()

    fun setMovies(list : List<Movie>){
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
            image.setImageResource(R.drawable.the_boss_baby)
            image.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root)

}
