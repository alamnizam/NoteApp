
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all subprojects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.dagger) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.library) apply false
}

fun CommonExtension.configureAndroidDefaults(){
    compileSdk = 37
    defaultConfig.minSdk = 24
    defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    defaultConfig.vectorDrawables.useSupportLibrary = true

    when (this) {
        is ApplicationExtension -> defaultConfig.targetSdk = 37
        is LibraryExtension -> defaultConfig.consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions.sourceCompatibility = JavaVersion.VERSION_17
    compileOptions.targetCompatibility = JavaVersion.VERSION_17
    testOptions.unitTests.isIncludeAndroidResources = true
}

fun PluginContainer.applyDefaultConfig(project: Project){
    whenPluginAdded{
        when(this){
            is AppPlugin -> {
                project.extensions.getByType<ApplicationExtension>().apply {
                    configureAndroidDefaults()
                }
            }
            is LibraryPlugin -> {
                project.extensions.getByType<LibraryExtension>().apply {
                    configureAndroidDefaults()
                }
            }
            is JavaPlugin ->{
                project.extensions.getByType<JavaPluginExtension>().apply{
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
            }
        }
    }
}

subprojects {
    project.plugins.applyDefaultConfig(project)

    tasks.withType<KotlinCompile>{
        compilerOptions {
            jvmTarget.set(JVM_17)
            freeCompilerArgs.addAll(
                listOf(
                    "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
                )
            )
        }
    }
}