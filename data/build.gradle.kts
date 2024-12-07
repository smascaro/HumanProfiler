plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "mas.ca.humanprofiler.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    ksp {
        arg("room.schemaLocation", "$rootDir/db_schemas")
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":utils"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    api(libs.retrofit)
    implementation(libs.gson.converter)
    implementation(libs.okhttp)
    implementation(libs.timber)
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
//    testImplementation Testing.junit.jupiter.api
    testImplementation(libs.junit.jupiter)
//    testRuntimeOnly Testing.junit.jupiter.engine
    /*testRuntimeOnly(libs.junit.jupiter.engine)*/
//    testRuntimeOnly "org.junit.vintage:junit-vintage-engine:_"
    testRuntimeOnly(libs.junit.vintage.engine)
//    testImplementation Testing.mockito.core
    testImplementation(libs.mockito)

    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.junit)

//    androidTestUtil(libs.androidx.test.orchestrator)

//    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}