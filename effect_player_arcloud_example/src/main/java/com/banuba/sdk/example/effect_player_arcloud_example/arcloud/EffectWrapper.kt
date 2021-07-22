package com.banuba.sdk.example.effect_player_arcloud_example.arcloud

import com.banuba.sdk.arcloud.data.source.model.ArEffect

data class EffectWrapper(
    var effect: ArEffect,
    var isDownloading: Boolean = false
)