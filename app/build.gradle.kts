plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    //Kotlin parcelize annotation plugin
    id("kotlin-parcelize")
    //kotlin annotation plugin
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.recipesearchapp"
    compileSdk = 34

    defaultConfig {
        buildConfigField(
            "String",
            "API_KEY_1",
            "\"${project.findProperty("API_KEY_1")}\""
        )
        buildConfigField(
            "String",
            "API_KEY_2",
            "\"${project.findProperty("API_KEY_2")}\""
        )
        buildConfigField(
            "String",
            "API_KEY_3",
            "\"${project.findProperty("API_KEY_3")}\""
        )
        buildConfigField(
            "String",
            "API_KEY_4",
            "\"${project.findProperty("API_KEY_4")}\""
        )
        applicationId = "com.example.recipesearchapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Retrofit for API requests
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.10.1")
    // ViewModel and LiveData for MVVM architecture
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.3.1")
    //Coil for url to image
    implementation("io.coil-kt:coil-compose:2.5.0")
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")
    // Room and Lifecycle dependencies
    implementation ("androidx.room:room-runtime:2.6.0-alpha02")
    kapt("androidx.room:room-compiler:2.6.1")
    //kotlin extensions for coroutine support with room
    implementation("androidx.room:room-ktx:2.6.1")
    //kotlin extension for coroutine support with activities
    implementation ("androidx.activity:activity-ktx:1.8.2")

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