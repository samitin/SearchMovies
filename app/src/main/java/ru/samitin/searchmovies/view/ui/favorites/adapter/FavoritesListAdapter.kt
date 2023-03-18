package ru.samitin.searchmovies.view.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import ru.samitin.searchmovies.databinding.FavoriteItemBinding
import ru.samitin.searchmovies.entities.CardMovie


class FavoritesListAdapter(var onItemClick:(cardMovie: CardMovie) -> Unit) : RecyclerView.Adapter<FavoritesListAdapter.ViewHolder>() {

    private var values: List<CardMovie> = arrayListOf()

    fun setMovies(list : List<CardMovie>){
        notifyDataSetChanged()
        values =list
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.binding.apply {
            name.text = item.name
            date.text = item.date
            rating.text = item.rating.toString()
            image.load(item.image)
            canteiner.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: FavoriteItemBinding) : RecyclerView.ViewHolder(binding.root)

}
