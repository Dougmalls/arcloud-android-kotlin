package com.banuba.sdk.example.effect_player_arcloud_example.arcloud

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.banuba.sdk.effect_player.Effect
import com.banuba.sdk.example.effect_player_arcloud_example.*
import com.banuba.sdk.input.CameraDevice
import com.banuba.sdk.input.CameraInput
import com.banuba.sdk.output.SurfaceOutput
import com.banuba.sdk.player.Player
import com.banuba.sdk.player.PlayerTouchListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_ar_cloud_effects.*
import kotlinx.android.synthetic.main.activity_camera_preview.surfaceView
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArCloudMasksActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_APPLY_MASK_PERMISSION = 1001

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private val effectsViewModel by viewModel<EffectsViewModel>()

    private val player by lazy(LazyThreadSafetyMode.NONE) {
        Player()
    }

    private val cameraDevice by lazy(LazyThreadSafetyMode.NONE) {
        CameraDevice(requireNotNull(this.applicationContext), this@ArCloudMasksActivity)
    }

    private val surfaceOutput by lazy(LazyThreadSafetyMode.NONE) {
        SurfaceOutput(surfaceView.holder)
    }

    var effectsAdapter: EffectsAdapter? = null

    private var effect: Effect? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar_cloud_effects)

        player.use(CameraInput(cameraDevice))
        player.use(surfaceOutput)

        // Set custom OnTouchListener to change mask style.
        surfaceView.setOnTouchListener(PlayerTouchListener(this, player))

        effectsAdapter = EffectsAdapter(Glide.with(this))
        effectsAdapter?.actionCallback = object : EffectsAdapter.ActionCallback {
            override fun onEffectSelected(checkableEffect: EffectWrapper, position: Int) {
                effectsViewModel.setLastEffect(checkableEffect)
                effect = if (position == 0) {
                    player.loadAsync("")
                    null
                } else {
                    player.loadAsync(checkableEffect.effect.uri)
                }
            }

            override fun onEffectStartDownloading(checkableEffect: EffectWrapper, position: Int) {
                effectsViewModel.downloadEffect(checkableEffect)
            }
        }

        effectsRv.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = effectsAdapter
            itemAnimator = null
        }

        effectsViewModel.effectsWrapperData.observe(this, Observer { effectsList ->
            effectsAdapter?.submitList(effectsList)
        })
        effectsViewModel.effectDownloadingSuccessData.observe(this, Observer { downloadingEffectWrapper ->
            effect = player.loadAsync(downloadingEffectWrapper.effect.uri)
        })


        effectsViewModel.load()
    }

    override fun onStart() {
        super.onStart()
        if (allPermissionsGranted()) {
            cameraDevice.start()
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_APPLY_MASK_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, results: IntArray
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
        cameraDevice.stop()
        super.onStop()
    }

    override fun onDestroy() {
        cameraDevice.close()
        surfaceOutput.close()
        player.close()
        super.onDestroy()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

}