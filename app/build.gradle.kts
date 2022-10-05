import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension

plugins {
    id("com.android.application")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
    id("io.gitlab.arturbosch.detekt")
    id("com.squareup.sqldelight")
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.finance_tracker.finance_tracker"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.finance_tracker.finance_tracker"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            (this as ExtensionAware).configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = false
            }
        }
        release {
            isMinifyEnabled = true
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
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
}

dependencies {

    // Firebase BoM
    implementation(platform ("com.google.firebase:firebase-bom:30.3.2"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-config-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    // Jetpack Compose
    val composeVersion = "1.3.0-beta01"
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.compose.animation:animation:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.navigation:navigation-compose:2.5.2")

    // LeakCanary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.9.1")

    // Chucker
    val chukerVersion = "3.5.2"
    debugImplementation("com.github.chuckerteam.chucker:library:$chukerVersion")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:$chukerVersion")

    // Core
    implementation("androidx.core:core-ktx:1.9.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    // View
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.6.1")

    // SQL Delight
    val sqlDelightVersion = "1.5.3"
    implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
    implementation("com.squareup.sqldelight:coroutines-extensions:$sqlDelightVersion")

    // Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.1")

    // Compose Destinations
    val composeDestinationsVersion = "1.7.21-beta"
    implementation("io.github.raamcosta.compose-destinations:animations-core:$composeDestinationsVersion")
    ksp("io.github.raamcosta.compose-destinations:ksp:$composeDestinationsVersion")

    // Koin
    val koinVersion = "3.2.2"
    val koinKspVersion = "1.0.3"
    val koinAndroidComposeVersion = "3.2.1"
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-annotations:$koinKspVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinAndroidComposeVersion")
    ksp("io.insert-koin:koin-ksp-compiler:$koinKspVersion")
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.finance_tracker.finance_tracker"
    }
}