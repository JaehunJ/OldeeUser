package com.oldeee.user.ui.payment.done

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentPaymentDoneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentDoneFragment :
    BaseFragment<FragmentPaymentDoneBinding, PaymentDoneViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_payment_done
    override val viewModel: PaymentDoneViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initViewCreated() {

    }
}