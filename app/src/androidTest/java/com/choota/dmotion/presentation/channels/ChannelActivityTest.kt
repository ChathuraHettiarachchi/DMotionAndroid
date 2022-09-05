package com.choota.dmotion.presentation.channels


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.choota.dmotion.R
import com.choota.dmotion.di.AppModule
import com.choota.dmotion.util.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.*
import org.junit.runner.RunWith

@LargeTest
@UninstallModules(AppModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ChannelActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule: ActivityScenarioRule<ChannelActivity> =
        ActivityScenarioRule(ChannelActivity::class.java)

    @After
    fun unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun test_activity_channel_shimmer_isInView(){
        hiltRule.inject()
        val activityScenario = ActivityScenario.launch(ChannelActivity::class.java)
        onView(withId(R.id.viewShimmer)).check(matches(isDisplayed()))
    }

    @Test
    fun test_activity_channel_shimmer_gone_and_channels_isInView(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        hiltRule.inject()
        onView(withId(R.id.viewShimmer)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.recyclerChannels)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun test_channel_video_list_activity_is_visible(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        hiltRule.inject()
        onView(withId(R.id.recyclerChannels)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.recyclerChannels)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ChannelAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.txtTitleVideoList)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun test_back_navigation_to_channel_list_from_video_list(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        hiltRule.inject()
        onView(withId(R.id.recyclerChannels)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.recyclerChannels)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ChannelAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.txtTitleVideoList)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        pressBack()

        onView(withId(R.id.recyclerChannels)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

}
