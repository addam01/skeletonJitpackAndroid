package addam.com.my.skeletonApp.widgets

import addam.com.my.skeletonApp.R
import addam.com.my.skeletonApp.utilities.KeyboardManager
import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.InverseBindingListener
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.content.res.AppCompatResources
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.custom_edit_text.view.*
import java.util.*

/**
 * Created by Arman on 11/9/2017.
 */

class CustomEditText : LinearLayout {

    private var validationActions: ValidationActions? = null
    private var state = InputEditText.InputEditTextState.NORMAL
    private var showValidated = true
    private var showFocus = true
    private var isPassword = false
    private var isShowPassword = false
    private var isDob = false
    private var mLastImageShowPasswordResourceId: Int = 0

    companion object {
        @JvmStatic
        @BindingAdapter("android:textAttrChanged")
        fun onTextChangedListener(view: CustomEditText, listener: InverseBindingListener) {
            view.getEditText().addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listener.onChange()
                }
            })
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_edit_text, this, true)
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText, defStyleAttr, 0)

        val headerText = a.getString(R.styleable.CustomEditText_header_text)
        val hint = a.getString(R.styleable.CustomEditText_hint_text)
        val digits = a.getString(R.styleable.CustomEditText_digits)
        val keyboardLayout = a.getInt(R.styleable.CustomEditText_keyboard_layout, 0)
        val hasNextFocus = a.getBoolean(R.styleable.CustomEditText_has_next_focus, false)
        val isEnabled = a.getBoolean(R.styleable.CustomEditText_enabled, true)
        val drawableStart = a.getDrawable(R.styleable.CustomEditText_drawableStart)
        val drawableEnd = a.getDrawable(R.styleable.CustomEditText_drawableEnd)
        showValidated = a.getBoolean(R.styleable.CustomEditText_show_validated, true)
        showFocus = a.getBoolean(R.styleable.CustomEditText_show_focus, true)
        isPassword = a.getBoolean(R.styleable.CustomEditText_is_password, false)
        isShowPassword = a.getBoolean(R.styleable.CustomEditText_show_password, false)
        isDob = a.getBoolean(R.styleable.CustomEditText_is_dob, false)

        var normalBackground = a.getDrawable(R.styleable.CustomEditText_normal_background)
        if (normalBackground == null) {
            normalBackground = AppCompatResources.getDrawable(getContext(), R.drawable.edit_text_normal)
        }

        var errorBackground = a.getDrawable(R.styleable.CustomEditText_error_background)
        if (errorBackground == null) {
            errorBackground = AppCompatResources.getDrawable(getContext(), R.drawable.edit_text_error)
        }

        initializeProperties(headerText, hint, digits, keyboardLayout, hasNextFocus, isEnabled, normalBackground, errorBackground, drawableStart, drawableEnd)
        a.recycle()
        init()
    }

    @TargetApi(21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText, defStyleAttr, defStyleRes)

        val headerText = a.getString(R.styleable.CustomEditText_header_text)
        val hint = a.getString(R.styleable.CustomEditText_hint_text)
        val digits = a.getString(R.styleable.CustomEditText_digits)
        val keyboardLayout = a.getInt(R.styleable.CustomEditText_keyboard_layout, 0)
        val hasNextFocus = a.getBoolean(R.styleable.CustomEditText_has_next_focus, false)
        val isEnabled = a.getBoolean(R.styleable.CustomEditText_enabled, true)
        val drawableStart = a.getDrawable(R.styleable.CustomEditText_drawableStart)
        val drawableEnd = a.getDrawable(R.styleable.CustomEditText_drawableEnd)
        showValidated = a.getBoolean(R.styleable.CustomEditText_show_validated, true)
        showFocus = a.getBoolean(R.styleable.CustomEditText_show_focus, true)
        isPassword = a.getBoolean(R.styleable.CustomEditText_is_password, false)
        isDob = a.getBoolean(R.styleable.CustomEditText_is_dob, false)

        var normalBackground = a.getDrawable(R.styleable.CustomEditText_normal_background)
        if (normalBackground == null) {
            normalBackground = AppCompatResources.getDrawable(getContext(), R.drawable.edit_text_normal)
        }

        var errorBackground = a.getDrawable(R.styleable.CustomEditText_error_background)
        if (errorBackground == null) {
            errorBackground = AppCompatResources.getDrawable(getContext(), R.drawable.edit_text_error)
        }

        initializeProperties(headerText, hint, digits, keyboardLayout, hasNextFocus, isEnabled, normalBackground, errorBackground, drawableStart, drawableEnd)
        a.recycle()
        init()
    }

    private fun initializeProperties(headerText: String?, hint: String?, digits: String?, keyboardLayout: Int, hasNextFocus: Boolean,
                                     isEnabled: Boolean, normalBackground: Drawable, errorBackground: Drawable, drawableStart: Drawable?, drawableEnd: Drawable?) {
        if (TextUtils.isEmpty(headerText)) {
            tv_header.visibility = View.GONE
        } else {
            tv_header.visibility = View.VISIBLE
            tv_header.text = headerText
        }

        KeyboardManager.setKeyboard(iet_content, keyboardLayout)

        if (hasNextFocus) {
            iet_content.imeOptions = EditorInfo.IME_ACTION_NEXT
        } else {
            iet_content.imeOptions = EditorInfo.IME_ACTION_DONE
        }

        setEnabled(isEnabled)
        tv_show.visibility = if (isPassword) View.VISIBLE else View.GONE
        if (!isPassword) iet_content.setPadding(iet_content.paddingLeft, iet_content.paddingTop, 0, iet_content.paddingBottom)

        if (isDob) {
            iet_content.keyListener = null
            iet_content.isEnabled = false
        }

        if (isShowPassword) {
            iet_content.transformationMethod = HideReturnsTransformationMethod.getInstance()
            mLastImageShowPasswordResourceId = R.string.password_hide
            tv_show.setText(mLastImageShowPasswordResourceId)
            iet_content.setSelection(iet_content.text!!.length)
        }


        iet_content.setNormalBackground(normalBackground)
        iet_content.setErrorBackground(errorBackground)

        if (!TextUtils.isEmpty(hint)) {
            iet_content.hint = hint
            iet_content.typeface = ResourcesCompat.getFont(context, R.font.open_sans_italic)
        } else {
            iet_content.typeface = ResourcesCompat.getFont(context, R.font.open_sans_bold)
        }
        if (digits != null && !TextUtils.isEmpty(digits)) {
            iet_content.keyListener = DigitsKeyListener.getInstance(digits)
        }

        setDrawableStart(drawableStart)
        setDrawableEnd(drawableEnd)
    }

    private fun init() {
        state = InputEditText.InputEditTextState.NORMAL

        iet_content.setOnFocusChangeListener { v, hasFocus -> this@CustomEditText.onFocusChange(v, hasFocus) }
        iet_content.setOnEditorActionListener(TextView.OnEditorActionListener(this::onKeyboardAction))
        iet_content.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                this@CustomEditText.afterTextChanged(p0)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                this@CustomEditText.onTextChanged(p0, p1, p2, p3)
            }
        })

        tv_show.setOnClickListener {
            this@CustomEditText.onShowHideText()
        }

        img_end.setOnClickListener {
            if (isDob) {
                openDatePicker()
            }
        }
    }

    fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val mYear = calendar.get(Calendar.YEAR)
        val mMonth = calendar.get(Calendar.MONTH)
        val mDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { p0, year, month, day ->
            val month1 = month + 1
            val date = setMonthWith0(year, month1, day)
            iet_content.setText(date)
        }, mYear, mMonth, mDay)

        datePickerDialog.show()
    }

    private fun setMonthWith0(year: Int, month1: Int, day: Int): String {
        return "$year/${String.format("%02d", month1)}/${String.format("%02d", day)}"
    }

    fun afterTextChanged(s: Editable?) {
        tv_error.visibility = View.GONE
        if (iet_content.isEnabled) {
            state = if (iet_content.hasFocus() && showFocus) InputEditText.InputEditTextState.FOCUSED else InputEditText.InputEditTextState.NORMAL
        } else {
            state = InputEditText.InputEditTextState.DISABLED
        }
        updateEditTextState()
        if (validationActions != null) {
            validationActions!!.onTextChanged()
        }

    }

    fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (iet_content.text.toString().isEmpty()) {
            iet_content.typeface = ResourcesCompat.getFont(context, R.font.open_sans_italic)
        } else {
            iet_content.typeface = ResourcesCompat.getFont(context, R.font.open_sans_bold)
        }

    }

    fun onShowHideText() {
        // Show the value of password field
        if (mLastImageShowPasswordResourceId == R.string.password_hide) {
            mLastImageShowPasswordResourceId = R.string.password_show
            iet_content.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {
            mLastImageShowPasswordResourceId = R.string.password_hide
            iet_content.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }// Hide the value of password field
        tv_show.setText(mLastImageShowPasswordResourceId)
        iet_content.setSelection(iet_content.text!!.length)
    }

    fun setValidationActions(actions: ValidationActions) {
        validationActions = actions
    }

    fun getText(): String {
        return iet_content.text.toString()
    }

    fun setText(content: String) {
        iet_content.setText(content)
    }

    fun getEditText(): EditText {
        return iet_content
    }

    fun setHeaderText(headerText: String) {
        if (!TextUtils.isEmpty(headerText)) {
            tv_header.visibility = View.VISIBLE
            tv_header.text = headerText
        }
    }

    override fun setOnKeyListener(listener: View.OnKeyListener) {
        iet_content.setOnKeyListener(listener)
    }

    fun setError(error: CharSequence?) {
        if (error != null && error.isNotEmpty()) {
            tv_error.text = error
            tv_error.visibility = View.VISIBLE
        }

        state = InputEditText.InputEditTextState.ERROR
        updateEditTextState()
    }

    fun forceRemoveError() {
        tv_error.text = null
        tv_error.visibility = View.GONE
        state = InputEditText.InputEditTextState.NORMAL
    }

    fun clearError() {
        if (state !== InputEditText.InputEditTextState.ERROR) {
            tv_error.text = null
            tv_error.visibility = View.GONE
        }
    }

    //requestFocus is final so renaming it
    fun setFocus() {
        iet_content.requestFocus()
    }

    fun hasError(): Boolean {
        return state === InputEditText.InputEditTextState.ERROR
    }

    fun setHasNextFocus(hasNextFocus: Boolean) {
        if (hasNextFocus) {
            iet_content.imeOptions = EditorInfo.IME_ACTION_NEXT
        } else {
            iet_content.imeOptions = EditorInfo.IME_ACTION_DONE
        }
    }

    override fun setEnabled(isEnabled: Boolean) {
        super.setEnabled(isEnabled)
        iet_content.isEnabled = isEnabled
        if (!isEnabled) {
            state = InputEditText.InputEditTextState.DISABLED
        } else {
            if (iet_content.hasFocus() && showFocus) {
                state = InputEditText.InputEditTextState.FOCUSED
            } else {
                state = InputEditText.InputEditTextState.NORMAL
            }
        }
        updateEditTextState()
    }

    fun setNotEditable(isNotEditable: Boolean) {
        if (isNotEditable) {
            iet_content.isFocusable = false
            iet_content.isCursorVisible = false
            iet_content.isClickable = true
            iet_content.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        } else {
            iet_content.isFocusableInTouchMode = true
            iet_content.isCursorVisible = true
            iet_content.isClickable = false
        }
    }

    private fun onFocusChange(view: View, hasFocus: Boolean) {
        if (!hasFocus) {
            state = InputEditText.InputEditTextState.NORMAL
            if (isPassword && mLastImageShowPasswordResourceId == R.string.password_hide) {
                mLastImageShowPasswordResourceId = R.string.password_show
                iet_content.transformationMethod = PasswordTransformationMethod.getInstance()
                tv_show.setText(mLastImageShowPasswordResourceId)
            }
            validate()

        } else {
            if (showFocus) {
                state = InputEditText.InputEditTextState.FOCUSED
            }
        }
        updateEditTextState()
        if (validationActions != null) {
            validationActions!!.onFocusChanged(this, hasFocus)
        }
    }

    private fun updateBackground(state: InputEditText.InputEditTextState) {
        til_content.background = when (state) {
            InputEditText.InputEditTextState.FOCUSED -> AppCompatResources.getDrawable(context, R.drawable.edit_text_focus)
            InputEditText.InputEditTextState.ERROR -> AppCompatResources.getDrawable(context, R.drawable.edit_text_error)
            InputEditText.InputEditTextState.DISABLED -> AppCompatResources.getDrawable(context, R.drawable.edit_text_disabled)
            else -> AppCompatResources.getDrawable(context, R.drawable.edit_text_normal)
        }
    }

    private fun onKeyboardAction(view: TextView, actionId: Int, keyEvent: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL && keyEvent?.action == KeyEvent.ACTION_DOWN) {
            clearAllFocus()
        } else if (actionId == EditorInfo.IME_ACTION_NEXT) {
            if (validationActions != null) {
                validationActions!!.onNext(this)
            }
        }
        return false
    }

    private fun updateEditTextState() {
        iet_content.updateState(state)
        updateBackground(state)
    }

    private fun clearAllFocus() {
        if (validationActions != null) {
            validationActions!!.onDone(this)
        }
    }

    private fun validate() {
        if (validationActions != null) {
            if (validationActions!!.needsValidation(this)) {
                val isValid = validationActions!!.validate(this)
                if (isValid) {
                    if (showValidated) {
                        state = InputEditText.InputEditTextState.VALIDATED
                        clearError()
                    }
                } else {
                    state = InputEditText.InputEditTextState.ERROR
                }
            }
        }
    }

    fun setKeyboardType(type: Int) {
        setKeyboardType(type, -1)
    }

    fun setKeyboardType(type: Int, maxLength: Int) {
        KeyboardManager.setKeyboard(iet_content, type, maxLength)
    }

    fun setRefreshEditTextState(state: InputEditText.InputEditTextState) {
        this.state = state
        updateEditTextState()
    }

    fun setDrawableStart(drawableStart: Drawable?) {
        img_start.visibility = if (drawableStart == null) {
            View.GONE
        } else {
            img_start.setImageDrawable(drawableStart)
            View.VISIBLE
        }
    }

    fun setDrawableEnd(drawableEnd: Drawable?) {
        img_end.visibility = if (drawableEnd == null) {
            View.GONE
        } else {
            img_end.setImageDrawable(drawableEnd)
            View.VISIBLE
        }
    }

    fun setPasswordShowVisibility(visibility: Int) {
        tv_show.visibility = visibility
    }
}

interface ValidationActions {
    fun needsValidation(view: CustomEditText): Boolean
    fun validate(view: CustomEditText): Boolean
    fun onNext(view: CustomEditText)
    fun onDone(view: CustomEditText)
    fun onFocusChanged(view: CustomEditText, hasFocus: Boolean)
    fun onTextChanged()
}