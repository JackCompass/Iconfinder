package dev.anuj.iconfinder.ui.iconset

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import dev.anuj.iconfinder.data.Iconset
import dev.anuj.iconfinder.databinding.LayoutIconsetItemBinding

class IconsetAdapter(
    private val iconsets: List<Iconset>,
    private val onClick: (Iconset) -> Unit
) : RecyclerView.Adapter<IconsetAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutIconsetItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutIconsetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val iconset = iconsets[position]
            textViewIconsetName.text = iconset.name
            textViewAuthor.text = iconset.author.name
            textViewIconCount.text = "${iconset.icons_count} icons"
            textViewIconsetPremium.text = if (iconset.is_premium) "Premium" else "Free"
        }

        holder.binding.root.setOnClickListener {
            onClick(iconsets[position])
        }
    }

    override fun getItemCount() = iconsets.size
}