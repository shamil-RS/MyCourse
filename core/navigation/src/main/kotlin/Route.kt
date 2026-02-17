package com.example.course.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import com.example.course.designsystem.R
import kotlinx.serialization.Serializable

@Serializable
sealed class HomeBottomBarNav(
    @StringRes val text: Int,
    @DrawableRes val icon: Int
) : NavKey {

    @Serializable
    data object HomeScreen :
        HomeBottomBarNav(text = R.string.nav_home, icon = R.drawable.ic_nav_home)

    @Serializable
    data object Bookmarks :
        HomeBottomBarNav(text = R.string.nav_bookmarks, icon = R.drawable.ic_nav_bookmarks)

    @Serializable
    data object UserScreen :
        HomeBottomBarNav(text = R.string.nav_user, icon = R.drawable.ic_nav_user)
}

val bottomNavigationBarItems = listOf(
    HomeBottomBarNav.HomeScreen,
    HomeBottomBarNav.Bookmarks,
    HomeBottomBarNav.UserScreen
)