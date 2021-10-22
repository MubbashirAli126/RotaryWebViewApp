package com.mobitising.rotarydistrictapplication

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import mobitising.rotarydistrictapplication.R

class FinalSplashActivity : AppCompatActivity() {
//    private var appUpdateManager: AppUpdateManager? = null
//    private var installStateUpdatedListener: InstallStateUpdatedListener? = null
//    private val FLEXIBLE_APP_UPDATE_REQ_CODE = 123
    private val TAG:String =FinalSplashActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_splash)
//        appUpdateManager = AppUpdateManagerFactory.create(this@FinalSplashActivity)
//        installStateUpdatedListener = InstallStateUpdatedListener { state: InstallState ->
//            when {
//                state.installStatus() == InstallStatus.DOWNLOADED -> {
//                    popupSnackBarForCompleteUpdate()
//                }
//                state.installStatus() == InstallStatus.INSTALLED -> {
//                    removeInstallStateUpdateListener()
//                }
//                else -> {
//                    Toast.makeText(
//                        applicationContext,
//                        "InstallStateUpdatedListener: state: " + state.installStatus(),
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//        }
//        appUpdateManager!!.registerListener(installStateUpdatedListener!!)
        val motionLayout:MotionLayout =findViewById(R.id.finalMotionLayout)
        motionLayout.addTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {

                if(!internetIsConnected()){
                    val alertDialog = AlertDialog.Builder(this@FinalSplashActivity).create()
                    alertDialog.setTitle("Sorry")
                    alertDialog.setMessage("Please Check Your Internet Connection")
                    alertDialog.setButton(
                        "Again"
                    ) { _, _ ->
                        alertDialog.hide();

                        this@FinalSplashActivity.finish()
                        startActivity(this@FinalSplashActivity.intent)
//                        if (internetIsConnected()){
//                            checkUpdate()
//
//                        }else{

//                        }
                    }
                    alertDialog.show()
                }else{

                    //                    checkUpdate()
                    this@FinalSplashActivity.finish()

                    startActivity(Intent(this@FinalSplashActivity,MainActivity::class.java))
                }

            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }

        })
    }
//    private fun checkUpdate() {
//        val appUpdateInfoTask = appUpdateManager?.appUpdateInfo
//        appUpdateInfoTask?.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
//            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
//            ) {
//                startUpdateFlow(appUpdateInfo)
//            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
//                popupSnackBarForCompleteUpdate()
//            }
//        }
//    }
//
//    private fun startUpdateFlow(appUpdateInfo: AppUpdateInfo) {
//        try {
//            appUpdateManager?.startUpdateFlowForResult(
//                appUpdateInfo,
//                AppUpdateType.FLEXIBLE,
//                this,
//                 FLEXIBLE_APP_UPDATE_REQ_CODE
//            )
//        } catch (e: SendIntentException) {
//            e.printStackTrace()
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == FLEXIBLE_APP_UPDATE_REQ_CODE) {
//            if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(
//                    applicationContext,
//                    "Update canceled by user! Result Code: $resultCode", Toast.LENGTH_LONG
//                ).show()
//            } else if (resultCode == RESULT_OK) {
//                Toast.makeText(
//                    applicationContext,
//                    "Update success! Result Code: $resultCode", Toast.LENGTH_LONG
//                ).show()
//            } else {
//                Toast.makeText(
//                    applicationContext,
//                    "Update Failed! Result Code: $resultCode",
//                    Toast.LENGTH_LONG
//                ).show()
//                checkUpdate()
//            }
//        }
//    }
//
//    private fun popupSnackBarForCompleteUpdate() {
//        Snackbar.make(
//            findViewById<View>(android.R.id.content).rootView,
//            "New app is ready!",
//            Snackbar.LENGTH_INDEFINITE
//        )
//            .setAction("Install") {
//                if (appUpdateManager != null) {
//                    appUpdateManager!!.completeUpdate()
//                }
//            }
//            .setActionTextColor(resources.getColor(R.color.purple_500))
//            .show()
//    }
//
//    private fun removeInstallStateUpdateListener() {
//        if (appUpdateManager != null) {
//            installStateUpdatedListener?.let { appUpdateManager!!.unregisterListener(it) }
//        }
//    }

//    override fun onStop() {
//        super.onStop()
//        removeInstallStateUpdateListener()
//    }
//    private fun checkUpdate() {
//        // Returns an intent object that you use to check for an update.
//        val appUpdateInfoTask = appUpdateManager?.appUpdateInfo
//        // Checks that the platform will allow the specified type of update.
//        Log.d(TAG, "Checking for updates")
//        appUpdateInfoTask?.addOnSuccessListener { appUpdateInfo ->
//            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
//                // Request the update.
//                Log.d(TAG, "Update available")
//            } else {
//                Log.d(TAG, "No Update available")
//            }
//        }
//    }
    private fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: java.lang.Exception) {
            false
        }
    }
}