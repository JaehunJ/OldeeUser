package com.oldeee.user.base

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseViewModel(private var repository: BaseRepository) : ViewModel() {
    fun isLoading() = repository.getIsLoading()

    fun postDelay(callback:()->Unit, time:Long){
        viewModelScope.launch {
            delay(time)
            callback()
        }
    }

    fun <T: BaseRepository> getRepository() = (repository  as T)


    fun setImage(imageView: ImageView, url: String) {
        viewModelScope.launch {
            val bitmap = repository.getImageFromServer(url)
            if (bitmap != null) {
                Glide.with(imageView.context).load(bitmap).into(imageView)
            }
        }
    }

    fun setImageCircle(imageView: ImageView, url: String){
        viewModelScope.launch {
            val bitmap = repository.getImageFromServer(url)
            if (bitmap != null) {
                Glide.with(imageView.context).load(bitmap).apply(RequestOptions().circleCrop()).into(imageView)
            }
        }
    }

    /*
    fun setImageCircle(imageView: ImageView, url: String) {
        viewModelScope.launch {
            val bitmap = repository.getImageFromServer(url)
            if (bitmap != null) {
                Glide.with(imageView.context).load(bitmap).apply(RequestOptions().circleCrop())
                    .into(imageView)
            }
        }
    }
    */

}