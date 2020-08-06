package com.app.wiprodemo.ui.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.wiprodemo.data.repositories.PostRepository

class PostsViewModelFactory (
    private val repository: PostRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(repository) as T
    }
}