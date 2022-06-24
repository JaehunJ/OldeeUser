package com.oldeee.user.ui.dialog

import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.DialogFragment
import com.oldeee.user.BuildConfig
import com.oldeee.user.R
import com.oldeee.user.databinding.DialogPostSearchBinding
import com.oldeee.user.network.response.PostResponse
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
            setSupportMultipleWindows(true)
            domStorageEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
        }
        webView.apply {
            addJavascriptInterface(PostWebInterface { road, zone ->
                dialog?.dismiss()
                confirmCallback.invoke(road, zone)
            }, "Android")
        }

        webView.webViewClient = client
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//BuildConfig
        Log.e("#debug", BuildConfig.BASE_URL + "api/v1/address")
        binding.wvPost.loadUrl(BuildConfig.BASE_URL + BuildConfig.POST_NUM_PATH)
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
            handler?.proceed()
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