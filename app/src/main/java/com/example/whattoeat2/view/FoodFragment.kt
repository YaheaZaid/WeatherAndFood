package com.example.whattoeat2.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whattoeat2.R
import com.example.whattoeat2.model.Name
import com.example.whattoeat2.viewmodel.NameViewModel
import io.reactivex.CompletableObserver
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.util.HalfSerializer.onComplete
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.drink_fragment.view.*
import kotlinx.android.synthetic.main.food_fragment.view.*
import io.reactivex.exceptions.UndeliverableException

import io.reactivex.plugins.RxJavaPlugins
import kotlinx.android.synthetic.main.post_item.*
import java.io.IOException
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.lang.NullPointerException
import java.net.SocketException


class FoodFragment : Fragment(R.layout.food_fragment) {
    private lateinit var adabter:HorizontalRecyclerView
    private lateinit var foodAdabter:NameRecyclerview
    private lateinit var mNameViewModel:NameViewModel
    val datahot:MutableList<DataInject> = ArrayList()
    val datacold:MutableList<DataInject> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.food_fragment, container, false).rootView
        mNameViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(activity!!.application)
        ).get(
            NameViewModel::class.java
        )
        mNameViewModel = ViewModelProvider(this).get(NameViewModel::class.java)

        foodAdabter = NameRecyclerview()
        rootView.food_rerecyclerview.adapter=foodAdabter

        rootView.food_rerecyclerview.layoutManager = LinearLayoutManager(context
            ,LinearLayoutManager.VERTICAL,false)


        datahot.add(DataInject("Soap",R.mipmap.ic_soap_foreground))
        datahot.add(DataInject("Hot Soap",R.mipmap.ic_soaphot_foreground))
        datahot.add(DataInject("Rice",R.mipmap.ic_rice_foreground))


        datacold.add(DataInject("Ice Creem",R.mipmap.ic_icecreem1_foreground))
        datacold.add(DataInject("Ice Creem Choklate",R.mipmap.ic_icecreem2_foreground))
        datacold.add(DataInject("Burger",R.mipmap.ic_burger_foreground))
        datacold.add(DataInject("Salad",R.mipmap.ic_salad_foreground))


        rootView.addFood.setOnClickListener {
            insertDataToDatabase(rootView.foodName.text.toString())
      }
        rootView.deleteFood.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes"){ _ ,_ ->
                val name = Name(0,rootView.foodName.text.toString())
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
            foodAdabter.setData(name)

        })
        adabter = HorizontalRecyclerView(datacold)
        rootView.recyFood.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        rootView.recyFood.adapter = adabter

        return rootView
    }

    private fun insertDataToDatabase(s:String) {


        val type=s
        val name = Name(0,s)
        mNameViewModel.addName(name)
        Toast.makeText(requireContext(),"${name}Has Been Add",Toast.LENGTH_SHORT).show()
    }

}