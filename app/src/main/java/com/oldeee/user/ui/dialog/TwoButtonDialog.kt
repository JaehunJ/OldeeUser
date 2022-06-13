package com.oldeee.user.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.oldeee.user.databinding.LayoutDialogTwoButtonBinding

class TwoButtonDialog:DialogFragment() {
    private lateinit var binding:LayoutDialogTwoButtonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutDialogTwoButtonBinding.inflate(inflater, container, false)
        return binding.root
    }
}