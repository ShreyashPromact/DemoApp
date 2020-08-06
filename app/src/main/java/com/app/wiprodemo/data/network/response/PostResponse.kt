package com.app.wiprodemo.data.network.response

import com.app.wiprodemo.data.db.entities.Post

data class PostResponse (
    val posts: List<Post>
)