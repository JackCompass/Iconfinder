package dev.anuj.iconfinder.ui.icondownload

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.anuj.iconfinder.data.Icon
import dev.anuj.iconfinder.network.ApiService
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class IconDownloadViewModel @Inject constructor(
    private val api: ApiService
) : ViewModel() {

    val icon = MutableLiveData<Icon>()

    fun getIcon(iconId: Int) {
        viewModelScope.launch {
            icon.value = api.getIcon(iconId)
        }
    }
}