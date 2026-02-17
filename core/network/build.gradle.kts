plugins {
    id("skydoves.pokedex.android.library")
    id("skydoves.pokedex.android.hilt")
    id("skydoves.pokedex.spotless")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.course.core.network"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.model)

    // coroutines
    implementation(libs.kotlinx.coroutines.android)

    // network
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.auth)

    // json parsing
    implementation(libs.kotlinx.serialization.json)
}
