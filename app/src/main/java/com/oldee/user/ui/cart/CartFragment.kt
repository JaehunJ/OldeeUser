package com.oldee.user.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.databinding.FragmentCartBinding
import com.oldee.user.databinding.LayoutCartOrderItemBinding
import com.oldee.user.network.response.BasketListItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_cart
    override val viewModel: CartViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    val childList = mutableListOf<LayoutCartOrderItemBinding>()
    var checkCnt = 0
    var checkedItemDataList = mutableListOf<BasketListItem>()

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
            if(checkedItemDataList.isEmpty())
                return@setOnClickListener

            val res = checkedItemDataList
            res.let{ datas->
                val action = CartFragmentDirections.actionCartFragmentToPaymentFragment(datas.toTypedArray())
                findNavController().navigate(action)
            }
        }

        binding.tvTotalDelete.setOnClickListener {
            if(checkedItemDataList.isNotEmpty()){
                viewModel.requestCartListItemDelete(checkedItemDataList)
            }
        }
    }

    override fun initDataBinding() {
        viewModel.res.observe(viewLifecycleOwner){
            it?.let{
                init()

                if(it.isEmpty()){
                    binding.clEmpty.visibility = View.VISIBLE
                    binding.nsExist.visibility = View.GONE
                }else{
                    binding.clEmpty.visibility = View.GONE
                    binding.nsExist.visibility = View.VISIBLE
                }

                setCartList(it)
                viewModel.totalPrice.postValue(0)


                if(binding.cbTotal.isChecked){
                    binding.llTotalSelect.performClick()
                }
            }
        }
    }

    override fun initViewCreated() {
        viewModel.requestCartList()
    }

    fun init(){
        checkedItemDataList.clear()
        childList.clear()
        checkCnt = 0
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
                val d = it.data ?: return@setOnCheckedChangeListener

                if(b){
                    checkedItemDataList.add(d)
                    val prevPrice = viewModel.totalPrice.value
                    prevPrice?.let{ prev->
                        val itemPrice = d.reformPrice?:"0"
                        viewModel.totalPrice.value = prev+itemPrice.toInt()
                    }
                    checkCnt++

                    if(checkCnt == childList.size){
                        binding.cbTotal.isChecked = true
                    }
                }else{
                    checkedItemDataList.remove(d)
                    val prevPrice = viewModel.totalPrice.value
                    prevPrice?.let{ prev->
                        val itemPrice = d.reformPrice?:"0"
                        viewModel.totalPrice.value = prev-itemPrice.toInt()
                    }
                    checkCnt--
                    binding.cbTotal.isChecked = false
                }
            }
        }

    }
}