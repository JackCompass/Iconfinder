package dev.anuj.iconfinder.ui.icondownload

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.anuj.iconfinder.R
import dev.anuj.iconfinder.databinding.FragmentIconDownloadBinding

@AndroidEntryPoint
class IconDownloadFragment : Fragment() {
    private lateinit var binding: FragmentIconDownloadBinding
    private val args by navArgs<IconDownloadFragmentArgs>()
    private val viewModel by viewModels<IconDownloadViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIconDownloadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.icon.observe(viewLifecycleOwner) {
            binding.recyclerViewIconSizes.apply {
                adapter = IconDownloadAdapter(it.raster_sizes) {

                }
            }
        }

        viewModel.getIcon(args.iconId)
    }
}