plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-parcelize")
}

android {
    namespace = "com.codeturtle.notes"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.codeturtle.notes"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.codeturtle.notes.AndroidJUnitRunner"
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
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}
dependencies {
    //modules
    implementation(project(":app:navigation"))
    implementation(project(":authentication:login:data"))
    implementation(project(":authentication:login:domain"))
    implementation(project(":authentication:login:presentation"))
    implementation(project(":authentication:navigation"))
    implementation(project(":authentication:registration:data"))
    implementation(project(":authentication:registration:domain"))
    implementation(project(":authentication:registration:presentation"))
    implementation(project(":common"))
    implementation(project(":navigation"))
    implementation(project(":notes:add_note:data"))
    implementation(project(":notes:add_note:domain"))
    implementation(project(":notes:add_note:presentation"))
    implementation(project(":notes:edit_note:data"))
    implementation(project(":notes:edit_note:domain"))
    implementation(project(":notes:edit_note:presentation"))
    implementation(project(":notes:navigation"))
    implementation(project(":notes:note_details:presentation"))
    implementation(project(":notes:note_list:data"))
    implementation(project(":notes:note_list:domain"))
    implementation(project(":notes:note_list:presentation"))
    implementation(project(":notes:note_search:presentation"))


    //core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //core junit test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //dagger hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    kspAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestAnnotationProcessor(libs.hilt.android.compiler)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //compose testing
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    //Icons
    implementation(libs.androidx.material.icons.extended.android)

    //serialization
    implementation(libs.kotlinx.serialization.json)

    //navigation
    implementation(libs.androidx.navigation.compose)

    //datastore preference
    implementation(libs.datastore.preferences)

    //room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //coil
    implementation(libs.coil.compose)

    //lottie
    implementation(libs.lottie.compose)

    //test String resource
    testImplementation(libs.robolectric)

    //mockito
    testImplementation(libs.mockito.core)
    testImplementation(libs.turbine)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)

    //coroutines test
    testImplementation(libs.kotlinx.coroutines.test)

    //Idling resource
    implementation(libs.androidx.espresso.idling.resource)
    androidTestImplementation(libs.androidx.espresso.idling.resource)
    androidTestImplementation(libs.okhttp3.idling.resource)

    //mock web server
    testImplementation(libs.mockwebserver)
    androidTestImplementation(libs.mockwebserver)
    androidTestImplementation(libs.okhttp)
    androidTestImplementation(libs.okhttp.tls)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.rules)
    testImplementation(libs.junit.jupiter)
}