package com.oldee.user.ui.payment.done

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.databinding.FragmentPaymentDoneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentDoneFragment :
    BaseFragment<FragmentPaymentDoneBinding, PaymentDoneViewModel, PaymentDoneFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_payment_done
    override val viewModel: PaymentDoneViewModel by viewModels()
    override val navArgs: PaymentDoneFragmentArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnConfirm.setOnClickListener {
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


    }

    override fun onResume() {
        super.onResume()

        viewModel.orderId.postValue(navArgs.id)
    }

    override fun initDataBinding() {
        viewModel.orderId.observe(viewLifecycleOwner, getObserver(viewLifecycleOwner) {
            it?.let {
                viewModel.requestOrder(it)
            }
        })
    }

    override fun initViewCreated() {

    }
}