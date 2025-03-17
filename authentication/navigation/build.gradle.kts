plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.codeturtle.notes.navigation"
    buildFeatures {
        compose = true
    }
}

dependencies {
    //modules
    implementation(project(":navigation"))
    implementation(project(":authentication:login:presentation"))
    implementation(project(":authentication:registration:presentation"))

    //core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //core junit test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //navigation
    implementation(libs.androidx.navigation.compose)
}