package com.oldeee.user.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentCartBinding
import com.oldeee.user.databinding.LayoutCartOrderItemBinding
import com.oldeee.user.network.response.BasketListItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_cart
    override val viewModel: CartViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    val childList = mutableListOf<LayoutCartOrderItemBinding>()
    var checkCnt = 0

    override fun initView(savedInstanceState: Bundle?) {
        binding.llTotalSelect.setOnClickListener {
            binding.cbTotal.isChecked = !binding.cbTotal.isChecked

            childList.forEach {
                it.cbSub.isChecked = binding.cbTotal.isChecked
            }
        }
        binding.cbTotal.setOnCheckedChangeListener { compoundButton, b ->

        }
        binding.vm = viewModel

        binding.btnConfirm.setOnClickListener {
            val res = viewModel.res.value
            res?.let{ datas->
                val action = CartFragmentDirections.actionCartFragmentToPaymentFragment(datas.toTypedArray())
                findNavController().navigate(action)
            }
        }
    }

    override fun initDataBinding() {
        viewModel.res.observe(viewLifecycleOwner){
            it?.let{
                setCartList(it)
                viewModel.totalPrice.postValue(0)
//                if(!binding.cbTotal.isChecked)
//                    binding.llTotalSelect.performClick()
            }
        }
    }

    override fun initViewCreated() {
        viewModel.requestCartList()
    }

    fun setCartList(datas:List<BasketListItem>){
        childList.clear()
        binding.llContainer.removeAllViews()
        datas.forEach {
            val layoutInflater = LayoutInflater.from(requireContext())
            val view = LayoutCartOrderItemBinding.inflate(layoutInflater, binding.llContainer, true)

            view.data = it
            view.llCheck.setOnClickListener {
                view.cbSub.isChecked = !view.cbSub.isChecked
            }

            childList.add(view)
        }

        childList.forEach {
            it.cbSub.setOnCheckedChangeListener { compoundButton, b ->
                if(b){
                    val prevPrice = viewModel.totalPrice.value
                    prevPrice?.let{ prev->
                        val itemPrice = it.data?.reformPrice?:"0"
                        viewModel.totalPrice.value = prev+itemPrice.toInt()
                    }
                    checkCnt++

                    if(checkCnt == childList.size){
                        binding.cbTotal.isChecked = true
                    }
                }else{
                    val prevPrice = viewModel.totalPrice.value
                    prevPrice?.let{ prev->
                        val itemPrice = it.data?.reformPrice?:"0"
                        viewModel.totalPrice.value = prev-itemPrice.toInt()
                    }
                    checkCnt--
                    binding.cbTotal.isChecked = false
                }
            }
        }

    }
}