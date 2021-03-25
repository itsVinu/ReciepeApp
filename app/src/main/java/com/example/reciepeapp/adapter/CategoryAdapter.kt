package com.example.reciepeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.reciepeapp.R
import com.example.reciepeapp.responses.categoryResponse.CategoriesItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_recyclerview.view.*

class CategoryAdapter(var list: List<CategoriesItem>) : RecyclerView.Adapter<CategoryAdapter.onItemViewHolder>() {

    var onItemClick: ((list: CategoriesItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onItemViewHolder {
        return onItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_recyclerview, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: onItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class onItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: CategoriesItem) {
            itemView.apply {
                mealName.text = user.strCategory.toString()
                Picasso.get().load(user.strCategoryThumb.toString()).into(mealImg)

                setOnClickListener {
                    onItemClick?.invoke(user)
                    Toast.makeText(context,"${adapterPosition} ${user.strCategory}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}