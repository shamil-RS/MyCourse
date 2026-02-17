package com.example.mycourse.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.course.core.navigation.CourseScreen
import com.example.course.core.navigation.HomeBottomBarNav
import com.example.course.core.navigation.SignIn
import com.example.course.core.navigation.SignUp
import com.example.course.feature.details.CourseScreen
import com.example.course.feature.favorites.FavoriteScreen
import com.example.course.feature.home.HomeScreen
import com.example.course.feature.signIn.SignInScreen
import com.example.course.feature.signUp.SignUpScreen
import com.example.course.feature.user.UserScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavStack(
    modifier: Modifier,
    navBackStack: NavBackStack<NavKey>,
) {
    NavDisplay(
        backStack = navBackStack,
        onBack = { navBackStack.removeLastOrNull() },
        modifier = modifier,
        entryProvider = entryProvider {
            entry<SignIn> {
                SignInScreen(
                    modifier = Modifier.fillMaxSize(),
                    onNavigateToHome = { navBackStack.add(HomeBottomBarNav.HomeScreen) },
                    onNavigateToSignUp = { navBackStack.add(SignUp) },
                )
            }

            entry<SignUp> {
                SignUpScreen(
                    modifier = Modifier.fillMaxSize(),
                    onNavigateToHome = { navBackStack.add(HomeBottomBarNav.HomeScreen) },
                    onNavigateToSignIn = { navBackStack.add(SignIn) }
                )
            }

            entry<HomeBottomBarNav.HomeScreen> {
                HomeScreen(navBackStack)
            }

            entry<HomeBottomBarNav.Bookmarks> {
                FavoriteScreen(navBackStack = navBackStack)
            }

            entry<HomeBottomBarNav.UserScreen> {
                UserScreen(navBackStack = navBackStack)
            }

            entry<CourseScreen> {
                CourseScreen(
                    course = it.course,
                    img = it.img,
                    navBackStack = navBackStack
                )
            }
        }
    )
}