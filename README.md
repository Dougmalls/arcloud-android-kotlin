Quick start examples for [Banuba SDK on Android](https://docs.banuba.com/face-ar-sdk/android/android_overview).

# Getting Started

1. Get the latest Banuba SDK archive for Android and the client token. Please fill in our form on [form on banuba.com](https://www.banuba.com/face-filters-sdk) website, or contact us via [info@banuba.com](mailto:info@banuba.com).
2. Copy `aar` files from the Banuba SDK archive into `libs` dir:
    `BNBEffectPlayer/bin/banuba_sdk/banuba_sdk-release.aar` => `arcloud-android-kotlin/libs/`
    `BNBEffectPlayer/banuba_effect_player-release.aar` => `arcloud-android-kotlin/libs/`
3. Copy and Paste your client token into appropriate section of [`BANUBA_CLIENT_TOKEN`](client_token/com/banuba/sdk/example/common/BanubaClientToken.kt#L7)
4. Open the project in Android Studio and run the necessary target using the usual steps.

# AR Cloud

 1. Get the latest BanubaARCloud archive for Android and AR cloud URL. Please fill in our form on [form on banuba.com](https://www.banuba.com/face-filters-sdk) website, or contact us via [info@banuba.com](mailto:info@banuba.com). The latest version of library can be found here: [BanubaARCloud](https://github.com/Banuba/banuba-ar/packages/665586).
 2. Copy received `aar` libraries into `arcloud-android-kotlin/libs/` directory.
 3. Copy and Paste your AR cloud URL into appropriate section of [`BANUBA_AR_CLOUD_URL`](client_token/com/banuba/sdk/example/common/BanubaClientToken.kt#L8) or use predefined Demo bucket

# Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
