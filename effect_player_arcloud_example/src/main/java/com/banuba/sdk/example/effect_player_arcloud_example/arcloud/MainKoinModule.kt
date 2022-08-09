package com.banuba.sdk.example.effect_player_arcloud_example.arcloud

import com.banuba.sdk.arcloud.data.source.ArEffectsRepositoryProvider
import com.banuba.sdk.example.common.BANUBA_AR_CLOUD_URL
import com.banuba.sdk.example.common.BANUBA_CLIENT_TOKEN
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

class MainKoinModule {

    val module = module {

        single(named("banubaLocalToken")) {
            BANUBA_CLIENT_TOKEN
        }

        single(named("arEffectsCloudUrl")) {
            BANUBA_AR_CLOUD_URL
        }

        single(createdAtStart = true) {
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
