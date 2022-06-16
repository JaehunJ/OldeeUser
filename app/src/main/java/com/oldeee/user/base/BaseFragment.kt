package com.oldeee.user.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.oldeee.user.CommonActivityFuncImpl

abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel, NA : NavArgs> : Fragment() {
    var navController: NavController? = null

    private var _binding: T? = null

    val binding get() = _binding!!

    abstract val layoutId: Int

    abstract val viewModel: VM
    abstract val navArgs: NavArgs

    lateinit var activityFuncFunction: CommonActivityFuncImpl

    /**
     * layout infalte후 호출
     *
     * @param savedInstanceState
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 데이터 바인딩 처리
     *
     */
    abstract fun initDataBinding()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            navController = findNavController()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner

        activityFuncFunction = activity as CommonActivityFuncImpl
        viewModel.isLoading().observe(viewLifecycleOwner) {
            if (it)
                activityFuncFunction.showProgress()
            else
                activityFuncFunction.hideProgress()
        }

        initView(savedInstanceState)
        initDataBinding()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun nextFragment(dir:NavDirections){
        findNavController().navigate(dir)
    }
}