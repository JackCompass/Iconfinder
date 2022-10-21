package dev.anuj.iconfinder.ui.icondownload

import android.app.Application
import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.anuj.iconfinder.data.Icon
import dev.anuj.iconfinder.data.RasterSize
import dev.anuj.iconfinder.network.ApiService
import java.io.File
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber

@HiltViewModel
class IconDownloadViewModel @Inject constructor(
    private val app: Application, // Bad practice but no good alternative
    private val api: ApiService,
    private val okHttpClient: OkHttpClient
) : AndroidViewModel(app) {

    val snackbarMessage = MutableLiveData<String>()

    val icon = MutableLiveData<Icon>()

    var cacheIconSize: RasterSize? = null

    fun getIcon(iconId: Int) {
        viewModelScope.launch {
            icon.value = api.getIcon(iconId)
        }
    }

    fun downloadIcon(iconSize: RasterSize) {
        viewModelScope.launch {
            val resolver = app.contentResolver

            val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

            val image = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, iconSize.formats[0].preview_url.toUri().lastPathSegment.toString())
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            val request = Request.Builder().url(iconSize.formats[0].download_url).build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    snackbarMessage.postValue("Failed to download icon")
                }

                override fun onResponse(call: Call, response: Response) {
                    val data = response.body?.byteStream()

                    if (data != null) {
                        try {
                            resolver.insert(imageCollection, image)?.let { uri ->
                                resolver.openOutputStream(uri).use { outputStream ->
                                    data.copyTo(outputStream!!)
                                    snackbarMessage.postValue("Icon downloaded successfully")
                                }
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                            snackbarMessage.postValue("Failed to download icon")
                        }
                    }
                }
            })
        }
    }
}