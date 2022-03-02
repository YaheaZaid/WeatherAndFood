package com.example.whattoeat2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.whattoeat2.R
import com.example.whattoeat2.model.Name
import kotlinx.android.synthetic.main.post_item.view.*
import kotlinx.android.synthetic.main.row.view.*

class NameRecyclerview: RecyclerView.Adapter<NameRecyclerview.PostsViewHolder>() {
     private var nameEat= emptyList<Name>()

    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.name

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)
        return PostsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val currentItem = nameEat[position]
        holder.name.text = currentItem.type.toString()

    }

    override fun getItemCount(): Int {
       return nameEat.size
    }
    fun setData(name: List<Name>){
        this.nameEat = name
        notifyDataSetChanged()
    }
}
