package com.example.whattoeat2.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whattoeat2.R
import com.example.whattoeat2.model.Name
import com.example.whattoeat2.viewmodel.NameViewModel
import kotlinx.android.synthetic.main.drink_fragment.view.*
import kotlinx.android.synthetic.main.food_fragment.view.*

private lateinit var adabter:HorizontalRecyclerView
private lateinit var drinkAdabter:NameRecyclerview
private lateinit var mNameViewModel: NameViewModel
val datahot:MutableList<DataInject> = ArrayList()
val datacold:MutableList<DataInject> = ArrayList()
class DrinkFragment : Fragment(R.layout.drink_fragment) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        var rootView = inflater.inflate(R.layout.drink_fragment, container, false).rootView
        mNameViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(activity!!.application)
        ).get(
            NameViewModel::class.java
        )
        mNameViewModel = ViewModelProvider(this).get(NameViewModel::class.java)

        drinkAdabter = NameRecyclerview()
        rootView.drink_rerecyclerview.adapter= drinkAdabter
        rootView.drink_rerecyclerview.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false
            )

        datahot.add(DataInject("capuchino",R.mipmap.ic_cap))
        datahot.add(DataInject("Tea",R.mipmap.ic_tea_foreground))
        datahot.add(DataInject("Coffe",R.mipmap.ic_coffe_foreground))
        datacold.add(DataInject("Water",R.mipmap.ic_water))
        datacold.add(DataInject("Ice Tea",R.mipmap.ic_icetea))

        rootView.addDrink.setOnClickListener {
            insertDataToDatabase(rootView.drinkName.text.toString())
        }
        rootView.deleteDrink.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes"){ _ ,_ ->
                val name = Name(0,rootView.drinkName.text.toString())
                mNameViewModel.deleteAll()
                Toast.makeText(requireContext(),"Removed",Toast.LENGTH_SHORT).show()

            }
            builder.setNegativeButton("No"){_,_ ->

            }
            builder.setTitle("Delete All ?")
            builder.setMessage("Are You Sure You Want To Delete All ?")
            builder.create().show()

        }
        mNameViewModel.readAllData.observe(viewLifecycleOwner, Observer {name ->
            drinkAdabter.setData(name)

        })
        adabter = HorizontalRecyclerView(datahot)
        rootView.recyDrink.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        rootView.recyDrink.adapter = adabter
        return rootView
    }
    private fun insertDataToDatabase(s:String) {


        val type=s
        val name = Name(0,s)
        mNameViewModel.addName(name)
        Toast.makeText(requireContext(),"${name}Has Been Add", Toast.LENGTH_SHORT).show()
    }
}