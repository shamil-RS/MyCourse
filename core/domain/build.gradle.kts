plugins {
    id("skydoves.pokedex.android.library")
    id("skydoves.pokedex.android.hilt")
    id("skydoves.pokedex.spotless")
}

android {
    namespace = "com.example.course.core.domain"
}

dependencies {

    implementation(projects.core.data)
    implementation(projects.core.database)

    // Kotlin Serialization for Json
    implementation(libs.kotlinx.serialization.json)

}
