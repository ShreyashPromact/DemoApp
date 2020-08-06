package com.app.wiprodemo.ui.posts

import com.app.wiprodemo.R
import com.app.wiprodemo.data.db.entities.Post
import com.app.wiprodemo.databinding.ItemPostBinding
import com.xwray.groupie.databinding.BindableItem

class PostItem(val post: Post) : BindableItem<ItemPostBinding>(){

    override fun getLayout() = R.layout.item_post

    override fun bind(viewBinding: ItemPostBinding, position: Int) {
        viewBinding.setPost(post)
    }
}