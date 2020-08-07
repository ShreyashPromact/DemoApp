package com.app.wiprodemo.ui.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.wiprodemo.R
import com.app.wiprodemo.data.db.entities.Post
import com.app.wiprodemo.util.Coroutines
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class PostActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val postFactory: PostsViewModelFactory by instance()
    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postViewModel = ViewModelProviders.of(this, postFactory).get(PostViewModel::class.java)

        pull_to_refresh.setOnRefreshListener {
            fetchData()
        }

        // need to load data for first time
        fetchData()
    }

    private fun fetchData() = Coroutines.main {
        pull_to_refresh.isRefreshing = true
        postViewModel.posts.await().observe(this, Observer {
            pull_to_refresh.isRefreshing = false
            initRecyclerView(it.toPostItem())
        })
    }

    private fun initRecyclerView(postItem: List<PostItem>) {
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(postItem)
        }

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun List<Post>.toPostItem() : List<PostItem>{
        return this.map {
            PostItem(it)
        }
    }
}