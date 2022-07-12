package com.oldee.user.ui.faq

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.databinding.FragmentFaqBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FaqFragment : BaseFragment<FragmentFaqBinding, FaqViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_faq
    override val viewModel: FaqViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.layoutFaqItem.clArr.setOnClickListener {
            binding.layoutFaqItem.cbArr.isChecked = !binding.layoutFaqItem.cbArr.isChecked
        }
        binding.layoutFaqItem.cbArr.setOnCheckedChangeListener { compoundButton, b ->
            val card = binding.layoutFaqItem
            if(b){
                TransitionManager.beginDelayedTransition(
                    card.cvRoot,
                    AutoTransition()
                )
                card.clHide.visibility = View.VISIBLE
            }else{
                TransitionManager.beginDelayedTransition(
                    card.cvRoot,
                    AutoTransition()
                )
                card.clHide.visibility = View.GONE
            }
//            binding.layoutFaqItem.tvContents.visibility = if(b) View.VISIBLE else View.GONE
        }

        binding.clKakao.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://pf.kakao.com/_QuRxmb")))
        }
//        val card = binding.layoutFaqItem
//        card.clFixed.setOnClickListener {
//            if(card.clHide.visibility == View.VISIBLE){
//                TransitionManager.beginDelayedTransition(
//                    card.cvRoot,
//                    AutoTransition()
//                )
//                card.clHide.visibility = View.GONE
//            }else{
//                TransitionManager.beginDelayedTransition(
//                    card.cvRoot,
//                    AutoTransition()
//                )
//                card.clHide.visibility = View.VISIBLE
//            }
//        }
    }

    override fun initDataBinding() {

    }

    override fun initViewCreated() {

    }

}