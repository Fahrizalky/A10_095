plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.tanipintar"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tanipintar"
        minSdk = 21
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.ui.v150)
    implementation(libs.androidx.material.v150)
    implementation(libs.androidx.ui.tooling.preview.v150)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlin.stdlib)
    debugImplementation(libs.androidx.ui.tooling.v150)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Material 3
    implementation(libs.androidx.material3.v120)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)
    implementation (libs.mysql.connector.java)

    implementation (libs.okhttp)

    // Tambahkan ini
    implementation(libs.androidx.runtime.livedata)


    implementation (libs.kotlinx.coroutines.core)

    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.compose.v253)

    implementation (libs.androidx.material.icons.extended)


    implementation (libs.androidx.ui.v151 )// atau versi terbar
    implementation (libs.material.v151)
    implementation (libs.androidx.ui.tooling.v151)
    implementation (libs.logging.interceptor)

    implementation (libs.androidx.material3.v120alpha02)

}
