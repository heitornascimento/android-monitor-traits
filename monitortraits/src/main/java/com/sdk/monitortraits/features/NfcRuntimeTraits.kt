package com.sdk.monitortraits.features

import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.sdk.monitortraits.core.DELAY
import com.sdk.monitortraits.core.RuntimeTraits
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.util.concurrent.atomic.AtomicBoolean

class NfcRuntimeTraits (private var context: LifecycleOwner?, private  var nfcManager: NfcManager?) :
    RuntimeTraits<Boolean> {

    private val _enabled = AtomicBoolean(false)
    private var nfcAdapter: NfcAdapter? = null

    override val name: String
        get() = "nfc"
    override val valid: Boolean
        get() = _enabled.get()

    init {
        _enabled.set(nfcManager != null)
    }

    override fun updates() = flow {
        if (nfcManager != null) {
            nfcAdapter = nfcManager?.defaultAdapter
            while (nfcAdapter != null) {
                delay(DELAY)
                emit(nfcAdapter?.isEnabled ?: false)
            }

            if (nfcAdapter == null) {
                emit(false)
            }
        }
    }.flowWithLifecycle((context as LifecycleOwner).lifecycle, Lifecycle.State.RESUMED)

}
