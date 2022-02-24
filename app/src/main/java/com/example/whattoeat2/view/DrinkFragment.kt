package com.example.whattoeat2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whattoeat2.R
import kotlinx.android.synthetic.main.drink_fragment.view.*

private lateinit var recyclerView: RecyclerView
private lateinit var adabter:HorizontalRecyclerView
class DrinkFragment : Fragment(R.layout.drink_fragment) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val data:MutableList<DataInject> = ArrayList()
        for(i in 1..10){
            data.add(DataInject("Title$i"))

        }
        adabter = HorizontalRecyclerView(data)
        var rootView = inflater.inflate(R.layout.drink_fragment, container, false).rootView
        rootView.recyDrink.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        rootView.recyDrink.adapter = adabter
        return rootView
    }
}