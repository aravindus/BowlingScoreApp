package com.aravind.bowlingscoreapp

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aravind.bowlingscoreapp.presentation.BowlingScoreActivity
import com.aravind.bowlingscoreapp.utils.Constants
import com.aravind.bowlingscoreapp.utils.Constants.Companion.INVALID_FRAME
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BowlingScoreActivityTest {

    private lateinit var gameFrameToEditText: String
    private lateinit var invalidGameFrameInput: String
    private var decorView: View? = null

    @get : Rule
    var activityRule: ActivityScenarioRule<BowlingScoreActivity> =
        ActivityScenarioRule(BowlingScoreActivity::class.java)

    @Before
    fun initialisationForTest() {
        gameFrameToEditText = Constants.PERFECT_GAME_FRAME
        invalidGameFrameInput = INVALID_FRAME
        activityRule.scenario.onActivity { activity: BowlingScoreActivity ->
            decorView = activity.window.decorView
        }
    }

    @Test
    fun testForUIElementsPresentInActivity() {
        onView(withId(R.id.btnScore)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.edtScores)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tvFinalScore)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tvProgress)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tvStatusMessage)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tvTitle)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun inputForPerfectFrameTest() {
        onView(withId(R.id.edtScores)).perform(typeText(gameFrameToEditText))
        onView(withId(R.id.btnScore)).perform(click())
    }

    @Test
    fun inputForInvalidFrameTest() {
        onView(withId(R.id.edtScores)).perform(typeText(invalidGameFrameInput))
        onView(withId(R.id.btnScore)).perform(click())
    }
}