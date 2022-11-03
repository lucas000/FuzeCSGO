package com.fuze.csgo.ui.fragment

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.MediumTest
import com.fuze.csgo.getOrAwaitValue
import com.fuze.csgo.launchFragmentInHiltContainer
import com.fuze.csgo.ui.FuzeViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.test.espresso.Espresso.pressBack
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.mockito.Mockito.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class DetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun loadDetailsMatchFromRepository() {
        var testViewModel: FuzeViewModel? = null

        val bundle = Bundle().apply {
            putSerializable("matchItem", "1")
        }

        launchFragmentInHiltContainer<DetailsFragment>(
            fragmentArgs = bundle
        ) {
            testViewModel = viewModel

            viewModel?.getTeamMembers(128940, 130522)
        }

        assertThat(testViewModel?.teams?.getOrAwaitValue()).isNotNull()
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<DetailsFragment>{
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()
        verify(navController).popBackStack()
    }
}