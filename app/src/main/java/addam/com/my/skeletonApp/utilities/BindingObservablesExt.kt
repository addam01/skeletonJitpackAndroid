package addam.com.my.skeletonApp.utilities

import android.os.Parcelable
import androidx.databinding.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observable.create
import java.lang.Math.max
import java.lang.Math.min
import androidx.databinding.Observable as DataBindingObservable

/**
 * Created by Arman on 11/20/2017.
 *
 * From: https://gist.githubusercontent.com/stepango/c41a01a23bc986d82b8f20038c12e8a2/raw/cce5d7b37eb163394f56bdbcf94cc5d471163f9e/BindingObservablesExt.kt
 *
 * Converts databinding observable to rx observable
 *
 */

@Suppress("UNCHECKED_CAST")
private inline fun <T : DataBindingObservable, R : Any?> T.observe(
        crossinline block: (T) -> R
): Observable<R> = create { subscriber ->
    object : androidx.databinding.Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: DataBindingObservable, id: Int) = try {
            subscriber.onNext(block(observable as T))
        } catch (e: Exception) {
            subscriber.onError(e)
        }
    }.let {
        subscriber.setCancellable { this.removeOnPropertyChangedCallback(it) }
        this.addOnPropertyChangedCallback(it)
    }
}

class ObservableString(initialValue: String) : ObservableField<String>(initialValue)

fun ObservableInt.observe() = observe { it.get() }
fun ObservableByte.observe() = observe { it.get() }
fun ObservableChar.observe() = observe { it.get() }
fun ObservableLong.observe() = observe { it.get() }
fun ObservableShort.observe() = observe { it.get() }
fun ObservableFloat.observe() = observe { it.get() }
fun ObservableDouble.observe() = observe { it.get() }
fun ObservableBoolean.observe() = observe { it.get() }
fun <T : Any> ObservableField<T>.observe() = observe { it.get() }
fun <T : Parcelable> ObservableParcelable<T>.observe() = observe { it.get() }
//fun <T : Any> ObservableField<T?>.observeOption() = observe { it.get().toOption() }
//fun <T : Parcelable> ObservableParcelable<T?>.observeOption() = observe { it.get().toOption() }

operator fun ObservableInt.invoke() = get()
operator fun ObservableByte.invoke() = get()
operator fun ObservableChar.invoke() = get()
operator fun ObservableLong.invoke() = get()
operator fun ObservableShort.invoke() = get()
operator fun ObservableFloat.invoke() = get()
operator fun ObservableDouble.invoke() = get()
operator fun ObservableBoolean.invoke() = get()
operator fun <T : Any?> ObservableField<T>.invoke(): T? = get()
operator fun <T : Parcelable?> ObservableParcelable<T>.invoke(): T? = get()

fun ObservableInt.inc() = apply { set(get().inc()) }
fun ObservableByte.inc() = apply { set(get().inc()) }
fun ObservableChar.inc() = apply { set(get().inc()) }
fun ObservableLong.inc() = apply { set(get().inc()) }
fun ObservableShort.inc() = apply { set(get().inc()) }
fun ObservableFloat.inc() = apply { set(get().inc()) }
fun ObservableDouble.inc() = apply { set(get().inc()) }

fun ObservableInt.inc(max: Int) = apply { set(min(get().inc(), max)) }
fun ObservableByte.inc(max: Byte) = apply { set(min(get().inc(), max)) }
fun ObservableChar.inc(max: Char) = apply { set(min(get().inc(), max)) }
fun ObservableLong.inc(max: Long) = apply { set(min(get().inc(), max)) }
fun ObservableShort.inc(max: Short) = apply { set(min(get().inc(), max)) }
fun ObservableFloat.inc(max: Float) = apply { set(min(get().inc(), max)) }
fun ObservableDouble.inc(max: Double) = apply { set(min(get().inc(), max)) }

fun ObservableInt.dec() = apply { set(get().dec()) }
fun ObservableByte.dec() = apply { set(get().dec()) }
fun ObservableChar.dec() = apply { set(get().dec()) }
fun ObservableLong.dec() = apply { set(get().dec()) }
fun ObservableShort.dec() = apply { set(get().dec()) }
fun ObservableFloat.dec() = apply { set(get().dec()) }
fun ObservableDouble.dec() = apply { set(get().dec()) }

fun ObservableInt.dec(min: Int) = apply { set(max(get().dec(), min)) }
fun ObservableByte.dec(min: Byte) = apply { set(max(get().dec(), min)) }
fun ObservableChar.dec(min: Char) = apply { set(max(get().dec(), min)) }
fun ObservableLong.dec(min: Long) = apply { set(max(get().dec(), min)) }
fun ObservableShort.dec(min: Short) = apply { set(max(get().dec(), min)) }
fun ObservableFloat.dec(min: Float) = apply { set(max(get().dec(), min)) }
fun ObservableDouble.dec(min: Double) = apply { set(max(get().dec(), min)) }

private fun min(a: Short, b: Short) = if (a > b) b else a
private fun min(a: Byte, b: Byte) = if (a > b) b else a
private fun min(a: Char, b: Char) = if (a > b) b else a

private fun max(a: Short, b: Short) = if (a < b) b else a
private fun max(a: Byte, b: Byte) = if (a < b) b else a
private fun max(a: Char, b: Char) = if (a < b) b else a