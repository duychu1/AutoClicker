plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.protobuf)

}

android {
    namespace = "com.duycomp.autoclicker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.duycomp.autoclicker"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get().toString()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

//    sourceSets.register(this.name) {
//        val buildDir = layout.buildDirectory.get().asFile
//        java.srcDir(buildDir.resolve("generated/source/proto/${it.name}/java"))
//        kotlin.srcDir(buildDir.resolve("generated/source/proto/${it.name}/kotlin"))
//    }
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

//androidComponents {
//    onVariants(selector().all()) { variant ->
//        afterEvaluate {
//            // This is a workaround for https://issuetracker.google.com/301244513 which depends on internal
//            // implementations of the android gradle plugin and the ksp gradle plugin which might change in the future
//            // in an unpredictable way.
////            project.tasks.getByName("ksp${variant.name.capitalize()}Kotlin").configure {
////                val buildConfigTask = project.tasks.getByName("generate${variant.name.capitalize()}BuildConfig") as com.android.build.gradle.tasks.GenerateBuildConfig
////
////                (it as org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompileTool).source = buildConfigTask.sourceOutputDir
////            }
//
//            project.tasks.getByName("ksp" + variant.name.capitalized() + "Kotlin") {
//                val buildConfigTask =
//                    project.tasks.getByName("generate${variant.name.replaceFirstChar {
//                        if (it.isLowerCase()) it.titlecase(
//                            Locale.getDefault()
//                        ) else it.toString()
//                    }}BuildConfig") as com.android.build.gradle.tasks.GenerateBuildConfig
//                (this as org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompileTool<*>).setSource(buildConfigTask.sourceOutputDir)
//            }
//        }
//    }
//}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material.iconsExtended)

    implementation(libs.hilt.navigation.compose)
//    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)


    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidxComposeLibs)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //database
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)


    implementation(libs.moshi.core)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    //datastore
    implementation(libs.dataStore.core)
    implementation(libs.protobuf.kotlin.lite)




























    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}