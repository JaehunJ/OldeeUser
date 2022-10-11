package com.oldee.user.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oldee.user.R

abstract class BaseBottomSheetDialogFragment(val isFullScreen:Boolean):BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindView(inflater, container)
    }

    abstract fun bindView(inflater: LayoutInflater,
                          container: ViewGroup?,):View?

    abstract fun initView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        if(isFullScreen){
            (dialog as BottomSheetDialog).let {
                it.setOnShowListener { imp->
                    val bsd = (imp as BottomSheetDialog)

                    val parentLayout =
                        bsd.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                    parentLayout?.let { it ->
                        setupFullHeight(it)
                    }

                    bsd.behavior.apply {
//                    isHideable = true
//                    isDraggable = false
//                    isCancelable = false
                        state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }
            }
        }
        return dialog
    }
}