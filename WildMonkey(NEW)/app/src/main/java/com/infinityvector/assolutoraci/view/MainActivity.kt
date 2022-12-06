package com.infinityvector.assolutoraci.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.infinityvector.assolutoraci.AppClass.Companion.C1
import com.infinityvector.assolutoraci.AppClass.Companion.MAIN_ID
import com.infinityvector.assolutoraci.AppClass.Companion.appsCheck
import com.orhanobut.hawk.Hawk
import com.infinityvector.assolutoraci.databinding.ActivityMainBinding
import com.infinityvector.assolutoraci.logic.MenuActivity
import com.infinityvector.assolutoraci.viewmodel.MainViewModel
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    // ViewModel
    private lateinit var mainVM: MainViewModel
    private lateinit var bindMainAct: ActivityMainBinding

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainVM = ViewModelProvider(this)[MainViewModel::class.java]

        bindMainAct = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindMainAct.root)

        GlobalScope.launch(Dispatchers.IO) {
            job
        }

        AppsFlyerLib.getInstance().init("ZiA73qzjHqrPRxhC2ZUSLA", mainVM.conversionDataListener, applicationContext)
        AppsFlyerLib.getInstance().start(this)
    }

    private fun getAdId(){
        val adInfo = AdvertisingIdClient(applicationContext)
        adInfo.start()

        val adIdInfo = adInfo.info.id
        Hawk.put(MAIN_ID, adIdInfo)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private val job: Job by lazy {
        GlobalScope.launch(Dispatchers.IO) {
            val countyCode: String = mainVM.getData().toString()
            val countriesPool = mainVM.getDataDev().toString()

            val appsCh: String? = Hawk.get(appsCheck)
            var naming: String? = Hawk.get(C1)

            getAdId()
            if (appsCh == "1") {
                val executorService = Executors.newSingleThreadScheduledExecutor()
                executorService.scheduleAtFixedRate({
                    if (naming != null) {
                        if (naming!!.contains("tdb2") || countriesPool.contains(countyCode)) {
                            executorService.shutdown()
                            startActivity(Intent(this@MainActivity, LogActivity::class.java))
                            finish()
                        } else {
                            executorService.shutdown()
                            startActivity(Intent(this@MainActivity, MenuActivity::class.java))
                            finish()
                        }
                    } else {
                        naming = Hawk.get(C1)
                    }
                }, 0, 2, TimeUnit.SECONDS)
            } else if (countriesPool.contains(countyCode)) {
                startActivity(Intent(this@MainActivity, LogActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@MainActivity, MenuActivity::class.java))
                finish()
            }
        }
    }

}







