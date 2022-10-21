package dev.anuj.iconfinder.ui.icons

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.anuj.iconfinder.data.Icon
import dev.anuj.iconfinder.network.ApiService
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class IconsViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {

    val icons = MutableLiveData<List<Icon>>()


    fun getIcons(iconsetId: Int) {
        viewModelScope.launch {
            icons.value = api.getIcons(iconsetId).icons
        }
    }
}