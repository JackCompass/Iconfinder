package dev.anuj.iconfinder.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.anuj.iconfinder.data.Icon
import dev.anuj.iconfinder.network.ApiService
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class IconSearchViewModel @Inject constructor(
    private val api: ApiService
) : ViewModel() {

    val icons = MutableLiveData<List<Icon>>()
    var job: Job? = null

    fun search(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(450)
            icons.value = api.searchIcons(query).icons
        }
    }

}
