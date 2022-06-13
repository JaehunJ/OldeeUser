package com.oldeee.user.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.oldeee.user.databinding.LayoutDialogOneButtonBinding

class OneButtonDialog:DialogFragment() {
    private lateinit var binding : LayoutDialogOneButtonBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutDialogOneButtonBinding.inflate(inflater, container, false)
        return binding.root
    }

}