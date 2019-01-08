package addam.com.my.skeletonApp.utilities

import addam.com.my.skeletonApp.R
import android.app.Activity
import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.text.method.PasswordTransformationMethod
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.github.ajalt.timberkt.Timber


/**
 * Created by Arman on 11/9/2017.
 */

class KeyboardManager {
    companion object {
        fun showKeyboard(context: Context) {
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }

        fun hideKeyboard(activity: Activity) {
            try {
                val decorView = activity.window.peekDecorView()
                val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(decorView.windowToken, 0)
            } catch (ex: Exception) {
                Timber.e { ex.message.toString() }
            }

        }

        fun setKeyboard(editText: EditText, keyboardType: Int) {
            setKeyboardType(editText, keyboardType, -1)
        }

        fun setKeyboard(editText: EditText, keyboardType: Int, maxLength: Int) {
            setKeyboardType(editText, keyboardType, maxLength)
        }

        private fun setKeyboardType(editText: EditText, keyboardType: Int, maximumLength: Int) {
            var maxLength = maximumLength
            when (keyboardType) {
                KeyboardType.MYKAD_DEFAULT_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 13
                    }
                    val filters = arrayOf(InputFilter.LengthFilter(maxLength), InputFilter.AllCaps(), TextHelper.createFilter(editText.context.getString(R.string.keyboard_mykad)))
                    editText.filters = filters
                    editText.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    editText.maxLines = 1
                }
                KeyboardType.MYKAD_BASIC_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 12
                    }
                    val filters = arrayOf(InputFilter.LengthFilter(maxLength), InputFilter.AllCaps(), TextHelper.createFilter(editText.context.getString(R.string.keyboard_mykad_basic)))
                    editText.filters = filters
                    editText.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    editText.maxLines = 1
                }
                KeyboardType.MYKAD_ROADTAX_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 50
                    }
                    val filters = arrayOf(InputFilter.LengthFilter(maxLength), InputFilter.AllCaps(), TextHelper.createFilter(editText.context.getString(R.string.keyboard_mykad_organization)))
                    editText.filters = filters
                    editText.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    editText.maxLines = 1
                }
                KeyboardType.PASSPORT -> {
                    if (maxLength < 1) {
                        maxLength = 20
                    }
                    val filters = arrayOf(InputFilter.LengthFilter(maxLength), InputFilter.AllCaps(), TextHelper.createFilter(editText.context.getString(R.string.keyboard_passport)))
                    editText.filters = filters
                    editText.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    editText.maxLines = 1
                }
                KeyboardType.MYKAD_CDL_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 12
                    }
                    val filters = arrayOf(InputFilter.LengthFilter(maxLength), InputFilter.AllCaps(), TextHelper.createFilter(editText.context.getString(R.string.keyboard_mykad_no_asterisk)))
                    editText.filters = filters
                    editText.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    editText.maxLines = 1
                }
                KeyboardType.PHONE_NO_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 11
                    }
                    editText.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
                    editText.keyListener = DigitsKeyListener.getInstance(editText.context.getString(R.string.keyboard_number_only))
                    editText.maxLines = 1
                    editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
                }
                KeyboardType.EMAIL_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 60
                    }
                    val filters = arrayOf(InputFilter.LengthFilter(maxLength), TextHelper.createFilter(editText.context.getString(R.string.keyboard_email)))
                    editText.filters = filters
                    editText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    editText.maxLines = 1
                }
                KeyboardType.POSTCODE_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 5
                    }
                    editText.inputType = InputType.TYPE_CLASS_NUMBER
                    editText.maxLines = 1
                    editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
                }
                KeyboardType.CITY_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 30
                    }
                    val filters = arrayOf(InputFilter.LengthFilter(maxLength), TextHelper.createFilter(editText.context.getString(R.string.keyboard_city)))
                    editText.filters = filters
                    editText.inputType = InputType.TYPE_CLASS_TEXT
                    editText.maxLines = 1
                }
                KeyboardType.ADDRESS_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 50
                    }
                    editText.inputType = InputType.TYPE_CLASS_TEXT
                    val filters = arrayOf(InputFilter.LengthFilter(maxLength), TextHelper.createFilter(editText.context.getString(R.string.keyboard_address)))
                    editText.filters = filters
                    editText.maxLines = 1
                }
                KeyboardType.CAR_NO_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 20
                    }
                    val filters = arrayOf(InputFilter.LengthFilter(maxLength), InputFilter.AllCaps(), TextHelper.createFilter(editText.context.getString(R.string.keyboard_car_reg_no)))
                    editText.filters = filters
                    editText.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    editText.maxLines = 1
                }
                KeyboardType.NAME_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 100
                    }
                    val filters = arrayOf(InputFilter.LengthFilter(maxLength), TextHelper.createFilter(editText.context.getString(R.string.keyboard_name)))
                    editText.filters = filters
                    editText.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    editText.maxLines = 1
                }
                KeyboardType.PASSWORD_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 40
                    }
                    val filters = arrayOf(InputFilter.LengthFilter(maxLength), TextHelper.createFilter(editText.context.getString(R.string.keyboard_password)))
                    editText.filters = filters
                    editText.transformationMethod = PasswordTransformationMethod.getInstance()
                    editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    editText.maxLines = 1
                }
                // Max Length 60 for login
                KeyboardType.LOGIN_USERNAME_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 60
                    }
                    val filters = arrayOf(InputFilter.LengthFilter(maxLength), TextHelper.createFilter(editText.context.getString(R.string.keyboard_username)))
                    editText.filters = filters
                    editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                    editText.maxLines = 1
                }
                // MIN 6 & MAX 10, Alphanumeric
                KeyboardType.NEW_USERNAME_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 14
                    }
                    val filters = arrayOf(InputFilter.LengthFilter(maxLength), TextHelper.createFilter(editText.context.getString(R.string.keyboard_username_new)))
                    editText.filters = filters
                    editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                    editText.maxLines = 1
                }
                KeyboardType.TRANSACTION_ID_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 12
                    }
                    editText.inputType = InputType.TYPE_CLASS_NUMBER
                    editText.maxLines = 1
                    editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
                }
                KeyboardType.MYKAD_PASSPORT_ARMY_POLICE -> {
                    if (maxLength < 1) {
                        maxLength = 12
                    }
                    val filters = arrayOf(InputFilter.LengthFilter(maxLength), TextHelper.createFilter(editText.context.getString(R.string.keyboard_mykad_passport_army_police)))
                    editText.filters = filters
                    editText.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    editText.maxLines = 1
                }
                KeyboardType.CREDIT_CARD_KEYBOARD -> {
                    if (maxLength < 1) {
                        maxLength = 16
                    }
                    editText.inputType = InputType.TYPE_CLASS_NUMBER
                    editText.maxLines = 1
                    editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
                }
                else -> {
                    if (maxLength < 1) {
                        maxLength = 200
                    }
                    editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
                    editText.inputType = InputType.TYPE_CLASS_TEXT
                }
            }
        }
    }
}

internal interface KeyboardType {
    companion object {

        val NO_KEYBOARD = 0
        val MYKAD_DEFAULT_KEYBOARD = 1
        val MYKAD_BASIC_KEYBOARD = 2
        val MYKAD_ROADTAX_KEYBOARD = 3
        val PASSPORT = 4
        val PHONE_NO_KEYBOARD = 5
        val EMAIL_KEYBOARD = 6
        val POSTCODE_KEYBOARD = 7
        val CITY_KEYBOARD = 8
        val ADDRESS_KEYBOARD = 9
        val CAR_NO_KEYBOARD = 10
        val NAME_KEYBOARD = 11
        val PASSWORD_KEYBOARD = 12
        val LOGIN_USERNAME_KEYBOARD = 13
        val NEW_USERNAME_KEYBOARD = 14
        val MYKAD_CDL_KEYBOARD = 15
        val TRANSACTION_ID_KEYBOARD = 16
        val MYKAD_PASSPORT_ARMY_POLICE = 17
        val CREDIT_CARD_KEYBOARD = 18
    }
}