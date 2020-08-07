package com.app.wiprodemo.ui.posts

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.app.wiprodemo.R
import com.app.wiprodemo.data.db.entities.Post
import com.app.wiprodemo.databinding.ItemPostBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.xwray.groupie.databinding.BindableItem

class PostItem(val post: Post) : BindableItem<ItemPostBinding>(){

    override fun getLayout() = R.layout.item_post

    override fun bind(viewBinding: ItemPostBinding, position: Int) {
        viewBinding.setPost(post)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(view.context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_default_image)
                    .error(R.drawable.ic_default_image)
                    .into(view)
            }
        }
    }
}