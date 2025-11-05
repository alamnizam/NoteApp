plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger)
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
    implementation(project(":authentication:navigation"))
    implementation(project(":notes:navigation"))

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

    //hilt testing for androidTest
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)

    //compose UI testing for androidTest
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.ui.tooling)

    //datastore - required for Hilt component generation
    implementation(libs.datastore.preferences)

    //retrofit - required for Hilt component generation (used by data modules)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //okhttp - required for Hilt component generation
    implementation(libs.okhttp)

    //room - required for Hilt component generation (used by data modules)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //navigation
    implementation(libs.androidx.navigation.compose)
}