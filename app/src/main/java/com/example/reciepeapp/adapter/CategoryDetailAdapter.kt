package com.example.reciepeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reciepeapp.R
import com.example.reciepeapp.responses.categoryDetailResponse.MealsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.food_recyclerview.view.*

class CategoryDetailAdapter(var list: ArrayList<MealsItem>) :
    RecyclerView.Adapter<CategoryDetailAdapter.ItemViewHolder>() {

    var onItemClick: ((list: MealsItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.food_recyclerview, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(list[position])

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: MealsItem) {
            itemView.apply {
                mealName.text = user.strMeal.toString()
                Picasso.get().load(user.strMealThumb.toString()).into(mealImg)
                setOnClickListener {
                    // TODO: Handle on click
                    onItemClick?.invoke(user)
                }
            }
        }
    }
}