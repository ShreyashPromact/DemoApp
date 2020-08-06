package com.app.wiprodemo.ui.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.wiprodemo.R
import com.app.wiprodemo.data.db.entities.Post
import com.app.wiprodemo.util.Coroutines
import com.app.wiprodemo.util.hide
import com.app.wiprodemo.util.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class PostActivity : AppCompatActivity(), KodeinAware {

    val TAG: String? = PostActivity::class.simpleName
    var startTime: Long? = 0
    var endTime: Long? = 0;


    override val kodein by kodein()
    private val postFactory: PostsViewModelFactory by instance()
    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postViewModel = ViewModelProviders.of(this, postFactory).get(PostViewModel::class.java)

    }

    private fun fetchData() = Coroutines.main {
        progress_bar.show()
        postViewModel.posts.await().observe(this, Observer {
            progress_bar.hide()
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