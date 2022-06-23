package com.oldeee.user.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.oldeee.user.R
import com.oldeee.user.databinding.DialogPostSearchBinding

class PostDialog: DialogFragment() {
    lateinit var binding:DialogPostSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialogFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogPostSearchBinding.inflate(inflater,container, false)

        binding.ivBack.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}