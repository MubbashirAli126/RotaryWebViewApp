package com.mobitising.rotarywebviewapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.*

import android.widget.LinearLayout
import com.mobitising.rotarywebviewapplication.utils.Loader


import com.mobitising.rotarywebviewapplication.utils.FancyGifDialog
import mobitising.rotarywebviewapplication.R

class MainActivity : AppCompatActivity() {
    private var webView: WebView? = null
//    private var loader: Loader? = null
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lay: LinearLayout =findViewById<LinearLayout>(R.id.reportsLayout)

        // load the animation

        // load the animation
        val enter = AnimationUtils.loadAnimation(this@MainActivity, R.anim.enter_from_left)
        lay.startAnimation(enter)

        webView = findViewById(R.id.webview)
//        loader = Loader()

//        loader!!.showDialog(this@MainActivity)

        if(internetIsConnected()){

            startWebView("https://rotary3271.org/")
        }else{

//            loader!!.HideLoader()
            //Showing and creating an alet dialog
            val alertDialog = AlertDialog.Builder(this@MainActivity).create()
            alertDialog.setTitle("Sorry")
            alertDialog.setMessage("Please Check Your Internet Connection")
            alertDialog.setButton(
                "Again"
            ) { _, _ ->
                if (internetIsConnected()){
//                    loader!!.showDialog(this@MainActivity)
                    startWebView("https://rotary3271.org/")
                }else{
                    alertDialog.hide();
                    this@MainActivity.finish()
                    startActivity(this@MainActivity.intent)
                }
            }
            alertDialog.show()
        }

    }
    private fun ExitApp(){
        FancyGifDialog.Builder(this)
            .setTitle("Are you sure you want to Exit?") // You can also send title like R.string.from_resources
            .setMessage("") // or pass like R.string.description_from_resources
            .setTitleTextColor(R.color.titleText)
            .setDescriptionTextColor(R.color.descriptionText)
            .setNegativeBtnText("No") // or pass it like android.R.string.cancel
            .setPositiveBtnBackground(R.color.title_color)
            .setPositiveBtnText("Yes") // or pass it like android.R.string.ok
            .setNegativeBtnBackground(R.color.title_color)
            .setGifResource(R.drawable.exit) //Pass your Gif here
            .isCancellable(true)
            .OnPositiveClicked {
                finish()
                finishAffinity()
            }
            .OnNegativeClicked {
//                Toast.makeText(this@MainActivity, "Cancel", Toast.LENGTH_SHORT).show()
            }
            .build()
    }
    private fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: java.lang.Exception) {
            false
        }
    }
    //hello
    private fun startWebView(url: String) {
        val settings = webView!!.settings
        settings.javaScriptEnabled = true
        webView!!.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
        webView!!.settings.builtInZoomControls = true
        webView!!.settings.useWideViewPort = true
        webView!!.settings.loadWithOverviewMode = true

        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                   view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
//                loader!!.HideLoader()
            }

            //WebView view, WebResourceRequest req, WebResourceError rer
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                //Code here
                //Clearing the WebView
                try {
                    webView!!.stopLoading()
                } catch (ignored: Exception) {
                }
                try {
                    webView!!.clearView()
                } catch (ignored: Exception) {
                }
                if (webView!!.canGoBack()) {
                    webView!!.goBack()
                }
//                webView!!.loadUrl(url)


//                var request:WebResourceRequest=WebResourceRequest;
                //Don't forget to call supper!
//                super.onReceivedError(webView, req,rerr)
//                super.onReceivedError(webView, rerr.errorCode, rerr.description.toString(), req.url.toString())
                super.onReceivedError(webView, errorCode, description, failingUrl)
            }
        }
        webView!!.loadUrl(url)
    }
    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            webView?.goBack();
        } else {
            ExitApp()

        }
    }
}