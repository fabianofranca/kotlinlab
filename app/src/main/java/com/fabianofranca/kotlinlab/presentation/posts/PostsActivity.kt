package com.fabianofranca.kotlinlab.presentation.posts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.fabianofranca.injektor.injection
import com.fabianofranca.injektor.provideSession
import com.fabianofranca.kotlinlab.Posts
import com.fabianofranca.kotlinlab.R
import com.fabianofranca.kotlinlab.presentation.posts.contracts.PostsPresenter
import com.fabianofranca.kotlinlab.presentation.posts.contracts.PostsView

class PostsActivity : AppCompatActivity(), PostsView {

    private val presenter: PostsPresenter by injection(Posts)

    private lateinit var adapter: PostTitlesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        provideSession(Posts) { this@PostsActivity as PostsView }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        setupPosts()

        presenter.loadPosts()
    }

    private fun setupPosts() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_posts)

        recyclerView.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(
            this@PostsActivity,
            LinearLayoutManager.VERTICAL, false
        )

        recyclerView.layoutManager = linearLayoutManager

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@PostsActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        adapter = PostTitlesAdapter()

        recyclerView.adapter = adapter
    }

    override fun updatePostList(posts: List<String>) {
        adapter.update(posts)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

class PostTitleViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    private lateinit var title: TextView

    init {
        itemView?.let {
            title = itemView.findViewById(R.id.title)
        }
    }

    fun bind(value: String) {
        title.text = value
    }
}

class PostTitlesAdapter : RecyclerView.Adapter<PostTitleViewHolder>() {

    private var postTitles = mutableListOf<String>()

    fun update(titles: List<String>) {
        postTitles = mutableListOf()
        postTitles.addAll(titles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostTitleViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.posts_item, parent, false)

        return PostTitleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postTitles.size
    }

    override fun onBindViewHolder(holder: PostTitleViewHolder, position: Int) {
        holder.bind(postTitles[position])
    }
}