package addam.com.my.skeletonApp.utilities.observables

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.databinding.BaseObservable

/**
 * Created by Addam on 7/1/2019.
 */
class ObservableBackground: BaseObservable() {
    var mDrawableResource: Int? = null
        private set
    var mColorResource: Int? = null
        private set
    var mColorValue: Int? = null
        private set
    var mDrawable: Drawable? = null
        private set
    var mBitmap: Bitmap? = null
        private set
    var mText: String? = null
        private set

    // Calling View.setBackgroundColor(int) removes the rounded corners
    // instead use CardView.setCardBackgroundColor(int)
    var isCardView = false

    private fun reset() {
        mDrawableResource = null
        mColorResource = null
        mColorValue = null
        mDrawable = null
        isCardView = false
        mText = null
    }

    fun setDrawableResource(@DrawableRes drawableResource: Int) {
        reset()
        mDrawableResource = drawableResource
        notifyChange()
    }

    fun setColorResource(@ColorRes colorResource: Int) {
        reset()
        mColorResource = colorResource
        notifyChange()
    }

    fun setCardBackground(@ColorRes colorResource: Int) {
        reset()
        mColorResource = colorResource
        isCardView = true
        notifyChange()
    }

    fun setColorValue(colorValue: Int) {
        reset()
        mColorValue = colorValue
        notifyChange()
    }

    fun setDrawable(drawable: Drawable) {
        reset()
        mDrawable = drawable
        notifyChange()
    }

    fun setBitmap(bitmap: Bitmap) {
        reset()
        mBitmap = bitmap
        notifyChange()
    }

    fun setText(txt: String) {
        reset()
        mText = txt
        notifyChange()
    }

    fun clear() {
        reset()
        notifyChange()
    }
}