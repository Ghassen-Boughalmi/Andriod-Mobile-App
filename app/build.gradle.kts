plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.appproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appproject"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.google.android.material:material:1.8.0")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation ("com.github.bumptech.glide:glide:4.15.1") // Use the latest version
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1") // // For annotations
    implementation ("com.google.code.gson:gson:2.10.1")


}