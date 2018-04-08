package com.fabianofranca.kotlinlab

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.fabianofranca.kotlinlab.presentation.posts.PostsActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.btn_posts)

        button.setOnClickListener {
            startActivity(Intent(this, PostsActivity::class.java))
        }

    }
}