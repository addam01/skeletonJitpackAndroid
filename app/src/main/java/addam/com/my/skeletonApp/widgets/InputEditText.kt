package addam.com.my.skeletonApp.widgets


import addam.com.my.skeletonApp.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.AppCompatDrawableManager
import android.util.AttributeSet

/**
 * Created by Arman on 11/9/2017.
 */

open class InputEditText : android.support.design.widget.TextInputEditText {
    enum class InputEditTextState {
        NORMAL,
        FOCUSED,
        ERROR,
        VALIDATED,
        DISABLED
    }

    private val ctx: Context
    private var state = InputEditTextState.NORMAL
    private var normalBackground = AppCompatResources.getDrawable(context, R.drawable.edit_text_normal)
    private var errorBackground = AppCompatResources.getDrawable(context, R.drawable.edit_text_error)

    constructor(context: Context) : super(context) {
        state = InputEditTextState.NORMAL
        this.ctx = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        state = InputEditTextState.NORMAL
        this.ctx = context
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        state = InputEditTextState.NORMAL
        this.ctx = context
    }

    override fun getBackground(): Drawable? = when (state) {
        InputEditText.InputEditTextState.FOCUSED -> AppCompatResources.getDrawable(context, R.drawable.edit_text_focus)
        InputEditText.InputEditTextState.ERROR -> errorBackground
        InputEditText.InputEditTextState.DISABLED -> AppCompatResources.getDrawable(context, R.drawable.edit_text_disabled)
        else -> normalBackground
    }

    fun updateState(state: InputEditTextState?) {
        if (state != null) {
            this.state = state
        }

        updateTextColor()
//        setBackground(background)//setBackground has moved to it's parent layout
    }

    @SuppressLint("RestrictedApi")
    private fun updateTextColor() {
        when (state) {
            InputEditText.InputEditTextState.ERROR -> {
                setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                setTextColor(ContextCompat.getColor(context, R.color.colorRed))
            }
            InputEditText.InputEditTextState.VALIDATED -> {
                val drawable = AppCompatDrawableManager.get().getDrawable(ctx, R.drawable.ic_validate_success)
                setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
                setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }
            else -> {
                setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }
        }
    }

    fun setNormalBackground(normalBackground: Drawable) {
        this.normalBackground = normalBackground
    }

    fun setErrorBackground(errorBackground: Drawable) {
        this.errorBackground = errorBackground
    }
}