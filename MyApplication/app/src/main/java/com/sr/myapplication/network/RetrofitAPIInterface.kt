package com.sr.myapplication.network

import com.sr.myapplication.model.DataModel
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPIInterface {
    @GET("launches/")
    fun retrieveList(): Call<ArrayList<DataModel?>>?
}