package com.oldeee.user.ui.dialog

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oldeee.user.BuildConfig
import com.oldeee.user.R
import com.oldeee.user.databinding.LayoutTermDialogBinding

class TermBottomSheetDialog : BottomSheetDialogFragment() {

    lateinit var binding: LayoutTermDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val _binding = LayoutTermDialogBinding.inflate(inflater, container, false)
        binding = _binding

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        val webView = binding.webView
        webView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
            domStorageEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
        }
        binding.webView.loadUrl(BuildConfig.TERM_PRIVACY)

//        binding
    }

    private fun getWindowHeight(){
        val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay
    }
}