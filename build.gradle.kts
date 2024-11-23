// Top-level build file where you can add configuration options common to all sub-projects/modules.
allprojects {
//    repositories {
////        google()
////        mavenCentral()
//        maven {
//            url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
//        }
//    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
//    alias(libs.plugins.kotlin.compose) apply false
    id("org.jetbrains.compose") version "1.7.1" apply false
}