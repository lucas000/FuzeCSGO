package com.fuze.csgo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fuze.csgo.other.Status
import com.fuze.csgo.repository.FakeFuzeRepository
import com.fuze.csgo.ui.FuzeViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FuzeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: FuzeViewModel

    @Before
    fun setup() {
        viewModel = FuzeViewModel(FakeFuzeRepository())
    }

    @Test
    fun `get list matches`() {
        viewModel.getMatchesList()

        val value = viewModel.matches
        assertThat(value.value?.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}
