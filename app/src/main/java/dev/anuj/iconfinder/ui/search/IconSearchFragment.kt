package dev.anuj.iconfinder.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.anuj.iconfinder.R
import dev.anuj.iconfinder.databinding.FragmentIconSearchBinding
import dev.anuj.iconfinder.ui.icons.IconsAdapter

@AndroidEntryPoint
class IconSearchFragment : Fragment() {
    private lateinit var binding: FragmentIconSearchBinding
    private val viewModel by viewModels<IconSearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIconSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textFieldIconSearch.editText?.let {
            it.doAfterTextChanged {
                viewModel.search(it.toString().trim())
            }
            it.requestFocus()
            it.showKeyboard()
        }

        viewModel.icons.observe(viewLifecycleOwner) {
            binding.recyclerViewIcons.adapter = IconsAdapter(it) {
                findNavController().navigate(
                    IconSearchFragmentDirections.actionIconSearchFragmentToIconDownloadFragment(it.icon_id)
                )
            }
        }
    }

    fun EditText.showKeyboard() {
        post {
            requestFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}