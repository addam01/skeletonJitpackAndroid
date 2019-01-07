package addam.com.my.skeletonApp.core

/**
 * Created by Addam on 7/1/2019.
 */
abstract class SingleRecyclerViewLayoutAdapter<T>(private val layoutId: Int, itemClickListener: OnItemClickListener<T>?) : BaseRecyclerViewAdapter<T>(itemClickListener) {
    override fun getLayoutIdForPosition(position: Int): Int {
        return layoutId
    }
}