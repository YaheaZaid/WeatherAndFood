package com.example.whattoeat2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.whattoeat2.R
import kotlinx.android.synthetic.main.row.view.*

class HorizontalRecyclerView(data: MutableList<DataInject>) : RecyclerView.Adapter<HorizontalRecyclerView.MyViewHolder>(){

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tvTitle:TextView = itemView.tv_title
        val imgIcon:ImageView = itemView.ic_img
        val cardView:CardView = itemView.cardView
    }

   private val data:List<DataInject> = data

    private val items:MutableList<CardView>
    init {
        this.items=ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row,parent,false)
    return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.text = data[position].title
        holder.imgIcon.setImageResource(data[position].img)
        items.add(holder.cardView)

    }

    override fun getItemCount(): Int {
       return data.size
    }
}