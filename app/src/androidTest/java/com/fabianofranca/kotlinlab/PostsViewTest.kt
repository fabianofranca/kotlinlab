package com.fabianofranca.kotlinlab

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.fabianofranca.injektor.Default
import com.fabianofranca.injektor.SESSION
import com.fabianofranca.injektor.provide
import com.fabianofranca.kotlinlab.presentation.posts.PostTitleViewHolder
import com.fabianofranca.kotlinlab.presentation.posts.PostsActivity
import com.fabianofranca.kotlinlab.presentation.posts.contracts.PostsPresenter
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class PostsViewTest {

    @Mock
    lateinit var presenter: PostsPresenter

    @get:Rule
    val activityRule = ActivityTestRule(PostsActivity::class.java, false, false)

    @Before
    fun before() {
        Default.clear()
        Posts.clear()

        MockitoAnnotations.initMocks(this)

        provide(SESSION, Posts) { presenter }

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

        onView(withId(R.id.recycler_view_posts)).perform(
            RecyclerViewActions.scrollToPosition<PostTitleViewHolder>(
                1
            )
        )

        onView(withId(R.id.recycler_view_posts)).check(matches(allOf(hasDescendant(withText("Teste 1")))))
    }
}
