package com.oldeee.user.ui.payment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentPaymentBinding
import com.oldeee.user.databinding.LayoutPaymentItemBinding
import com.oldeee.user.databinding.LayoutPaymentItemImageBinding
import com.oldeee.user.network.response.BasketListItem
import com.oldeee.user.ui.dialog.TwoButtonDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding, PaymentViewModel, PaymentFragmentArgs>() {
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
    }

    override fun initDataBinding() {
        viewModel.datas.observe(viewLifecycleOwner){
            it?.let{
                setContainer(it)
            }
        }
    }

    override fun initViewCreated() {

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

    fun setContainer(datas:List<BasketListItem>){
        binding.llContainer.removeAllViews()

        //add child
        datas.forEach {
            val inflater = LayoutInflater.from(requireContext())
            val viewBinding = LayoutPaymentItemBinding.inflate(inflater, binding.llContainer, true)

            viewBinding.res = it
            viewBinding.rvImage.adapter = PaymentImageAdapter{iv,path->
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