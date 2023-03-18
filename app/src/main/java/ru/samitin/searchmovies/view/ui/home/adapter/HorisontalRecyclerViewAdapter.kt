package ru.samitin.searchmovies.view.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import ru.samitin.searchmovies.databinding.HorisontalItemBinding

import ru.samitin.searchmovies.entities.CardMovie

class HorisontalRecyclerViewAdapter(val arrayList: List<CardMovie>) :
    RecyclerView.Adapter<HorisontalRecyclerViewAdapter.HorisontalViewHolder>() {

    private var onHorisontalClickListener: VerticalRecyclerViewAdapter.OnHorisontalClickListener?=null
    fun setOnHorisontalClickListener(onHorisontalClickListener: VerticalRecyclerViewAdapter.OnHorisontalClickListener){
        this.onHorisontalClickListener=onHorisontalClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorisontalViewHolder {
        val binding = HorisontalItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HorisontalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HorisontalViewHolder, position: Int) {
        arrayList[position].apply {
            holder.binding.name.text = name
            holder.binding.date.text = date
            holder.binding.rating.text = rating.toString()
            holder.binding.image.load(image)
            holder.binding.image.setOnClickListener {
                onHorisontalClickListener?.onClick(this)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class HorisontalViewHolder(val binding: HorisontalItemBinding): RecyclerView.ViewHolder(binding.root)

}