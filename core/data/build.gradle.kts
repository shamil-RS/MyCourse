plugins {
    id("skydoves.pokedex.android.library")
    id("skydoves.pokedex.android.hilt")
    id("skydoves.pokedex.spotless")
}

android {
    namespace = "com.example.course.core.data"
}

dependencies {
    // core modules
    api(projects.core.model)
    api(projects.core.network)
    api(projects.core.database)

    // coroutines
    implementation(libs.kotlinx.coroutines.android)

    // DataStore
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.datastore.preferences)

    // Kotlin Serialization for Json
    implementation(libs.kotlinx.serialization.json)


}
