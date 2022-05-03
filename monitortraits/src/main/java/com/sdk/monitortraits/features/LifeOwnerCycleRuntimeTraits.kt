package com.sdk.monitortraits.features

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.sdk.monitortraits.core.DELAY
import com.sdk.monitortraits.core.RuntimeTraits
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.util.concurrent.atomic.AtomicBoolean

class LifeOwnerCycleRuntimeTraits(private var lifecycleOwner: LifecycleOwner) : RuntimeTraits<Boolean>,
    DefaultLifecycleObserver {

    private val _enabled = AtomicBoolean(false)

    override val valid: Boolean
        get() = _enabled.get()

    override fun updates() = flow {
        while (true) {
            delay(DELAY)
            emit(valid)
        }
    }.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.RESUMED)

    override val name: String
        get() = "application"

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        _enabled.set(true)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        _enabled.set(true)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        _enabled.set(true)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        _enabled.set(false)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        _enabled.set(false)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        _enabled.set(false)
    }

}