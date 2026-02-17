package com.example.mycourse.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.course.core.navigation.CourseBottomBar
import com.example.course.core.navigation.SignIn
import com.example.course.core.navigation.SignUp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppScreen() {
    val navBackStack = rememberNavBackStack(SignIn)
    val isBottomBarShow = navBackStack.lastOrNull() is SignIn
    val isBottomBarShow1 = navBackStack.lastOrNull() is SignUp

    Scaffold(
        bottomBar = {
            if (!isBottomBarShow && !isBottomBarShow1)
                CourseBottomBar(
                    navBackStack = navBackStack,
                    modifier = Modifier,
                )
        }
    ) { innerPadding ->
        NavStack(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            navBackStack = navBackStack,
        )
    }
}