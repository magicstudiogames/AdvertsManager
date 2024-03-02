plugins {
    id("com.android.application")
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("C:\\Users\\Dell\\AndroidStudioProjects\\Projects\\AdvertsManager\\AdvertsManager.jks")
            storePassword = "@#AdvertsManager#4021#@"
            keyAlias = "Adverts Manager"
            keyPassword = "@#AdvertsManager#4021#@"
        }
        create("release") {
            storeFile = file("C:\\Users\\Dell\\AndroidStudioProjects\\Projects\\AdvertsManager\\AdvertsManager.jks")
            storePassword = "@#AdvertsManager#4021#@"
            keyAlias = "Adverts Manager"
            keyPassword = "@#AdvertsManager#4021#@"
        }
    }
    namespace = "com.magicstudiogames.advertsmanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.magicstudiogames.advertsmanager"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("release")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.preference:preference:1.2.1")
    implementation("com.google.android.material:material:1.11.0")

    implementation("com.unity3d.ads:unity-ads:4.9.3")
    implementation("com.google.android.gms:play-services-ads:22.6.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}