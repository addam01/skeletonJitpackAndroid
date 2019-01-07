package addam.com.my.skeletonApp.utilities

import addam.com.my.skeletonApp.utilities.observables.ObservableBackground
import android.databinding.BindingAdapter
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.view.View
import android.widget.ImageView

/**
 * Created by Addam on 7/1/2019.
 */
@BindingAdapter("android:background")
fun setBackground(view: View, observable: ObservableBackground) {
    if (observable.mDrawableResource != null)
        observable.mDrawableResource?.let { view.setBackgroundResource(it) }
    else if (observable.mColorResource != null)
        observable.mColorResource?.let {
            val color = ContextCompat.getColor(view.context, it)
            if (observable.isCardView && view is CardView) {
                view.setCardBackgroundColor(color)
            } else {
                view.setBackgroundColor(color)
            }
        }
    else if (observable.mColorValue != null)
        observable.mColorValue?.let {
            if (observable.isCardView && view is CardView) {
                view.setCardBackgroundColor(it)
            } else {
                view.setBackgroundColor(it)
            }
        }
    else if (observable.mDrawable != null) {
        observable.mDrawable?.let {
            view.background = it
        }
    } else if (observable.mBitmap != null) {
        observable.mBitmap?.let {
            view.background = BitmapDrawable(view.context.resources, it)
        }
    } else {
        view.setBackgroundResource(0)
    }
}

@BindingAdapter("android:background")
fun setBackground(view: View, drawable: Drawable?) {
    drawable?.let {
        view.background = drawable
    }
}

@BindingAdapter("srcCompat")
fun setSrcCompat(view: ImageView, observable: ObservableBackground) {
    if (observable.mDrawableResource != null)
        observable.mDrawableResource?.let { view.setImageResource(it) }
    else if (observable.mColorResource != null)
        observable.mColorResource?.let {
            val color = ContextCompat.getColor(view.context, it)
            view.setBackgroundColor(color)
        }
    else if (observable.mColorValue != null)
        observable.mColorValue?.let { view.setBackgroundColor(it) }
    else if (observable.mDrawable != null) {
        observable.mDrawable?.let {
            view.setImageDrawable(it)
        }
    } else if (observable.mBitmap != null) {
        observable.mBitmap?.let {
            view.setImageBitmap(it)
        }
    } else {
        view.setImageResource(0)
    }
}

@BindingAdapter("srcDrawable")
fun setSrcDrawable(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        view.visibility = View.VISIBLE
        val resId = view.resources.getIdentifier(url, "drawable", view.context.packageName)
        val drawable = ContextCompat.getDrawable(view.context, resId)
        view.setImageDrawable(drawable)
    } else {
        view.visibility = View.GONE
    }
}
