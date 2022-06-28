package com.oldeee.user.ui.payment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentPaymentBinding
import com.oldeee.user.databinding.LayoutPaymentItemBinding
import com.oldeee.user.network.response.BasketListItem
import com.oldeee.user.ui.dialog.OneButtonDialog
import com.oldeee.user.ui.dialog.PostDialog
import com.oldeee.user.ui.dialog.TwoButtonDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PaymentFragment :
    BaseFragment<FragmentPaymentBinding, PaymentViewModel, PaymentFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_payment
    override val viewModel: PaymentViewModel by viewModels()
    override val navArgs: PaymentFragmentArgs by navArgs()

    lateinit var backCallback: OnBackPressedCallback

    override fun initView(savedInstanceState: Bundle?) {
        super.setOnInvokeBackAction {
            showCancelDialog()
        }

        binding.vm = viewModel
        viewModel.datas.postValue(navArgs.datas.toList())
        binding.btnPost.setOnClickListener {
            val dialog = PostDialog { road, zone ->
                viewModel.address.postValue(road)
                viewModel.postNum.postValue(zone)
            }
            dialog.show(requireActivity().supportFragmentManager, "")
        }

        binding.btnConfirm.setOnClickListener {
            if (viewModel.isValidation()) {
                val dialog = TwoButtonDialog(
                    title = "주문을 신청학시겠어요?",
                    contents = "",
                    okText = "신청",
                    cancelText = "취소", {
                        viewModel.requestPaymentProcess({
                            val dialog = OneButtonDialog(
                                title = "주문이 완료되었습니다.",
                                contents = "",
                                okText = "확인"
                            ){
                                findNavController().popBackStack(R.id.homeFragment, false)
                            }
                            dialog.show(requireActivity().supportFragmentManager, "")
                        }) {

                        }
                    }
                ) {

                }

                dialog.show(requireActivity().supportFragmentManager, "")
            } else {
                activityFuncFunction.showToast("누락된 정보가 있습니다.")
            }
//            viewModel.requestPaymentProcess({
//
//            }){
//
//            }
        }
    }

    override fun initDataBinding() {
        viewModel.datas.observe(viewLifecycleOwner) {
            it?.let {
                setContainer(it)

                var price = 0
                it.forEach { item ->
                    price += item.reformPrice.toInt()
                }

                viewModel.totalPrice.postValue(price)
            }
        }

        viewModel.latestAddress.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.address.postValue(it.shippingAddress)
                viewModel.extendAddress.postValue(it.shippingAddressDetail)
                viewModel.postNum.postValue(it.postalCode)
            }
        }
    }

    override fun initViewCreated() {
        viewModel.requestAddressList()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showCancelDialog()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backCallback)
    }

    override fun onDetach() {
        super.onDetach()
        backCallback.remove()
    }

    fun setContainer(datas: List<BasketListItem>) {
        binding.llContainer.removeAllViews()

        //add child
        datas.forEach {
            val inflater = LayoutInflater.from(requireContext())
            val viewBinding = LayoutPaymentItemBinding.inflate(inflater, binding.llContainer, true)

            viewBinding.res = it
            viewBinding.rvImage.adapter = PaymentImageAdapter { iv, path ->
                viewModel.setImage(iv, path)
            }

            val imageList = it.getImageNameList()
            (viewBinding.rvImage.adapter as PaymentImageAdapter).setData(imageList.toList())
        }
    }

    fun showCancelDialog() {
        val dialog = TwoButtonDialog(
            title = "",
            contents = "결제를 취소하시겠어요?",
            okText = "계속 진행하기",
            cancelText = "결제취소하기",
            {}, { findNavController().popBackStack() }
        )
        dialog.isCancelable = false

        activity?.let {
            dialog.show(it.supportFragmentManager, "")
        }
    }
}