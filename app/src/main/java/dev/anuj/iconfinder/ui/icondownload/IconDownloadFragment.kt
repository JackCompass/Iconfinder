package dev.anuj.iconfinder.ui.icondownload

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.anuj.iconfinder.data.RasterSize
import dev.anuj.iconfinder.databinding.FragmentIconDownloadBinding

@AndroidEntryPoint
class IconDownloadFragment : Fragment() {
    private lateinit var binding: FragmentIconDownloadBinding
    private val args by navArgs<IconDownloadFragmentArgs>()
    private val viewModel by viewModels<IconDownloadViewModel>()

    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            viewModel.downloadIcon(viewModel.cacheIconSize!!)
        } else {
            Snackbar.make(binding.root, "Permission denied", Snackbar.LENGTH_SHORT).show()
        }
    }

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

        viewModel.snackbarMessage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                viewModel.snackbarMessage.value = ""
            }
        }

        viewModel.icon.observe(viewLifecycleOwner) {
            binding.recyclerViewIconSizes.apply {
                adapter = IconDownloadAdapter(it.raster_sizes) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        viewModel.downloadIcon(it)
                    } else {
                        checkPermission(it)
                    }
                }
            }
        }

        viewModel.getIcon(args.iconId)
    }

    private fun checkPermission(iconSize: RasterSize) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> { // You can use the API that requires the permission.
                viewModel.downloadIcon(iconSize)
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                Snackbar.make(binding.root, "Permission needed to download icon", Snackbar.LENGTH_SHORT).show()
            }
            else -> {
                viewModel.cacheIconSize = iconSize
                requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }
}