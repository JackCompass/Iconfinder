package dev.anuj.iconfinder.ui.icons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import coil.load
import dev.anuj.iconfinder.R
import dev.anuj.iconfinder.data.Icon
import dev.anuj.iconfinder.databinding.LayoutIconItemBinding

class IconsAdapter(
    private val icons: List<Icon>,
    private val onDownloadClick: (Icon) -> Unit
) : RecyclerView.Adapter<IconsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutIconItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutIconItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with (holder.binding) {
            val icon = icons[position]
            imageViewIcon.load(icon.raster_sizes.find { it.size == 128 }?.formats?.get(0)?.preview_url)
            textViewIconId.text = icon.icon_id.toString()
            textViewIconTags.text = icon.tags.joinToString(", ")
            imageViewIconDownload.setImageResource(if (icon.is_premium) R.drawable.baseline_attach_money else R.drawable.ic_download)

            if (!icon.is_premium) imageViewIconDownload.setOnClickListener {
                onDownloadClick(icon)
            }
        }
    }

    override fun getItemCount() = icons.size
}