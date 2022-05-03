package com.sdk.monitortraits

import android.content.Context
import android.nfc.NfcManager
import androidx.lifecycle.LifecycleOwner
import com.sdk.monitortraits.core.Runtime
import com.sdk.monitortraits.core.RuntimeTraits
import com.sdk.monitortraits.core.model.TraitState
import com.sdk.monitortraits.features.CompoundTrait
import com.sdk.monitortraits.features.ConnectivityRuntimeTraits
import com.sdk.monitortraits.features.LifeOwnerCycleRuntimeTraits
import com.sdk.monitortraits.features.NfcRuntimeTraits
import java.lang.ref.WeakReference

class AndroidRuntimeMonitor(private val context: Context) : Runtime {

    private val _context: WeakReference<Context> by lazy {
        WeakReference(context)
    }

    /***
     * Life Cycle Visibility
     */
    override val visibility: RuntimeTraits<Boolean>
        get() = createLifeCycleTraits()

    private fun createLifeCycleTraits(): RuntimeTraits<Boolean> {
        val lifeOwnerCycleRuntimeTraits = LifeOwnerCycleRuntimeTraits(context as LifecycleOwner)
        if (_context.get() is LifecycleOwner) {
            (_context.get() as LifecycleOwner).lifecycle.addObserver(lifeOwnerCycleRuntimeTraits)
        }
        return lifeOwnerCycleRuntimeTraits
    }

    /***
     * NFC
     */
    override val nfc: RuntimeTraits<Boolean>
        get() = createNfcRuntimeTraits()

    private fun createNfcRuntimeTraits() : RuntimeTraits<Boolean>{
        val nfcManager : NfcManager? = context.getSystemService(Context.NFC_SERVICE) as? NfcManager
        return NfcRuntimeTraits(_context.get() as? LifecycleOwner, nfcManager)
    }

    /***
     * Connectivity
     */
    override val connectivity: RuntimeTraits<Boolean>
        get() = ConnectivityRuntimeTraits(_context.get())


    /***
     * All Traits available
     */
    override fun compoundTraits(): RuntimeTraits<List<TraitState>> {
        return CompoundTrait(nfc, visibility, connectivity)
    }



}