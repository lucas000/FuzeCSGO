package com.fuze.csgo.ui.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.MediumTest
import com.fuze.csgo.getOrAwaitValue
import com.fuze.csgo.launchFragmentInHiltContainer
import com.fuze.csgo.ui.FuzeViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.fuze.csgo.R
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.mockito.Mockito.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun loadMatchesListFromRepository() {
        var fuzeViewModel: FuzeViewModel? = null

        launchFragmentInHiltContainer<HomeFragment> {
            fuzeViewModel = viewModel
            viewModel?.getMatchesList()
        }

        assertThat(fuzeViewModel?.matches?.getOrAwaitValue()).isNotNull()
    }

    @Test
    fun clickItemMatch_navigateToDetailsFragment() {
        val navController = mock(NavController::class.java)
        var fuzeViewModel: FuzeViewModel? = null

        launchFragmentInHiltContainer<HomeFragment>{
            Navigation.setViewNavController(requireView(), navController)
            fuzeViewModel = viewModel
            viewModel?.getMatchesList()
            viewModel?.getTeamMembers(128940, 130522)
        }

        onView(withId(R.id.rv_matches_list)).perform(click())
        assertThat(fuzeViewModel?.teams?.getOrAwaitValue()).isNotNull()
    }
}