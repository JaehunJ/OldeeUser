package com.oldee.user.ui.payment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.oldee.user.BuildConfig
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.databinding.FragmentPaymentBinding
import com.oldee.user.databinding.LayoutPaymentItemBinding
import com.oldee.user.network.response.BasketListItem
import com.oldee.user.ui.TossWebActivity
import com.oldee.user.ui.dialog.LatestAddressDialog
import com.oldee.user.ui.dialog.OneButtonDialog
import com.oldee.user.ui.dialog.PostDialog
import com.oldee.user.ui.dialog.TwoButtonDialog
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

        binding.btnLatestAddress.setOnClickListener {
            val dialog = LatestAddressDialog({},{ id->

            }, viewModel.allAddress.value?: mutableListOf())
            dialog.show(requireActivity().supportFragmentManager,"address")
        }

        binding.btnConfirm.setOnClickListener {
            if (viewModel.isValidation()) {
                val dialog = TwoButtonDialog(
                    title = "주문을 신청하시겠어요?",
                    contents = "",
                    okText = "신청",
                    cancelText = "취소", {
//                        viewModel.requestPaymentPage{
//                            showTossWebView(it)
//                        }
                        viewModel.requestPaymentProcess({
                            val dialog = OneButtonDialog(
                                title = "주문이 완료되었습니다.",
                                contents = "",
                                okText = "확인"
                            ) {
                                val option = navOptions {
                                    popUpTo(R.id.homeFragment)
                                }
                                val bundle = bundleOf("selectedTab" to 1)
                                findNavController().navigate(
                                    R.id.action_global_orderLogFragment,
                                    bundle,
                                    option
                                )
                            }
                            dialog.show(requireActivity().supportFragmentManager, "")
                        }) {
                            activityFuncFunction.showToast(it)
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
                //이전 정보가 있으면 세팅
                viewModel.name.postValue(it.shippingName)
                viewModel.phone.postValue(it.userPhone)
                viewModel.address.postValue(it.shippingAddress)
                viewModel.extendAddress.postValue(it.shippingAddressDetail)
                viewModel.postNum.postValue(it.postalCode)
            }
        }

    }

    override fun initViewCreated() {
        viewModel.requestAddressLatest()
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

    fun showTossWebView(html:String){
        val intent = Intent(requireActivity(), TossWebActivity::class.java)
        intent.putExtra("html", html)
        startActivity(Intent(requireActivity(), TossWebActivity::class.java))
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
            (viewBinding.rvImage.adapter as PaymentImageAdapter).setData(imageList?.toList()?: listOf())
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