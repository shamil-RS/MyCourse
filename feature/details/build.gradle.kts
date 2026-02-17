plugins {
  id("skydoves.pokedex.android.feature")
  id("skydoves.pokedex.android.hilt")
}

android {
  namespace = "com.example.course.feature.details"
}

dependencies {
    implementation(project(":core:navigation"))

    implementation(project(":core:designsystem"))
}