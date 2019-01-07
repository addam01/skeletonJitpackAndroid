package addam.com.my.skeletonApp.core

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by Arman on 12/12/2017.
 */

val Map<Router.Parameter, Any?>.bundle: Bundle
    get() {
        val bundle = Bundle()
        forEach {
            val k = it.key.name
            val v = it.value
            if (v != null) when (v) {
                is Boolean -> bundle.putBoolean(k, v)
                is Bundle -> bundle.putBundle(k, v)
                is Byte -> bundle.putByte(k, v)
                is ByteArray -> bundle.putByteArray(k, v)
                is Char -> bundle.putChar(k, v)
                is CharArray -> bundle.putCharArray(k, v)
                is CharSequence -> bundle.putCharSequence(k, v)
                is Int -> bundle.putInt(k, v)
                is Float -> bundle.putFloat(k, v)
                is FloatArray -> bundle.putFloatArray(k, v)
                is Parcelable -> bundle.putParcelable(k, v)
                is Serializable -> bundle.putSerializable(k, v)
                is Short -> bundle.putShort(k, v)
                is ShortArray -> bundle.putShortArray(k, v)
                else -> bundle.putSerializable(k, v as Serializable)
            }
        }
        return bundle
    }