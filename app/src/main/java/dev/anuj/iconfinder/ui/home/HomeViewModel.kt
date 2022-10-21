package dev.anuj.iconfinder.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.anuj.iconfinder.data.Category
import dev.anuj.iconfinder.network.ApiService
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: ApiService
) : ViewModel() {
    val categories = MutableLiveData<List<Category>>()
    private var job: Job? = null

    init {
        getCategories()
    }

    fun getCategories() {
        job?.cancel()
        job = viewModelScope.launch {
            if (categories.value.isNullOrEmpty()) {
                categories.value = api.getCategories().categories
            } else {
                val response = api.getCategories(after = categories.value!!.last().identifier)
                categories.value = categories.value!! + response.categories
            }
        }
    }
}