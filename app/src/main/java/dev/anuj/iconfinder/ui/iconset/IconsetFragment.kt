package dev.anuj.iconfinder.ui.iconset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.anuj.iconfinder.databinding.FragmentIconsetBinding

@AndroidEntryPoint
class IconsetFragment : Fragment() {
    private lateinit var binding: FragmentIconsetBinding
    private val args by navArgs<IconsetFragmentArgs>()
    private val viewModel by viewModels<IconsetViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIconsetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.iconsets.observe(viewLifecycleOwner) {
            binding.recyclerViewIconsets.apply {
                adapter = IconsetAdapter(it) {
                    findNavController().navigate(IconsetFragmentDirections.actionIconsetFragmentToIconsFragment(it.iconset_id))
                }
            }
        }

        viewModel.getIconsets(args.categoryId)
    }
}