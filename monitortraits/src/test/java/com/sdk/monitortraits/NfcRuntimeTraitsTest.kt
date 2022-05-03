package com.sdk.monitortraits

import android.nfc.NfcManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.sdk.monitortraits.core.RuntimeTraits
import com.sdk.monitortraits.features.NfcRuntimeTraits
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class NfcRuntimeTraitsTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var nfcRuntimeTraits: RuntimeTraits<Boolean>
    private val mockContextLifeCycleOwner = mockk<LifecycleOwner>(relaxed = true)
    private val mockNfcManger = mockk<NfcManager>(relaxed = true)

    @Test
    fun `Should traits be valid`() = runBlocking {
        nfcRuntimeTraits = NfcRuntimeTraits(mockContextLifeCycleOwner, mockNfcManger)
        val isEnabled = nfcRuntimeTraits.valid
        assert(isEnabled)
    }

    @Test
    fun `Should trait not be valid`() = runBlocking {
        nfcRuntimeTraits = NfcRuntimeTraits(null, null)
        val isEnabled = nfcRuntimeTraits.valid
        Assert.assertFalse(isEnabled)
    }

}