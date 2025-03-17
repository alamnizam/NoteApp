plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.codeturtle.notes.navigation"
    buildFeatures {
        compose = true
    }
}

dependencies {

    //modules
    implementation(project(":common"))
    implementation(project(":navigation"))
    implementation(project(":notes:note_list:presentation"))
    implementation(project(":notes:note_list:domain"))
    implementation(project(":notes:add_note:presentation"))
    implementation(project(":notes:note_details:presentation"))
    implementation(project(":notes:edit_note:presentation"))
    implementation(project(":notes:note_search:presentation"))

    //core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //core junit test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //serialization
    implementation(libs.kotlinx.serialization.json)

    //navigation
    implementation(libs.androidx.navigation.compose)
}