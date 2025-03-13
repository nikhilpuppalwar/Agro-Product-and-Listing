plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)

}

android {
    namespace = "pccoe.pune.agroproduct"
    compileSdk = 35

    defaultConfig {
        applicationId = "pccoe.pune.agroproduct"
        minSdk = 28
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // circular image
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    //dowload online images
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //dot indicator
    implementation("com.tbuonomo:dotsindicator:5.1.0")
    //gson to conver
    implementation("com.google.code.gson:gson:2.12.1")
    //another for loading image
    implementation("com.squareup.picasso:picasso:2.8")
    //slider
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    //phonepe intergration
    implementation("com.razorpay:checkout:1.6.40")
// lottie animation
    implementation("com.airbnb.android:lottie:6.6.2")


}