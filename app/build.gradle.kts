import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.google.gms.google.services)

    // ADDED: Apply the Hilt plugin for Dependency Injection
    alias(libs.plugins.hilt.android)

    alias(libs.plugins.ksp)

    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.arlabs.raksha"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.arlabs.raksha"
        minSdk = 31
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(FileInputStream(localPropertiesFile))
        }
        val mapsApiKey = localProperties.getProperty("MAPS_API_KEY") ?: ""
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

// REMOVED: kapt block is no longer needed for Hilt if we are using KSP, 
// unless other processors still need it. But usually we replace it entirely.
// If you have other kapt processors, you might need to keep it or migrate them too.

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.compose.material:material-icons-extended:1.7.8")


    // --- NEW DEPENDENCIES START HERE ---

    // --- Architecture Dependencies (ViewModel, Navigation, Hilt) ---
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    // Hilt's annotation processor - Switched to KSP
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.android)


    // --- Firebase Dependencies (BOM manages versions for auth & firestore) ---
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)

    // --- Google Services Dependencies (Auth, Maps, and Coroutines) ---
    implementation(libs.google.play.services.auth)
    implementation(libs.google.maps.compose)
    implementation(libs.google.play.services.maps)
    implementation(libs.coroutines.play.services)
    implementation(libs.google.places)
    implementation(libs.maps.utils)
    implementation(libs.play.services.location)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.datastore.preferences)
}