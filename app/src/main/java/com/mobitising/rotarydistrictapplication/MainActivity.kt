package com.mobitising.rotarydistrictapplication


import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.mobitising.rotarydistrictapplication.utils.FancyGifDialog
import mobitising.rotarydistrictapplication.R


class MainActivity : AppCompatActivity() {
    private var webView: WebView? = null
    private lateinit var main_cl: ConstraintLayout
    private lateinit var lay: LinearLayout
    val STARTUP_DELAY = 300
    val ANIM_ITEM_DURATION = 1000
    val ITEM_DELAY = 300

    private val STORAGE_PERMISSION_CODE = 1

    private var first_time=0
    private var first_time_post=0
    private val animationStarted = false
    //    private var loader: Loader? = null
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(R.style.AppTheme)
//        window.decorView.systemUiVisibility =
//            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        requestStoragePermission()
        lay = findViewById<LinearLayout>(R.id.reportsLayout)
        main_cl = findViewById<ConstraintLayout>(R.id.main_cl)

        // load the animation

        // load the animation


        webView = findViewById(R.id.webview)
//        loader = Loader()

//        loader!!.showDialog(this@MainActivity)

        if (internetIsConnected()) {


            startWebView("https://rotary3271.org/",savedInstanceState)
        } else {

//            loader!!.HideLoader()
            //Showing and creating an alet dialog
            val alertDialog = AlertDialog.Builder(this@MainActivity).create()
            alertDialog.setTitle("Sorry")
            alertDialog.setMessage("Please Check Your Internet Connection")
            alertDialog.setButton(
                "Again"
            ) { _, _ ->
                if (internetIsConnected()) {
//                    loader!!.showDialog(this@MainActivity)
                    startWebView("https://rotary3271.org/", savedInstanceState)
                } else {
                    alertDialog.hide()
                    this@MainActivity.finish()
                    startActivity(this@MainActivity.intent)
                }
            }
            alertDialog.show()
        }

    }
//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        if (!hasFocus || animationStarted) {
//            return
//        }
//        animate()
//        super.onWindowFocusChanged(hasFocus)
//    }
    private fun animate() {
        main_cl.visibility= View.VISIBLE
    }

    override fun onResume() {
        super.onResume()

        if (first_time==0){
            webView!!.visibility= View.GONE
            main_cl.visibility= View.VISIBLE
            first_time++
        }else{
            webView!!.visibility= View.VISIBLE
            main_cl.visibility= View.GONE
        }
        Log.d("mChecking","resume")

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        webView!!.saveState(outState);

    }

    private fun ExitApp() {
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
    private fun startWebView(url: String, savedInstanceState: Bundle?) {
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
                //hide loading image
                main_cl.visibility = View.GONE;

                val enter = AnimationUtils.loadAnimation(this@MainActivity, R.anim.enter_from_right)
                webView!!.startAnimation(enter)
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //show webview
                webView!!.visibility = View.VISIBLE;
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
        webView!!.setDownloadListener(DownloadListener { url: String?, userAgent: String?, contentDisposition: String?, mimeType: String?,
                                                         contentLength: Long ->
            val source = Uri.parse(url)
            val request = DownloadManager.Request(source)
            val cookies = CookieManager.getInstance().getCookie(url)
            request.addRequestHeader("cookie", cookies)
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("Downloading File...")
            request.setTitle(
                URLUtil.guessFileName(
                    url,
                    contentDisposition,
                    mimeType
                )
            )
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                URLUtil.guessFileName(url, contentDisposition, mimeType)
            )
            val dm =
                getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(applicationContext, "Downloading File", Toast.LENGTH_LONG).show()
        })
        if (savedInstanceState != null)
            webView!!.restoreState(savedInstanceState)
        else
            webView!!.loadUrl(url)

    }

    override fun onPostResume() {
        super.onPostResume()
        if (first_time_post==0){
            webView!!.visibility= View.GONE
            main_cl.visibility= View.VISIBLE
            first_time_post++
        }else{

            webView!!.visibility= View.VISIBLE
            main_cl.visibility= View.GONE
        }
        Log.d("mChecking","post resume ")
    }

    override fun onRestart() {
        super.onRestart()
        webView!!.visibility= View.VISIBLE
        main_cl.visibility= View.GONE
        Log.d("mChecking","restart")
    }

    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            webView?.goBack()
        } else {
            ExitApp()

        }
    }
}