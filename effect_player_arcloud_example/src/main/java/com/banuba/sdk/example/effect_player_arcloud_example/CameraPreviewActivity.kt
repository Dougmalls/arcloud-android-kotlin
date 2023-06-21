package com.banuba.sdk.example.effect_player_arcloud_example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.banuba.sdk.input.CameraDevice
import com.banuba.sdk.input.CameraDeviceConfigurator
import com.banuba.sdk.input.CameraInput
import com.banuba.sdk.output.SurfaceOutput
import com.banuba.sdk.player.Player
import kotlinx.android.synthetic.main.activity_camera_preview.*

/**
 * Sample activity that shows how to open Android camera with Banuba SDK.
 */
class CameraPreviewActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_CAMERA_PREVIEW_PERMISSION = 1000

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private val player by lazy(LazyThreadSafetyMode.NONE) {
        Player()
    }

    private val cameraDevice by lazy(LazyThreadSafetyMode.NONE) {
        CameraDevice(requireNotNull(this.applicationContext), this@CameraPreviewActivity)
    }

    private val surfaceOutput by lazy(LazyThreadSafetyMode.NONE) {
        SurfaceOutput(surfaceView.holder)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_preview)
    }

    override fun onStart() {
        super.onStart()
        player.use(CameraInput(cameraDevice))
        player.use(surfaceOutput)

        if (allPermissionsGranted()) {
            cameraDevice.start()
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_CAMERA_PREVIEW_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            results: IntArray
    ) {
        if (requireAllPermissionsGranted(permissions, results)) {
            cameraDevice.start()
        } else {
            finish()
        }
        super.onRequestPermissionsResult(requestCode, permissions, results)
    }

    override fun onResume() {
        super.onResume()
        player.play()
    }

    override fun onPause() {
        super.onPause()
        player.pause()
    }

    override fun onStop() {
        super.onStop()
        cameraDevice.close()
        surfaceOutput.close()
        player.close()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}