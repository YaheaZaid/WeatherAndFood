package com.example.whattoeat2.service

import com.example.whattoeat2.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather?&units=metric&appid=d43f375d94104ea1a8fef567ac79c9eb")

    fun getData(
        @Query("q") cityName:String

    ): Single<WeatherModel>

}