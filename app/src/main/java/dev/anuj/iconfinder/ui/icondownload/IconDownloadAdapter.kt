package dev.anuj.iconfinder.ui.icondownload

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.anuj.iconfinder.data.RasterSize
import dev.anuj.iconfinder.databinding.LayoutIconDownloadItemBinding

class IconDownloadAdapter(
    private val variants: List<RasterSize>,
    private val onClick: (RasterSize) -> Unit
) : RecyclerView.Adapter<IconDownloadAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutIconDownloadItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutIconDownloadItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with (holder.binding) {
            val variant = variants[position]
            textViewIconSize.text = "${variant.size}x${variant.size}"
            imageViewDownloadIcon.setOnClickListener {
                onClick(variant)
            }
        }
    }

    override fun getItemCount() = variants.size
}