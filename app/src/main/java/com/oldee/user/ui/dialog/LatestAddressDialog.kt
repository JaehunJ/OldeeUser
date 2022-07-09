package com.oldee.user.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oldee.user.R
import com.oldee.user.databinding.LayoutDialogLatestAddressBinding
import com.oldee.user.databinding.LayoutLatestAddressDialogItemBinding
import com.oldee.user.network.response.ShippingAddressListItem

class LatestAddressDialog(val selectedCallback:(ShippingAddressListItem)->Unit, val deleteCallback:(Int)->Unit, val datas: List<ShippingAddressListItem?>) :
    BottomSheetDialogFragment() {
    lateinit var binding: LayoutDialogLatestAddressBinding

    var selectedItem = if(datas.isNotEmpty()) datas[0] else null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val _binding = LayoutDialogLatestAddressBinding.inflate(inflater, container, false)
        binding = _binding

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        if(datas.isEmpty()){
            binding.clEmpty.visibility = View.VISIBLE
        }else{
            binding.clEmpty.visibility = View.GONE
        }
        setChildItem()
    }

    private fun setChildItem() {
        binding.llContainer.removeAllViews()
        datas.forEach {
            val v = LayoutLatestAddressDialogItemBinding.inflate(
                layoutInflater,
                binding.llContainer,
                true
            )

            val fullAddr = "${it?.shippingAddress} ${it?.shippingAddressDetail}"
            v.data = LatestAddressAdapterData(
                it?.shippingName ?: "",
                fullAddr,
                it?.userPhone ?: "",
                it?.addressId ?: 0
            )

            v.clRoot.setOnClickListener {
                v.cbSelected.isChecked = !v.cbSelected.isChecked
            }
            v.cbSelected.setOnCheckedChangeListener { compoundButton, b ->
                if(b){
                    v.clRoot.setBackgroundResource(R.drawable.bg_round_8dp_stroke_main3)
                    selectedItem
                }else{
                    v.clRoot.setBackgroundResource(R.drawable.bg_round_8dp_stroke_gray_1)
                }
            }
            v.ivDelete.setOnClickListener {view->
                it?.let{
                    deleteCallback.invoke(it.addressId)
                }
            }
        }
    }


    data class LatestAddressAdapterData(
        val name: String,
        val address: String,
        val phone: String,
        val addressId: Int
    )
}