package com.qihuan.photowidget.core.common

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

import com.qihuan.photowidget.R

/**
 * PhotoWidgetGlideModule
 * @author qi
 * @since 2022/3/2
 */
@GlideModule
class PhotoWidgetGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.setDefaultRequestOptions {
            RequestOptions.placeholderOf(R.color.image_place_holder_color)
                .error(R.drawable.ic_round_broken_image_24)
        }
    }
}
