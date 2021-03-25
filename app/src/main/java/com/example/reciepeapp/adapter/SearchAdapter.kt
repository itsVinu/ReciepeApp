package com.example.reciepeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reciepeapp.R
import com.example.reciepeapp.responses.searchResponse.MealsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.food_recyclerview.view.*

class SearchAdapter(var list: List<MealsItem>) : RecyclerView.Adapter<SearchAdapter.onItemViewHolder>() {

    var onItemClick: ((list1: MealsItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onItemViewHolder {
        return onItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.food_recyclerview, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: onItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class onItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: MealsItem) {
            itemView.apply {
                mealName.text = user.strMeal.toString()
                Picasso.get().load(user.strMealThumb.toString()).into(mealImg)
                setOnClickListener {
                    onItemClick?.invoke(user)
                }
            }
        }
    }
}