package ru.samitin.searchmovies.view.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.samitin.searchmovies.databinding.VerticalItemBinding
import ru.samitin.searchmovies.entities.CardMovie
import ru.samitin.searchmovies.entities.Category

class VerticalRecyclerViewAdapter(val context: Context?):
    RecyclerView.Adapter<VerticalRecyclerViewAdapter.VerticalRVViewHolder>() {

    private var onVerticalClickListener:OnVerticalClickListener?=null
    private var onHorisontalClickListener: OnHorisontalClickListener?=null
    var categories = mutableListOf <Category>()

    fun setOnVerticalClickListener(onVerticalClickListener:OnVerticalClickListener){
        this.onVerticalClickListener=onVerticalClickListener
    }
    fun setOnHorisontalClickListener(onHorisontalClickListener:OnHorisontalClickListener){
        this.onHorisontalClickListener=onHorisontalClickListener
    }
    fun addCategoryModel(category: Category){
        categories.add(category)
        notifyItemInserted(itemCount -1)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalRVViewHolder {
        val binding=VerticalItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VerticalRVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerticalRVViewHolder, position: Int) {
        holder.binding.apply {
             categories[position].let {category ->
                 textTitle1.text = category.categoryName
                 btnMore.setOnClickListener {
                     onVerticalClickListener?.onClick(idGenre = categories.get(position).id?.toInt()!!)
                 }
                 val horisontalrecyclerViewAdapter = HorisontalRecyclerViewAdapter(category.listMovie!!)
                 horisontalrecyclerViewAdapter.setOnHorisontalClickListener(onHorisontalClickListener!!)
                 recyclerview1.setHasFixedSize(true)
                 recyclerview1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
                 recyclerview1.adapter = horisontalrecyclerViewAdapter

             }

        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class VerticalRVViewHolder(val binding: VerticalItemBinding): RecyclerView.ViewHolder(binding.root)
    interface OnVerticalClickListener{
        fun onClick(idGenre:Int)
    }
    interface OnHorisontalClickListener{
        fun onClick(model: CardMovie)
    }
}