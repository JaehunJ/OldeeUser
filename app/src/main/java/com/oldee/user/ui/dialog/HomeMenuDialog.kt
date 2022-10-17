package com.oldee.user.ui.dialog

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oldee.user.databinding.LayoutHomeMenuBinding

class HomeMenuDialog(useFullScreen:Boolean, val initViewMethod:(Dialog?, LayoutHomeMenuBinding)->Unit):BaseBottomSheetDialogFragment(useFullScreen) {
    lateinit var binding:LayoutHomeMenuBinding

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = LayoutHomeMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        initViewMethod(dialog, binding)
    }
}