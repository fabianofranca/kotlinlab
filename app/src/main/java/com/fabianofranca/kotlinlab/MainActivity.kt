package com.fabianofranca.kotlinlab

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fabianofranca.kotlinlab.presentation.posts.PostsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btnPosts.setOnClickListener {
            startActivity(Intent(this, PostsActivity::class.java))
        }

        btnGlue.setOnClickListener {
            startActivity(Intent(this, GlueActivity::class.java))
        }
    }
}