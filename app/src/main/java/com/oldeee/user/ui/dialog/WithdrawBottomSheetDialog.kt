package com.oldeee.user.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oldeee.user.R
import com.oldeee.user.databinding.LayoutWithdrawDialogBinding

class WithdrawBottomSheetDialog(val onClick:()->Unit) : BottomSheetDialogFragment() {
    lateinit var binding: LayoutWithdrawDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        (dialog as BottomSheetDialog).let {
            it.setOnShowListener { imp ->
                val bsd = (imp as BottomSheetDialog)

                val parentLayout =
                    bsd.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                parentLayout?.let { it ->
                    setupFullHeight(it)
                }

                bsd.behavior.apply {
                    isHideable = true
                    isDraggable = false
                    isCancelable = false
                    state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val _binding = LayoutWithdrawDialogBinding.inflate(inflater, container, false)
        binding = _binding

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        binding.llCheck.setOnClickListener {
            binding.cb.isChecked = !binding.cb.isChecked
        }

        binding.cb.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.btnWithdraw.isEnabled = isChecked
        }

        binding.btnWithdraw.setOnClickListener {

        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}