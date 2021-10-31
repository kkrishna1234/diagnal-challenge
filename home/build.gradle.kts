plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)

    // Required for kotlin synthetic binding
    id(BuildPlugins.kotlinAndroidExtensions)
}

android {
    compileSdk = AndroidSdk.compile

    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
        testInstrumentationRunner = AndroidClient.testRunner
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(path = ":core"))
    implementation(project(path = ":domain"))

    // Android
    implementation(Libraries.CORE_KTX)
    implementation(Libraries.APPCOMPAT)

    // Architecture components
    implementation(Libraries.VIEWMODEL_KTX)
    implementation(Libraries.LIVE_DATA_KTX)
    implementation(Libraries.PAGING)

    // UI
    implementation(Libraries.MATERIAL_UI)
    implementation(Libraries.CONSTRAINT_LAYOUT)

    // Dependency injection
    implementation(Libraries.KOIN_CORE)
    implementation(Libraries.KOIN_ANDROID)

    // Coroutine
    implementation(Libraries.COROUTINE_ANDROID)

    // Timber
    implementation(Libraries.TIMBER)

    // Unit Testing
    testImplementation(Libraries.JUNIT)
    androidTestImplementation(Libraries.JUNIT_EXT)
    testImplementation(Libraries.MOCKK)
    testImplementation(Libraries.ARCH_CORE_TESTING)
    testImplementation(Libraries.COROUTINE_TESTING)

    // UI testing
    androidTestImplementation(Libraries.ESPRESSO_CORE)
    androidTestImplementation(Libraries.UI_TEST_RUNNER)
    androidTestImplementation(Libraries.UI_TEST_RULES)
}
