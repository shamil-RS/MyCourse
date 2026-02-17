plugins {
    id("skydoves.pokedex.android.library")
    id("skydoves.pokedex.android.hilt")
    id("skydoves.pokedex.spotless")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.course.core.database"

    defaultConfig {
        // The schemas directory contains a schema file for each version of the Room database.
        // This is required to enable Room auto migrations.
        // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.network)

    // coroutines
    implementation(libs.kotlinx.coroutines.android)

    // database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // DataStore
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.datastore.preferences)

    // json parsing
    implementation(libs.kotlinx.serialization.json)

}