package com.sdk.monitortraits.features

import com.sdk.monitortraits.core.RuntimeTraits
import com.sdk.monitortraits.core.model.TraitState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

class CompoundTrait(
    private var nfcRuntimeTraits: RuntimeTraits<Boolean>,
    private var lifeOwnerCycleRuntimeTraits: RuntimeTraits<Boolean>,
    private var connectivityRuntimeTraits: RuntimeTraits<Boolean>
) : RuntimeTraits<List<TraitState>> {

    override val name: String
        get() = "single trait"

    override val valid: Boolean
        get() = nfcRuntimeTraits.valid
                && lifeOwnerCycleRuntimeTraits.valid
                && connectivityRuntimeTraits.valid

    override fun updates(): Flow<List<TraitState>> {
        return lifeOwnerCycleRuntimeTraits.updates()
            .zip(nfcRuntimeTraits.updates()) { lcTrait, nfcTrait ->
                mutableListOf(
                    TraitState(
                        LifeOwnerCycleRuntimeTraits::class.java.simpleName,
                        lifeOwnerCycleRuntimeTraits.valid,
                        lcTrait
                    ),
                    TraitState(
                        NfcRuntimeTraits::class.java.simpleName,
                        nfcRuntimeTraits.valid,
                        nfcTrait
                    )
                )
            }.zip(connectivityRuntimeTraits.updates()) { traitsList, conn ->
                traitsList.plus(
                    TraitState(
                        ConnectivityRuntimeTraits::class.java.simpleName,
                        connectivityRuntimeTraits.valid,
                        conn
                    )
                )
            }
    }
}