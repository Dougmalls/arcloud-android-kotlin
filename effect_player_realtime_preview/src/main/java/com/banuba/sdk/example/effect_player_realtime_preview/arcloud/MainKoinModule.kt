package com.banuba.sdk.example.effect_player_realtime_preview.arcloud

import com.banuba.sdk.arcloud.data.source.ArEffectsRepositoryProvider
import com.banuba.sdk.example.common.BANUBA_CLIENT_TOKEN
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

class MainKoinModule {

    val module = module {

        single(named("banubaLocalToken"), override = true) {
            BANUBA_CLIENT_TOKEN
        }

        single(createdAtStart = true, override = true) {
            ArEffectsRepositoryProvider(
                arEffectsRepository = get(named("backendArEffectsRepository")),
                ioDispatcher = get(named("ioDispatcher"))
            )
        }

        viewModel {
            EffectsViewModel(
                arEffectsRepository = get<ArEffectsRepositoryProvider>().provide()
            )
        }

    }


}