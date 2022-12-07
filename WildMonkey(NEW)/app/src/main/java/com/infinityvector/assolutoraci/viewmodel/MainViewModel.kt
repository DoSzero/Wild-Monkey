package com.infinityvector.assolutoraci.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.appsflyer.AppsFlyerConversionListener
import com.infinityvector.assolutoraci.api.ApiInterface
import com.infinityvector.assolutoraci.model.Constants.C1
import com.infinityvector.assolutoraci.model.Constants.appsCheck
import com.infinityvector.assolutoraci.model.Constants.link
import com.orhanobut.hawk.Hawk
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel () {

    val conversionDataListener = object : AppsFlyerConversionListener {
        override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
            val dataGotten = data?.get("campaign").toString()
            Hawk.put(C1, dataGotten)
        }

        // TODO Delete Logs
        override fun onConversionDataFail(p0: String?) {
            Log.e("dev_test", "Error get conversion to data: $p0" )
        }
        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
        override fun onAttributionFailure(p0: String?) {}
    }

    suspend fun getData(): String? {
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://pro.ip-api.com/")
            .build()
            .create(ApiInterface::class.java)

        return retrofitBuilder.getData().body()?.countryCode
    }

    suspend fun getDataDev(): String? {
        val retroBuildTwo = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://wildmonkey.xyz/")
            .build()
            .create(ApiInterface::class.java)

        val linkView = retroBuildTwo.getDataDev().body()?.view
        val appsChecker = retroBuildTwo.getDataDev().body()?.appsChecker

        Hawk.put(appsCheck, appsChecker)
        Hawk.put(link, linkView)

        return retroBuildTwo.getDataDev().body()?.geo
    }

//    override fun onCleared() {
//        super.onCleared()
//        Log.e("Clear","VM Cleared")
//    }
}