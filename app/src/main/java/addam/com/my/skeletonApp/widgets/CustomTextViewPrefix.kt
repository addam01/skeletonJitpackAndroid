package addam.com.my.skeletonApp.widgets

import addam.com.my.skeletonApp.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.TextView
import android.text.TextPaint


/**
* Created by Addam on 8/12/2017.
*/
class CustomTextViewPrefix(context: Context, attrs: AttributeSet) : TextView(context, attrs) {

    var prefix : String = ""
    var suffix : String = ""

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextViewPrefix)
        prefix = typeArray.getString(R.styleable.CustomTextViewPrefix_textPrefix)
        suffix = typeArray.getString(R.styleable.CustomTextViewPrefix_textSuffix)
        typeArray.recycle()
    }

    @SuppressLint("SetTextI18n")
    override fun setText(text: CharSequence?, type: BufferType?)  {
        super.setText(prefix + text + suffix, type)
    }
}