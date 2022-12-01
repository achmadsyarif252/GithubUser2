package com.example.githubuser2.ui

import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.githubuser2.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun assetGetUserData() {
        Espresso.onView(ViewMatchers.withId(R.id.search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(androidx.appcompat.R.id.search_src_text))
            .perform(ViewActions.typeText("muhibbudins"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(androidx.appcompat.R.id.search_src_text))
            .perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER))

    }
}
