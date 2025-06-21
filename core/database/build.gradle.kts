plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp").version(libs.versions.ksp.version)
}

android {
    namespace = "com.qihuan.photowidget.core.database"
    compileSdk = libs.versions.compilesdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minsdk.get().toInt()

        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.paging)

    api(libs.androidx.room)
    ksp(libs.androidx.room.compiler)
    api(libs.androidx.room.ktx)
    api(libs.androidx.room.paging)

    implementation(libs.koin.android)
}