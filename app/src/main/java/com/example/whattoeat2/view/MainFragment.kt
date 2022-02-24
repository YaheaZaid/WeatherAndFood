package com.example.whattoeat2.view

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.whattoeat2.R
import com.example.whattoeat2.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*

class MainFragment : Fragment(R.layout.home_fragment) {
    private lateinit var viewModel:MainViewModel
    private lateinit var GET:SharedPreferences
    private lateinit var SET:SharedPreferences.Editor


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        var rootView = inflater.inflate(R.layout.home_fragment, container, false).rootView

        GET = this.activity!!.getSharedPreferences(activity!!.packageName, Context.MODE_PRIVATE)
        SET = GET.edit()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        var cName = GET.getString("cityName","ankara")
        viewModel.refreshData(cName!!)
        getLiveData(cName)

        rootView.img_search_city.setOnClickListener {

            val cityname= rootView.edt_city_name.text.toString()
            SET.putString("cityName",cityname)
            SET.apply()
            viewModel.refreshData(cityname)
            getLiveData(cityname)
        }
return rootView
    }


    private fun getLiveData(cName: String?) {
        viewModel.weather_data.observe(this, Observer {
            data -> data?.let {
            edt_city_name.setText(cName)
            pb_loading.visibility = View.GONE
                ll_data.visibility = View.VISIBLE
            tv_degree.text = data.main.temp.toString()
            tv_city_code.text = data.sys.country.toString()
            tv_city_name.text = data.name.toString()
            tv_humidity.text = data.main.humidity.toString()
            tv_wind_speed.text = data.wind.speed.toString()
            tv_lat.text = data.coord.lat.toString()
            tv_lon.text = data.coord.lon.toString()
            tv_degree.text = data.main.temp.toString() + "°C"
            tv_humidity.text = data.main.humidity.toString() + "%"
            Glide.with(getActivity()!!)
                .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png")
                .into(img_weather_pictures)

        }
        })
        viewModel.weather_load.observe(this, Observer {
            load->load?.let {
                if (it){
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    ll_data.visibility = View.GONE
                }
            else{
                pb_loading.visibility = View.GONE

            }
        }
        })
        viewModel.weather_error.observe(this, Observer {
            error->error?.let {
                if (it){
                    tv_error.visibility=View.VISIBLE
                    ll_data.visibility = View.GONE
                    pb_loading.visibility = View.GONE
                }
            else{
                tv_error.visibility = View.GONE
            }

        }
        })
    }

}