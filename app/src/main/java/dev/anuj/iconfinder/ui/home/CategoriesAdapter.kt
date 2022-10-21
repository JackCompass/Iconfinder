package dev.anuj.iconfinder.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.anuj.iconfinder.data.Category
import dev.anuj.iconfinder.databinding.LayoutCategoryItemBinding

class CategoriesAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<Category, CategoriesAdapter.ViewHolder>(Diff) {

    inner class ViewHolder(val binding: LayoutCategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textViewCategoryName.text = getItem(position).name

        holder.binding.root.setOnClickListener {
            onClick(getItem(position).identifier)
        }
    }

    object Diff : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) = oldItem.identifier == newItem.identifier

        override fun areContentsTheSame(oldItem: Category, newItem: Category) = oldItem == newItem
    }
}