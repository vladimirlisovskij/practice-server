import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlin.kapt)
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(libs.project.entities.network)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.sqldelight.driver)
    implementation(libs.bouncycastle)
    implementation(libs.dagger.core)

    kapt(libs.dagger.compiler)

    implementation(libs.bundles.ktor.core)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.ktor.auth)

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

sqldelight {
    database("Database") {
        sourceFolders = listOf("resources/sqldelight")
        packageName = "com.practice.server"
    }
}