package com.oldee.user.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oldee.user.databinding.LayoutDialogPermissionBinding

class PermissionDialog(useFullScreen: Boolean, val onClick: () -> Unit) :
    BaseBottomSheetDialogFragment(useFullScreen) {
    private lateinit var binding: LayoutDialogPermissionBinding


    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = LayoutDialogPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        binding.btnOk.setOnClickListener {
            dismiss()
            onClick()
        }
    }
}