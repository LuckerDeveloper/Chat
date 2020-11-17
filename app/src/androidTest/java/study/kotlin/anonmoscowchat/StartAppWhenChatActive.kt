package study.kotlin.anonmoscowchat

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import study.kotlin.anonmoscowchat.ui.MainActivity


@RunWith(AndroidJUnit4::class)
class StartAppWhenChatActive{

    @JvmField
    @Rule
    val rule  = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun checkButtonTextOnStart(){
        Espresso.onView(withId(R.id.start_chat_button)).check(matches(withText(R.string.start_searching_button_text)))
    }

    @Test
    fun checkButtonTextAfterClick(){
        Thread.sleep(3000)
        Espresso.onView(withId(R.id.start_chat_button)).perform(click())
        Espresso.onView(withId(R.id.start_chat_button)).check(matches(withText(R.string.stop_searching_button_text)))
    }
}