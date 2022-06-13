package com.oldeee.user.ui.cart

import com.oldeee.user.base.BaseRepository
import com.oldeee.user.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(repository: BaseRepository) : BaseViewModel(repository) {

}