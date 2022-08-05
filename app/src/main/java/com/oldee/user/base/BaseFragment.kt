package com.oldee.user.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.oldee.user.CommonActivityFuncImpl
import com.oldee.user.R
import com.oldee.user.network.NoConnectionInterceptor
import com.orhanobut.logger.Logger

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

    abstract fun initViewCreated()


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
        viewModel.isLoading.observe(viewLifecycleOwner) {
            Logger.e(it.toString())
            if (it)
                activityFuncFunction.showProgress()
            else
                activityFuncFunction.hideProgress()
        }

        val backView = _binding?.root?.findViewById<ConstraintLayout>(R.id.iv_back)

        backView?.let{
            it.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        setBaseError()
        initView(savedInstanceState)
        initDataBinding()
        activityFuncFunction.hideProgress()

        return binding.root
    }

    fun setBaseError(){
        viewModel.baseOnError = {str->
            activityFuncFunction.showToast(str)
        }
        viewModel.connectionError = {
            callNetworkErrorPage()
        }
    }

    fun setOnInvokeBackAction(onClick:()->Unit){
        val backView = _binding?.root?.findViewById<ConstraintLayout>(R.id.iv_back)

        backView?.let{
            it.setOnClickListener {
                onClick()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    protected fun callApi(action:()->Unit){
//        try {
//            action()
//        }catch (e:NoConnectionInterceptor.NoConnectivityException){
//            callNetworkErrorPage()
//        }
//    }

    private fun callNetworkErrorPage(){
        findNavController().navigate(R.id.action_global_networkErrorFragment)
    }

    fun nextFragment(dir:NavDirections){
        findNavController().navigate(dir)
    }

    fun <T> getObserver(owner: LifecycleOwner, action:(T)->Unit)
        = Observer<T> { if(owner.lifecycle.currentState == Lifecycle.State.RESUMED) action.invoke(it)}
}