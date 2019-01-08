package addam.com.my.skeletonApp.utilities

import android.os.Build
import android.text.*
import android.text.format.DateFormat
import android.widget.TextView
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Arman on 11/9/2017.
 */


class TextHelper {
    companion object {
        fun createFilter(allowedChars: String): InputFilter {
            return object : InputFilter {
                override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
                    var keepOriginal = true
                    val sb = StringBuilder(end - start)
                    for (i in start until end) {
                        val c = source[i]
                        if (isCharAllowed(c))
                            sb.append(c)
                        else
                            keepOriginal = false
                    }
                    if (keepOriginal)
                        return null
                    else {
                        if (source is Spanned) {
                            val sp = SpannableString(sb)
                            TextUtils.copySpansFrom(source, start, sb.length, null, sp, 0)
                            return sp
                        } else {
                            return sb
                        }
                    }
                }

                private fun isCharAllowed(c: Char): Boolean = allowedChars.contains(c.toString())
            }
        }

        @SuppressWarnings("deprecation")
        fun formatHtml(source: String): Spanned {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(source)
            }
        }

        fun formatDate(date: String?, format: String): String {
            val currentFormat = "yyyyMMdd"

            return try {
                val sdf = SimpleDateFormat(currentFormat, Locale.getDefault())
                DateFormat.format(format, sdf.parse(date)).toString()
            } catch (e: ParseException) {
                Timber.e(e.message)
                date!!
            } catch (e: IllegalArgumentException) {
                Timber.e(e.message)
                date!!
            }
        }

        fun format2Date(date: String?, currentFormat: String): Date {
            val dateFormat = SimpleDateFormat(currentFormat, Locale.getDefault())
            return dateFormat.parse(date)
        }

        fun formatDate(date: String?, format: String, currentFormat: String): String {
            return try {
                val sdf = SimpleDateFormat(currentFormat, Locale.getDefault())
                android.text.format.DateFormat.format(format, sdf.parse(date)).toString()
            } catch (e: ParseException) {
                Timber.e(e.message)
                date!!
            } catch (e: IllegalArgumentException) {
                Timber.e(e.message)
                date!!
            }
        }

        fun formatDate(date: Date?, format: String): String{
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            return dateFormat.format(date)
        }

        fun formatTime(date: String?, format: String): String {
            val currentFormat = "HHmmss"

            return try {
                val sdf = SimpleDateFormat(currentFormat, Locale.getDefault())
                android.text.format.DateFormat.format(format, sdf.parse(date)).toString()
            } catch (e: ParseException) {
                Timber.e(e.message)
                date!!
            } catch (e: IllegalArgumentException) {
                Timber.e(e.message)
                date!!
            }
        }

        @JvmStatic
        fun formatAmount(amount: Double?): String{
//            val df = DecimalFormat("#.00")
            return "RM ${String.format("%.2f", amount)}"
        }

        @JvmStatic
        fun formatAmount(amount: String, isPrefix: Boolean): String {
            return "${if (isPrefix) "RM " else ""}${String.format("%.2f", if (amount.isEmpty()) 0.0 else amount.toDouble())}"
        }
    }
}

@SuppressWarnings("deprecation")
fun TextView.formatHtml(source: String) {
    text = TextHelper.formatHtml(source)
}

fun formatDate(date: Date): String {
    val currentFormat = "dd/MM/yyyy"
    val newFormat = "EEE, dd MMM"
    val stringDate = date.day.toString() + "/" + (date.month + 1).toString() + "/" + date.year.toString()

    return try {
        val sdf = SimpleDateFormat(currentFormat, Locale.getDefault())
        android.text.format.DateFormat.format(newFormat, sdf.parse(stringDate)).toString()
    } catch (e: ParseException) {
        Timber.e(e.message)
        stringDate
    } catch (e: IllegalArgumentException) {
        Timber.e(e.message)
        stringDate
    }
}









