package com.oldee.user.ui.payment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.databinding.FragmentPaymentBinding
import com.oldee.user.databinding.LayoutPaymentItemBinding
import com.oldee.user.network.response.BasketListItem
import com.oldee.user.network.response.PaymentFailResponse
import com.oldee.user.network.response.PaymentSuccessResponse
import com.oldee.user.ui.TossWebActivity
import com.oldee.user.ui.dialog.LatestAddressDialog
import com.oldee.user.ui.dialog.OneButtonDialog
import com.oldee.user.ui.dialog.PostDialog
import com.oldee.user.ui.dialog.TwoButtonDialog
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PaymentFragment :
    BaseFragment<FragmentPaymentBinding, PaymentViewModel, PaymentFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_payment
    override val viewModel: PaymentViewModel by viewModels()
    override val navArgs: PaymentFragmentArgs by navArgs()

    lateinit var backCallback: OnBackPressedCallback

    val activityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
            res?.let { it ->
                when (it.resultCode) {
                    Activity.RESULT_OK -> {
                        val jsonStr = it.data?.getStringExtra("result")
                        if(!jsonStr.isNullOrEmpty()){
                            val gson = Gson()
                            val obj = gson.fromJson(jsonStr, PaymentSuccessResponse::class.java)

                            obj?.let{
                                viewModel.requestPaymentProcess(it.paymentKey){

                                }
                            }
                        }
//                        val orderId = it.data?.getIntExtra("id", -1) ?: -1
//
//                        if (orderId != -1) {
//                            moveNext(orderId)
//                        }
                    }
                    else -> {
                        val jsonStr = it.data?.getStringExtra("result")
                        if(!jsonStr.isNullOrEmpty()){
                            val gson = Gson()
                            val obj = gson.fromJson(jsonStr, PaymentFailResponse::class.java)
                            showPaymentCancelDialog()
                        }
                    }
                }
            }
        }

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
            val dialog = LatestAddressDialog({}, { id ->

            }, viewModel.allAddress.value ?: mutableListOf())
            dialog.show(requireActivity().supportFragmentManager, "address")
        }

        binding.btnConfirm.setOnClickListener {
            if (viewModel.isValidation()) {
                val dialog = TwoButtonDialog(
                    title = "주문을 신청하시겠어요?",
                    contents = "",
                    okText = "신청",
                    cancelText = "취소", {
                        viewModel.requestPaymentPage {
                            activityResult.launch(getWebViewIntent(it))
                        }
                    }
                ) {

                }

                dialog.show(requireActivity().supportFragmentManager, "")
            } else {
                activityFuncFunction.showToast("누락된 정보가 있습니다.")
            }
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

        viewModel.paymentDoneResponse.observe(viewLifecycleOwner){
            it?.let {
                if(it.message == "success"){
                    moveNext(it.data.orderId)
                }
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
        super.removeBackAction()
    }

    fun getWebViewIntent(html: String): Intent {
        val intent = Intent(requireActivity(), TossWebActivity::class.java)
        intent.putExtra("html", html)
        return intent
//        startActivity(Intent(requireActivity(), TossWebActivity::class.java))
    }

    fun setContainer(datas: List<BasketListItem>) {
        binding.llContainer.removeAllViews()

        //add child
        datas.forEach {
            val inflater = LayoutInflater.from(requireContext())
            val viewBinding = LayoutPaymentItemBinding.inflate(inflater, binding.llContainer, true)

            viewBinding.res = it
            viewBinding.rvImage.adapter =
                PaymentImageAdapter(requireContext()) { context, iv, path ->
                    viewModel.setImage(context, iv, path)
                }

            val imageList = it.getImageNameList()
            (viewBinding.rvImage.adapter as PaymentImageAdapter).setData(
                imageList?.toList() ?: listOf()
            )
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

    fun moveNext(orderId: Int) {
        Logger.e("move next")
        findNavController().navigate(
            PaymentFragmentDirections.actionPaymentFragmentToPaymentDoneFragment(
                orderId
            )
        )
    }

    fun showPaymentCancelDialog() {
        val dialog = OneButtonDialog("결제가 실패했어요.", "결제수단을 확인후\n다시 시도해주세요", "다시 시도하기") {

        }
        dialog.isCancelable = false
        activity?.let {
            dialog.show(it.supportFragmentManager, "")
        }
    }
}