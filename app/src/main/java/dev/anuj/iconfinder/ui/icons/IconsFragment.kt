package dev.anuj.iconfinder.ui.icons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.anuj.iconfinder.R
import dev.anuj.iconfinder.databinding.FragmentIconsBinding

@AndroidEntryPoint
class IconsFragment : Fragment() {
    private lateinit var binding: FragmentIconsBinding
    private val args by navArgs<IconsFragmentArgs>()
    private val viewModel by viewModels<IconsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIconsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.icons.observe(viewLifecycleOwner) {
            binding.recyclerViewIcons.adapter = IconsAdapter(it) {
                findNavController().navigate(IconsFragmentDirections.actionIconsFragmentToIconDownloadFragment(it.icon_id))
            }
        }

        viewModel.getIcons(args.iconsetId)
    }
}