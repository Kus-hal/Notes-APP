plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")

}

android {
    namespace = "com.example.notesapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.notesapp"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.0.0")


    // RecyclerView Animator
//    implementation 'jp.wasabeef:recyclerview-animators:3.0.0'

    // Architectural Components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")


    // LiveData
    implementation( "androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    // Annotation processor
    implementation( "androidx.lifecycle:lifecycle-common-java8:2.7.0")

    // Room
    implementation( "androidx.room:room-runtime:2.6.1")
    kapt( "androidx.room:room-compiler:2.6.1")

    // Kotlin Extensions and Coroutines support for Room
    implementation( "androidx.room:room-ktx:2.6.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Coroutine Lifecycle Scopes
    implementation( "androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation( "androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // Navigation Components
    implementation( "androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation( "androidx.navigation:navigation-ui-ktx:2.7.7")

    

    //material Components
    implementation("com.google.android.material:material:1.11.0")

    //color picker library
    implementation("com.thebluealliance:spectrum:0.7.1")


}