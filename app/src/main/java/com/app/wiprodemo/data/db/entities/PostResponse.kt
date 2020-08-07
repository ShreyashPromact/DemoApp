package com.app.wiprodemo.data.db.entities

import androidx.room.Entity

@Entity
data class PostResponse(
    val title: String?,
    val rows: ArrayList<Post>
)