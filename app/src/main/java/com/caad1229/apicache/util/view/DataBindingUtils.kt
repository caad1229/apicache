package com.caad1229.apicache.util.view

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

class DataBindingUtils {
    companion object {
        @BindingAdapter("loadImg")
        @JvmStatic
        fun setGlideImage(imageView: ImageView, url: String) {
            if (url.isEmpty()) return

            Glide.with(imageView.context)
                    .load(url)
                    .into(imageView)
        }
    }
}