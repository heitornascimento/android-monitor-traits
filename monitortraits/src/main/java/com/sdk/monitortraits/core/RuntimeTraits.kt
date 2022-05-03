package com.sdk.monitortraits.core

import kotlinx.coroutines.flow.Flow

const val DELAY: Long = 1200
interface RuntimeTraits<T>  {

    val name: String

    val valid: Boolean

    fun updates() : Flow<T>
}
