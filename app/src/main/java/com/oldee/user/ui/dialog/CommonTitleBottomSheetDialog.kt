package com.oldee.user.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oldee.user.databinding.LayoutDialogCommonTitleBottomBinding

class CommonTitleBottomSheetDialog(
    useFullScreen: Boolean,
    val title: String,
    val contents: String,
    val btnOkText:String = "",
    val btnCancelText:String = "",
    val onClickOk: (() -> Unit)? = null,
    val onClickCancel: (() -> Unit)? = null
) :
    BaseBottomSheetDialogFragment(useFullScreen) {

    lateinit var binding : LayoutDialogCommonTitleBottomBinding

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = LayoutDialogCommonTitleBottomBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun initView() {
        binding.tvTitle.text = title
        binding.tvContents.text = contents
        binding.btnOk.setOnClickListener {
            onClickOk?.invoke()
            dismiss()
        }
        if(btnOkText.isNotEmpty()){
            binding.btnOk.text = btnOkText
        }

        binding.btnCancel.setOnClickListener {
            onClickCancel?.invoke()
            dismiss()
        }
        if(btnCancelText.isNotEmpty()){
            binding.btnCancel.text = btnCancelText
        }
    }
}