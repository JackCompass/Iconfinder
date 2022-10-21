package dev.anuj.iconfinder.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.anuj.iconfinder.data.Category
import dev.anuj.iconfinder.network.ApiService
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: ApiService
) : ViewModel() {
    val categories = MutableLiveData<List<Category>>()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            categories.value = api.getCategories().categories
        }
    }
}