package com.oldee.user.custom

import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.*
import java.net.URISyntaxException

class PaymentsWebClient(val context:Context) : WebViewClient() {

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        return super.shouldInterceptRequest(view, request)
    }

    fun getMimeType(url:String){
        var type = ""
        var extention = MimeTypeMap.getFileExtensionFromUrl(url)

        when(extention){
            "js"->{

            }
            else->{

            }
        }
    }


    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        url?.let {
            if (!URLUtil.isNetworkUrl(url) && !URLUtil.isJavaScriptUrl(url)) {
                val uri = try {
                    Uri.parse(url)
                } catch (e: Exception) {
                    return false
                }

                return when (uri.scheme) {
                    "intent" -> {
                        startSchemeIntent(it)
                    }
                    else -> {
                        return try {
                            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                            true
                        } catch (e: java.lang.Exception) {
                            false
                        }
                    }
                }
            } else {
                return false
            }
        } ?: return false
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url.toString()
        url?.let {
            if (!URLUtil.isNetworkUrl(url) && !URLUtil.isJavaScriptUrl(url)) {
                val uri = try {
                    Uri.parse(url)
                } catch (e: Exception) {
                    return false
                }

                return when (uri.scheme) {
                    "intent" -> {
                        startSchemeIntent(it)
                    }
                    else -> {
                        return try {
                            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                            true
                        } catch (e: java.lang.Exception) {
                            false
                        }
                    }
                }
            } else {
                return false
            }
        } ?: return false
    }

    private fun startSchemeIntent(url: String): Boolean {
        val schemeIntent: Intent = try {
            Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
        } catch (e: URISyntaxException) {
            return false
        }
        try {
            context.startActivity(schemeIntent)
            return true
        } catch (e: ActivityNotFoundException) {
            val packageName = schemeIntent.getPackage()

            if (!packageName.isNullOrBlank()) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$packageName")
                    )
                )
                return true
            }
        }
        return false
    }
}