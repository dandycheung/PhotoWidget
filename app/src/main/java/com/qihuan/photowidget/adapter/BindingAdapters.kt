package com.qihuan.photowidget.adapter

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.*
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.Slider
import com.qihuan.photowidget.common.LinkType
import com.qihuan.photowidget.common.RadiusUnit
import com.qihuan.photowidget.ktx.calculateRadiusPx
import com.qihuan.photowidget.ktx.dp
import com.qihuan.photowidget.ktx.loadRounded
import com.qihuan.photowidget.ktx.performHapticFeedback
import com.qihuan.photowidget.view.SliderSelectionView
import com.qihuan.photowidget.view.TextSelectionView


/**
 * BindingAdapters
 * @author qi
 * @since 12/16/20
 */
@BindingMethods(
    BindingMethod(
        type = SliderSelectionView::class,
        attribute = "sliderSelectionValue",
        method = "setValue"
    ),
    BindingMethod(
        type = SliderSelectionView::class,
        attribute = "sliderSelectionValueTo",
        method = "setValueTo"
    ),
    BindingMethod(
        type = SliderSelectionView::class,
        attribute = "sliderSelectionValueUnit",
        method = "setValueUnit"
    ),
)
object BindingAdapters {

    @JvmStatic
    @InverseBindingAdapter(attribute = "android:value", event = "sliderAttrChanged")
    fun getSliderValue(view: Slider): Float {
        return view.value
    }

    @JvmStatic
    @BindingAdapter("sliderAttrChanged")
    fun setSliderListeners(view: Slider, attrChange: InverseBindingListener) {
        view.addOnChangeListener { _, _, _ ->
            attrChange.onChange()
        }
    }

    @JvmStatic
    @BindingAdapter("android:alpha")
    fun setAlpha(view: View, value: Float) {
        view.alpha = value
    }

    @JvmStatic
    @BindingAdapter("cardCornerRadius")
    fun setCardCornerRadius(view: MaterialCardView, value: Float) {
        view.radius = value.dp.toFloat()
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun setVisible(view: View, value: Boolean) {
        view.isVisible = value
    }

    @JvmStatic
    @BindingAdapter("linkTypeIcon")
    fun linkTypeIcon(view: TextView, type: LinkType?) {
        if (type == null) {
            return
        }
        view.setCompoundDrawablesWithIntrinsicBounds(type.icon, 0, 0, 0)
    }

    @JvmStatic
    @BindingAdapter("strokeWidth")
    fun setStrokeWidth(view: MaterialCardView, value: Float) {
        view.strokeWidth = value.dp
    }

    @JvmStatic
    @BindingAdapter("textSelectionContent")
    fun setTextSelectionContent(view: TextSelectionView, value: String) {
        view.setContent(value)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "sliderSelectionValue", event = "sliderSelectionAttrChanged")
    fun getSliderSelectionValue(view: SliderSelectionView): Float {
        return view.getValue()
    }

    @JvmStatic
    @BindingAdapter("sliderSelectionAttrChanged")
    fun setSliderSelectionListeners(view: SliderSelectionView, attrChange: InverseBindingListener) {
        view.addOnChangeListener { slider, _, fromUser ->
            attrChange.onChange()
            if (fromUser) {
                slider.performHapticFeedback()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("imagePath", "imageRadius", "imageRadiusUnit", requireAll = false)
    fun loadImage(
        view: ImageView,
        imagePath: Uri?,
        imageRadius: Float,
        imageRadiusUnit: RadiusUnit
    ) {
        if (imagePath == null) {
            return
        }
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeFile(imagePath.path, options)
        val radiusPx =
            calculateRadiusPx(options.outWidth, options.outHeight, imageRadius, imageRadiusUnit)
        view.loadRounded(imagePath, radiusPx)
    }

    @JvmStatic
    @BindingAdapter("cardCornerRadius", "cardCornerRadiusUnit", requireAll = false)
    fun setCardCornerRadius(view: MaterialCardView, radius: Float, unit: RadiusUnit) {
        view.post {
            val radiusPx = calculateRadiusPx(view.width, view.height, radius, unit)
            view.radius = radiusPx.toFloat()
        }
    }
}