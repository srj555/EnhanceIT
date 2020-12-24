package com.sr.myapplication

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sr.myapplication.ui.CardsListActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RecyclerViewTest {
    @get:Rule
    var rule: ActivityTestRule<CardsListActivity> = ActivityTestRule(CardsListActivity::class.java)

    @Before
    fun init() {
        rule.activity
                .supportFragmentManager.beginTransaction()
    }

    @Test
    fun ensureRecyclerViewIsPresent() {
        if (rVcount > 0) {
            // check if displayed
            onView(withId(R.id.item_list))
                    .check(matches(isDisplayed()))

            // check if scroll present
            onView(withId(R.id.item_list))
                    .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

            // assert click
            try {
                // adding delay
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            onView(withId(R.id.item_list))
                    .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }
    }


    private val rVcount: Int
        private get() {
            val recyclerView: RecyclerView = rule.activity.findViewById(R.id.item_list)
            return if (recyclerView.adapter != null) {
                recyclerView.adapter!!.itemCount
            } else 0
        }
}

