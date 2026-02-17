plugins {
    id("skydoves.pokedex.android.feature")
    id("skydoves.pokedex.android.hilt")
}

android {
    namespace = "com.example.course.feature.favorites"
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":core:domain"))

    implementation(project(":core:designsystem"))
}