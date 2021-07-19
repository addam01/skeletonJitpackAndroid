package addam.com.my.skeletonApp.core

import addam.com.my.skeletonApp.BR
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Addam on 7/1/2019.
 */
abstract class BaseRecyclerViewAdapter<T> internal constructor(private val itemClickListener: OnItemClickListener<T>?) : RecyclerView.Adapter<BaseRecyclerViewAdapter<T>.ViewHolder>() {

    interface OnItemClickListener<in T> {
        fun onItemClick(item: T, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater, viewType, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItemForPosition(position)
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener({ itemClickListener.onItemClick(item, holder.itemView) })
        }
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    protected abstract fun getItemForPosition(position: Int): T

    protected abstract fun getLayoutIdForPosition(position: Int): Int

    inner class ViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            binding.setVariable(BR.model, item)
            binding.executePendingBindings()
        }
    }
}