package com.app.wiprodemo.ui.posts

import androidx.lifecycle.ViewModel
import com.app.wiprodemo.data.repositories.PostRepository
import com.app.wiprodemo.util.lazyDeferred

class PostViewModel(repository: PostRepository) : ViewModel() {
    val posts by lazyDeferred {
        repository.getPosts()
    }
}