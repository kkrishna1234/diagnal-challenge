object Libraries {

    // Android
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX_VERSION}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT_VERSION}"

    // UI
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT_VERSION}"
    const val MATERIAL_UI = "com.google.android.material:material:${Versions.MATERIAL_UI_VERSION}"

    // Architecture components
    const val VIEWMODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_VERSION}"
    const val LIVE_DATA_KTX =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_VERSION}"
    const val PAGING = "androidx.paging:paging-runtime:${Versions.PAGING_LIBRARY_VERSION}"

    // Dependecy injection
    const val KOIN_CORE = "io.insert-koin:koin-core:${Versions.KOIN_VERSION}"
    const val KOIN_ANDROID = "io.insert-koin:koin-android:${Versions.KOIN_VERSION}"

    // Converters
    const val GSON_CONVERTER =
        "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT_VERSION}"

    // Coroutines
    const val COROUTINE_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINE_VERSION}"

    //Timber
    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER_VERSION}"

    // Unit Testing
    const val JUNIT = "junit:junit:${Versions.JUNIT_VERSION}"
    const val JUNIT_EXT = "androidx.test.ext:junit:${Versions.JUNIT_EXT_VERSION}"
    const val MOCKK = "io.mockk:mockk:${Versions.MOCKK_VERSION}"
    const val ARCH_CORE_TESTING =
        "android.arch.core:core-testing:${Versions.ARCH_CORE_TESTING_VERSION}"

    // UI testing
    const val ESPRESSO_CORE =
        "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE_VERSION}"
    const val UI_TEST_RUNNER = "androidx.test:runner:${Versions.UI_TEST_RUNNER_VERSION}"
    const val UI_TEST_RULES = "androidx.test:rules:${Versions.UI_TEST_RUNNER_VERSION}"
}

object AndroidSdk {
    const val min = 21
    const val compile = 31
    const val target = compile
}

object AndroidClient {
    const val appId = "com.android.diagnalmovies"
    const val versionCode = 1
    const val versionName = "1.0"
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object Kotlin {
    const val standardLibrary = "1.5.21"
}

object BuildPlugins {
    object Versions {
        const val buildToolsVersion = "7.0.2"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.standardLibrary}"
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
}
