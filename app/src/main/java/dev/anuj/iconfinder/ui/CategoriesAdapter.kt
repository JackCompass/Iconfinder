package dev.anuj.iconfinder.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.anuj.iconfinder.data.Category
import dev.anuj.iconfinder.databinding.LayoutCategoryItemBinding

class CategoriesAdapter(
    private val categories: List<Category>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutCategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textViewCategoryName.text = categories[position].name

        holder.binding.root.setOnClickListener {
            onClick(categories[position].identifier)
        }
    }
}