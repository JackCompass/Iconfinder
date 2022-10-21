package dev.anuj.iconfinder.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import dagger.hilt.android.AndroidEntryPoint
import dev.anuj.iconfinder.R
import dev.anuj.iconfinder.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_iconSearchFragment)
        }

        val adapter = CategoriesAdapter {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToIconsetFragment(it))
        }
        binding.recyclerViewCategories.adapter = adapter

        viewModel.categories.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.recyclerViewCategories.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = binding.recyclerViewCategories.layoutManager?.itemCount ?: 0
                val lastVisible = (binding.recyclerViewCategories.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (totalItemCount > 0 && (lastVisible + 5) >= totalItemCount) {
                    viewModel.getCategories()
                }
            }
        })
    }
}