package com.oldeee.user.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.oldeee.user.databinding.LayoutDialogTwoButtonBinding

class TwoButtonDialog(
    val title: String,
    val contents: String,
    val okText: String,
    val cancelText: String,
    val onClick: () -> Unit,
    val onCancel: () -> Unit
) : DialogFragment() {
    private lateinit var binding: LayoutDialogTwoButtonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutDialogTwoButtonBinding.inflate(inflater, container, false)

        if (title.isEmpty()) {
            binding.tvTitle.visibility = View.GONE
        }
        binding.tvTitle.text = title

        if (contents.isEmpty()) {
            binding.tvContents.visibility = View.GONE
        }
        binding.tvContents.text = contents
        binding.btnOk.text = okText
        binding.btnOk.setOnClickListener {
            dialog?.dismiss()
            onClick.invoke()
        }
        binding.btnClose.text = cancelText
        binding.btnClose.setOnClickListener {
            dialog?.dismiss()
            onCancel.invoke()
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding.root
    }


}