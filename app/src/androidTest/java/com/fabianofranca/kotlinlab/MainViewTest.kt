package com.fabianofranca.kotlinlab

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.*
import org.hamcrest.Matchers.*
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.fabianofranca.kotlinlab.infrastructure.provide
import com.fabianofranca.kotlinlab.presentation.main.MainActivity
import com.fabianofranca.kotlinlab.presentation.main.PostTitleViewHolder
import com.fabianofranca.kotlinlab.presentation.main.contracts.MainPresenter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class MainViewTest {

    @Mock
    lateinit var presenter: MainPresenter

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        provide(MainPresenter::class) { presenter }

        activityRule.launchActivity(null)
    }

    @Test
    fun loadPostOnCreate_isCorrect() {
        verify(presenter).loadPosts()
    }

    @Test
    fun updatePostList_isCorrect() {

        activityRule.runOnUiThread {
            activityRule.activity.updatePostList(listOf("Teste 1", "Teste 2", "Teste 3"))
        }

        onView(withId(R.id.recycler_view_posts))
            .perform(RecyclerViewActions.scrollToPosition<PostTitleViewHolder>(1))

        onView(withId(R.id.recycler_view_posts))
            .check(matches(allOf(hasDescendant(withText("Teste 1")))))
    }
}
