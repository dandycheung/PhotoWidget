package com.qihuan.photowidget.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.qihuan.photowidget.R
import com.qihuan.photowidget.bean.WidgetBean
import com.qihuan.photowidget.common.WidgetType
import com.qihuan.photowidget.databinding.ItemWidgetInfoBinding
import com.qihuan.photowidget.ktx.load

/**
 * WidgetPagingAdapter
 * @author qi
 * @since 3/29/21
 */
class WidgetPagingAdapter :
    PagingDataAdapter<WidgetBean, WidgetPagingAdapter.ViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<WidgetBean>() {
        override fun areItemsTheSame(oldItem: WidgetBean, newItem: WidgetBean): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WidgetBean, newItem: WidgetBean): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(
        private val binding: ItemWidgetInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(bindingAdapterPosition, it)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: WidgetBean?) {
            if (item != null) {
                val imageList = item.imageList
                val widgetInfo = item.widgetInfo
                if (imageList.isNotEmpty()) {
                    binding.ivWidgetPicture.load(imageList.first().imageUri)
                } else {
                    binding.ivWidgetPicture.setImageResource(R.drawable.ic_round_broken_image_24)
                }
                binding.tvTitle.text = "ID: ${widgetInfo.widgetId}"
                binding.ivGifTag.isVisible = item.widgetInfo.widgetType == WidgetType.GIF
            }
        }
    }

    private var onItemClickListener: ((Int, View) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWidgetInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setOnItemClickListener(listener: ((Int, View) -> Unit)) {
        this.onItemClickListener = listener
    }
}