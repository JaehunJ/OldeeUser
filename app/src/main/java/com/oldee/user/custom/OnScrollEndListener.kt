package com.oldee.user.custom

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class OnScrollEndListener(val callBack:()->Unit):RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        //top 스크롤 리프래시 방지
        if (!recyclerView.canScrollVertically(-1) && !recyclerView.canScrollVertically(1))
            return

        //bottom 스크롤 리프래시
        if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
            Log.e("#debug", "refresh")
            callBack.invoke()
        }
    }
}