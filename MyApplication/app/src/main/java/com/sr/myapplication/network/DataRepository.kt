package com.sr.myapplication.network

import androidx.lifecycle.MutableLiveData
import com.sr.myapplication.app.AppController
import com.sr.myapplication.model.DataModel
import com.sr.myapplication.model.DataRepoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DataRepository {
    @JvmField
    @Inject
    var webService: RetrofitAPIInterface? = null
    val list: MutableLiveData<DataRepoModel>
        get() {
            AppController.appComponent?.inject(this)
            val call = webService!!.retrieveList()
            call!!.enqueue(object : Callback<ArrayList<DataModel?>> {
                override fun onResponse(
                    call: Call<ArrayList<DataModel?>>,
                    response: Response<ArrayList<DataModel?>>
                ) {
                    data.value = DataRepoModel(response.body())
                }

                override fun onFailure(
                    call: Call<ArrayList<DataModel?>>,
                    t: Throwable
                ) {
                    data.value = DataRepoModel(t)
                }
            })
            return data
        }

    companion object {
        private val data = MutableLiveData<DataRepoModel>()
    }
}