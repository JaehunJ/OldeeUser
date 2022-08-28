package com.oldee.user.ui.payment.done

import android.os.Bundle
import android.widget.ImageView
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

    lateinit var adapter:PaymentItemAdapter

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

        adapter = PaymentItemAdapter{imageView: ImageView, s: String ->
              viewModel.setImage(imageView, s)
        }
        binding.rvItem.adapter = adapter
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

        viewModel.res.observe(viewLifecycleOwner, getObserver(viewLifecycleOwner){
            it?.let {
                if(it.data.isNotEmpty()){
                    if(it.data[0].surveySeqList.isNotEmpty()){
                        adapter.setData(it.data[0].surveySeqList)
                    }
                }
            }
        })
    }

    override fun initViewCreated() {

    }
}