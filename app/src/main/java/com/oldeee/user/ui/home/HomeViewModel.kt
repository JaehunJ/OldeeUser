package com.oldeee.user.ui.home

import com.oldeee.user.base.BaseRepository
import com.oldeee.user.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: BaseRepository):BaseViewModel(repository) {
}