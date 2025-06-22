import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp").version(libs.versions.ksp.version)
    id("kotlin-parcelize")
}

val localProperties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

android {
    signingConfigs {
        /*
        getByName("debug") {
            storeFile = file(localProperties.getProperty("storeFilePath"))
            storePassword = localProperties.getProperty("storePassword")
            keyAlias = localProperties.getProperty("keyAlias")
            keyPassword = localProperties.getProperty("keyPassword")
        }
        // */
        create("release") {
            storeFile = file(localProperties.getProperty("storeFilePath"))
            storePassword = localProperties.getProperty("storePassword")
            keyAlias = localProperties.getProperty("keyAlias")
            keyPassword = localProperties.getProperty("keyPassword")
        }
    }

    compileSdk = libs.versions.compilesdk.get().toInt()

    defaultConfig {
        applicationId = "com.qihuan.photowidget"
        minSdk = libs.versions.minsdk.get().toInt()
        targetSdk = libs.versions.targetsdk.get().toInt()
        versionCode = project.property("app.versionCode").toString().toInt()
        versionName = project.property("app.versionName").toString()
    }

    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")

            val appCenterSecret = localProperties.getProperty("appCenterSecretRelease")
            buildConfigField("String", "APP_CENTER_SECRET", "\"${appCenterSecret}\"")
        }

        debug {
            signingConfig = signingConfigs.getByName("debug")

            val appCenterSecret = localProperties.getProperty("appCenterSecretDebug")
            buildConfigField("String", "APP_CENTER_SECRET", "\"${appCenterSecret}\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
    namespace = "com.qihuan.photowidget"

    applicationVariants.all {
        outputs.all {
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                outputFileName = "photowidget_${buildType.name}_${defaultConfig.versionName}.apk"
            }
        }
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.google.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)

    implementation(libs.androidx.paging)

    api(libs.androidx.room)
    ksp(libs.androidx.room.compiler)
    api(libs.androidx.room.ktx)
    api(libs.androidx.room.paging)

    implementation(libs.koin.android)

    implementation(libs.androidx.startup)
    implementation(libs.bundles.appcenter)

    // WorkManager 执行时会触发 AppWidgetProvider.onUpdate() 回调，导致不可控的行为。
    // 在 AppWidgetProvider.onUpdate() 通过 WorkManager 执行刷新微件，会导致无限循环，所以暂时改用 JobScheduler 代替。
    // 具体可见：https://medium.com/intive-developers/toss-a-coin-to-your-widget-or-dont-part-1-of-3-188c39d50b66
    // def work_version = "2.7.1"
    // implementation("androidx.work:work-runtime-ktx:$work_version")

    implementation(libs.glide)
    ksp(libs.glide.compiler)

    implementation(libs.ucrop)
    implementation(libs.compressor)
    implementation(libs.colorpickerview)
}