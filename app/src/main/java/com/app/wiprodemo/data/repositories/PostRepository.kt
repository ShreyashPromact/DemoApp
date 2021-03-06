package com.app.wiprodemo.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.wiprodemo.data.db.AppDatabase
import com.app.wiprodemo.data.db.entities.Post
import com.app.wiprodemo.data.network.MyApi
import com.app.wiprodemo.data.network.SafeRequest
import com.app.wiprodemo.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeRequest() {

    private val posts = MutableLiveData<List<Post>>()

    init {
        posts.observeForever {
            savePosts(it)
        }
    }

    suspend fun getPosts(): LiveData<List<Post>> {
        return withContext(Dispatchers.IO) {
            fetchPosts()
            db.getPostDao().getPosts()
        }
    }

    private suspend fun fetchPosts() {
        try {
            val response = apiRequest { api.getPostsResponse() }
            posts.postValue(response.rows)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun savePosts(posts: List<Post>) {
        Coroutines.io {
            // Right now we don't have unique key as ID in this Post object
            // That's why we are clearing all data before adding it.
            // So we don't have duplicate Posts.
            db.clearAllTables() // This needs to be removed once we have unique key for the Post
            db.getPostDao().saveAllPosts(posts)
        }
    }
}