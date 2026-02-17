package com.example.course.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.course.core.designsystem.theme.DarkGray
import com.example.course.core.designsystem.theme.Green

@Composable
fun CourseBottomBar(
    navBackStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier,
) {
    val currentScreen = navBackStack.lastOrNull()

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Gray),
        containerColor = DarkGray,
        tonalElevation = 2.dp,
    ) {
        bottomNavigationBarItems.forEach { screen ->
            val isSelected = currentScreen == screen
            val colorTextAndIcon = if (isSelected) Green else Color.White

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(screen.icon),
                        tint = colorTextAndIcon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(screen.text),
                        color = colorTextAndIcon
                    )
                },
                selected = isSelected,
                onClick = { if (!isSelected) navBackStack.add(screen) }
            )
        }
    }
}