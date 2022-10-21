package dev.anuj.iconfinder.ui.iconset

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.anuj.iconfinder.data.Iconset
import dev.anuj.iconfinder.network.ApiService
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class IconsetViewModel @Inject constructor(
    private val api: ApiService
) : ViewModel() {

    val iconsets = MutableLiveData<List<Iconset>>()

    fun getIconsets(categoryId: String) {
        viewModelScope.launch {
            iconsets.value = api.getIconSets(categoryId).iconsets

        }
    }
}