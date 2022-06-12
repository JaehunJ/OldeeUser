package com.oldeee.user.base

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.launch

abstract class BaseViewModel(var repository: BaseRepository) : ViewModel() {
    fun isLoading() = repository.getIsLoading()

    /*
    fun setImage(imageView: ImageView, url: String) {
        viewModelScope.launch {
            val bitmap = repository.getImageFromServer(url)
            if (bitmap != null) {
                Glide.with(imageView.context).load(bitmap).into(imageView)
            }
        }
    }

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