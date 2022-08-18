package com.oldee.user.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.oldee.user.custom.PaymentsWebClient
import com.oldee.user.databinding.ActivityTossWebBinding
import com.oldee.user.ui.dialog.OneButtonDialog
import com.oldee.user.ui.dialog.TwoButtonDialog
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TossWebActivity : AppCompatActivity() {
    lateinit var binding: ActivityTossWebBinding

    var html: String? = """<head>
      <script src="https://js.tosspayments.com/v1"></script>
    </head>
    <body>
    <script>
    
          let addressId = 90;
          let basketList = [{"categoryCode":null,"classCode":null,"mainCategoryCode":null,"mainCategoryName":null,"subCategoryCode":null,"subCategoryName":null,"price":null,"mainOrder":null,"subOrder":null,"creationDate":null,"modifiedDate":null,"basketId":358,"userUUId":0,"surveySeq":1,"orderDetailTitle":"\uD14C\uC2A4\uD2B8"}];
          let orderPrice = 181000;
          let shippingFee = 3000;
          let totalPrice = 184000;
 
    
          let amount = "999";
    
          var orderId = new Date().getTime();
          let orderName = "\uD14C\uC2A4\uD2B8 \uD488\uBAA9";
          let customerName = "\uACB0\uC81C\uD14C\uC2A4\uD2B8\uC774\uB984";
          let clientKey = "test_ck_JQbgMGZzorzzXdypGB7rl5E1em4d";
    
          var tossPayments = TossPayments(clientKey);
          var button = document.getElementById('payment-button');

          console.log('url'+ window.location.origin)
    
          tossPayments.requestPayment('카드', {
              amount: amount,
              orderId: orderId,
              orderName: orderName,
              customerName: customerName,
              successUrl: window.location.origin + '/api/v1/payment/success',
              failUrl: window.location.origin + '/api/v1/payment/fail',
          });
    
        </script>
    </body>
    </html>"""

//    @Inject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTossWebBinding.inflate(layoutInflater)

        setContentView(binding.root)

        html = intent?.getStringExtra("html")

        val webview = binding.webView

        webview.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            cacheMode = WebSettings.LOAD_DEFAULT
            domStorageEnabled = true
        }
        webview.webViewClient = PaymentsWebClient(this)
        webview.webChromeClient = MyWeb(this)
        webview.clearCache(true)
        webview.clearHistory()
        webview.addJavascriptInterface(TossInterface(this), "OldeeAndroid")
        webview.loadDataWithBaseURL(null, html ?: "", "text/html; charset=utf-8", "UTF-8", null)
    }


    inner class MyWeb(private val activity:AppCompatActivity) : WebChromeClient() {
        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            Logger.e(consoleMessage?.message()?:"")

            return true
        }

        override fun onJsAlert(
            view: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {
            val dialog = OneButtonDialog("", message?:"","확인"){
                result?.confirm()
            }

            dialog.show(activity.supportFragmentManager, "")
            return true
        }

        override fun onJsConfirm(
            view: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {
            val dialog = TwoButtonDialog("", message?:"","확인","취소",{
                result?.confirm()
            }){
                result?.cancel()
            }

            dialog.show(activity.supportFragmentManager, "")
            return true
        }
    }

    inner class TossInterface(val context: Context){
        @JavascriptInterface
        fun paymentSuccess(id:Int){
            Logger.e("result success order id-> ${id}")
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("id", id)
            setResult(RESULT_OK, intent)
            finish()
        }

        @JavascriptInterface
        fun paymentFail(code:String, msg:String){
            Logger.e("result fail->$code / $msg")
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}