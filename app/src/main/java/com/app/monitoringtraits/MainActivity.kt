package com.app.monitoringtraits


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sdk.monitortraits.AndroidRuntimeMonitor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {


    private val androidRuntimeMonitor: AndroidRuntimeMonitor by lazy {
        AndroidRuntimeMonitor(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        runCompoundTraits()
//        runNfcTraits()
//        runConnectivityTraits()
//        runVisibilityTraits()
    }

    private fun runCompoundTraits() {
        lifecycleScope.launchWhenResumed {
            androidRuntimeMonitor.compoundTraits().updates()
                .collect {
                    val lc = it[0]
                    Log.d("traits","LIFE ${lc.isEnabled}")

                    val nfc = it[1]
                    Log.d("traits","NFC ${nfc.isEnabled}")

                    val conn = it[2]
                    Log.d("traits","CONNECTION ${conn.isEnabled}")
                }
        }
    }

    private fun runNfcTraits() {
        lifecycleScope.launchWhenCreated {
            Log.d("traits","NFC ENABLED -${androidRuntimeMonitor.nfc.valid}")
            androidRuntimeMonitor.nfc.updates().collect {
                Log.d("traits","NFC $it")
            }
        }
    }

    private fun runVisibilityTraits() {
        lifecycleScope.launchWhenCreated {
            Log.d("traits","LIFE IS VALID $androidRuntimeMonitor.visibility.valid")
            androidRuntimeMonitor.visibility.updates().collect {
                Log.d("traits","LIFE $it")
            }
        }
    }

    private fun runConnectivityTraits() {
        lifecycleScope.launchWhenCreated {
            Log.d("traits","CONNECTION IS VALID ${androidRuntimeMonitor.connectivity}.valid")
            androidRuntimeMonitor.connectivity.updates().collect {
                Log.d("traits","CONNECTION $it")
            }
        }
    }


}