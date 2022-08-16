package com.oldee.user.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oldee.user.BuildConfig
import com.oldee.user.custom.PaymentsWebClient
import com.oldee.user.databinding.ActivityTossWebBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.logging.Logger
import javax.inject.Inject

@AndroidEntryPoint
class TossWebActivity : AppCompatActivity() {
    lateinit var binding: ActivityTossWebBinding

    var html:String? = ""

//    @Inject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTossWebBinding.inflate(layoutInflater)

        setContentView(binding.root)

        html = intent?.getStringExtra("html")
    }

    override fun onStart() {
        super.onStart()

        val webview = binding.webView

        webview.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            domStorageEnabled = true
        }
        webview.webViewClient = PaymentsWebClient(this)
        com.orhanobut.logger.Logger.e("html null")
        webview.loadUrl(html?:"")
    }
}