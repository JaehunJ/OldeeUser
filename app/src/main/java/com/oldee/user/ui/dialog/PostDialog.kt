package com.oldee.user.ui.dialog

import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.oldee.user.BuildConfig
import com.oldee.user.R
import com.oldee.user.databinding.DialogPostSearchBinding
import com.oldee.user.network.response.PostResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PostDialog(val confirmCallback:(road:String, zone:String)->Unit) : DialogFragment() {
    lateinit var binding: DialogPostSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialogFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogPostSearchBinding.inflate(inflater, container, false)

        binding.ivBack.setOnClickListener {
            dismiss()
        }

        initWebView()

        return binding.root
    }

    fun initWebView() {
        val webView = binding.wvPost
        webView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            useWideViewPort = true
            loadWithOverviewMode = true
        }
        webView.apply {
            setPadding(0,0,0,0)
            setInitialScale(1)
            addJavascriptInterface(PostWebInterface { road, zone ->
                dialog?.dismiss()
                confirmCallback.invoke(road, zone)
            }, "Android")
        }
        webView.webViewClient = client
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.wvPost.loadUrl(BuildConfig.BASE_URL + BuildConfig.POST_NUM_PATH)
//        val script = StringBuilder()
//
//        script.append("javascript:(function(){")
//        script.append("document.querySelector(\\\"meta[name=viewport]\\\").setAttribute('content', 'width=device-width, initial-scale=0, maximun-scale=2.0, user-scalable=yes');\n" +
//                "document.querySelector(\\\"meta[name=viewport]\\\").setAttribute('content', 'width=device-width, initial-scale=1.0, maximun-scale=2.0, user-scalable=yes');")
//        script.append("})();")
//        binding.wvPost.loadUrl(script.toString())

    }

    private val client: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setMessage("이 사이트의 보안 인증서는 신뢰하는 보안 인증서가 아닙니다. 계속하시겠습니까?")
            dialog.setPositiveButton("계속하기"){d,i->
                handler!!.proceed()
            }
            dialog.setNegativeButton("취소"){d,i->
                handler?.cancel()
            }
            dialog.create().show()
        }
    }

    class PostWebInterface(val callback: (road: String, zone: String) -> Unit) {
        @JavascriptInterface
        fun postAddress(jsonStr: String) {
            CoroutineScope(Dispatchers.Default).launch {
                val obj = PostResponse.fromJson(jsonStr)

                obj?.let {
                    val road = it.roadAddress
                    val zoneCode = it.zonecode
                    callback.invoke(road, zoneCode)
                }
            }
        }
    }
}