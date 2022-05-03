package com.sdk.monitortraits.features

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.sdk.monitortraits.core.DELAY
import com.sdk.monitortraits.core.RuntimeTraits
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.util.concurrent.atomic.AtomicBoolean

class ConnectivityRuntimeTraits(private var context: Context?) :
    RuntimeTraits<Boolean> {

    private var connectivityManager: ConnectivityManager? = null

    private val _enabled = AtomicBoolean(false)

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _enabled.set(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            _enabled.set(false)
        }
    }

    init {
        connectivityManager =
            context?.getSystemService(ConnectivityManager::class.java)
        connectivityManager?.requestNetwork(networkRequest, networkCallback)
        _enabled.set(connectivityManager != null)
    }

    override val name: String
        get() = "connectivity"
    override val valid: Boolean
        get() = _enabled.get()

    override fun updates() = flow {
        while (connectivityManager != null) {
            delay(DELAY)
            emit(_enabled.get())
        }
    }.flowWithLifecycle((context as LifecycleOwner).lifecycle, Lifecycle.State.RESUMED)

}