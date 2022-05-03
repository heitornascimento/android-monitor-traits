package com.sdk.monitortraits.core

import com.sdk.monitortraits.core.model.TraitState
import kotlinx.coroutines.flow.Flow

interface Runtime {
    val visibility: RuntimeTraits<Boolean>

    val nfc: RuntimeTraits<Boolean>

    val connectivity : RuntimeTraits<Boolean>

    fun compoundTraits() : RuntimeTraits<List<TraitState>>
}

