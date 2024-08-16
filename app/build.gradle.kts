import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id ("com.google.gms.google-services")
    //Kotlin parcelize annotation plugin
    id("kotlin-parcelize")
    //kotlin annotation plugin
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.recipesearchapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.recipesearchapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localPropertiesFile.inputStream().use { stream ->
                properties.load(stream)
            }
        }

        // Retrieve the properties
        val apiKey1: String = properties.getProperty("api_1.key", "")
        val apiKey2: String = properties.getProperty("api_2.key", "")
        val apiKey3: String = properties.getProperty("api_3.key", "")
        val apiKey4: String = properties.getProperty("api_4.key", "")

        buildConfigField("String", "API_KEY_1", "\"${apiKey1}\"")
        buildConfigField("String", "API_KEY_2", "\"${apiKey2}\"")
        buildConfigField("String", "API_KEY_3", "\"${apiKey3}\"")
        buildConfigField("String", "API_KEY_4", "\"${apiKey4}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Retrofit for API requests
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("com.google.code.gson:gson:2.11.0")
    // ViewModel and LiveData for MVVM architecture
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.8.3")
    // Dagger 2
    implementation ("com.google.dagger:dagger:2.51.1")
    kapt ("com.google.dagger:dagger-compiler:2.51.1")
    // Coil for url to image
    implementation("io.coil-kt:coil-compose:2.6.0")
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")
    // Room and Lifecycle dependencies
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation(libs.androidx.runtime.livedata)
    kapt("androidx.room:room-compiler:2.6.1")
    // kotlin extensions for coroutine support with room
    implementation("androidx.room:room-ktx:2.6.1")
    // kotlin extension for coroutine support with activities
    implementation ("androidx.activity:activity-ktx:1.9.0")
    // Firebase SDK and Google Auth
    implementation ("com.google.firebase:firebase-auth-ktx:23.0.0")
    implementation ("com.google.android.gms:play-services-auth:21.2.0")
    // WorkManager
    implementation ("androidx.work:work-runtime-ktx:2.9.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}