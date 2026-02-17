package com.example.course.core.designsystem.util

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.course.designsystem.R

@Composable
fun safeStringResource(@StringRes id: Int?): String? =
    if (id == null) null
    else stringResource(id = id)

@Composable
fun ClearTrailingButton(
    onClick: () -> Unit
) = IconButton(onClick = onClick) {
    Icon(
        imageVector = LaIcons.Clear,
        contentDescription = stringResource(id = R.string.clear)
    )
}

@Composable
fun ToggleTextVisibilityTrailingButton(
    onClick: () -> Unit,
    isVisible: Boolean
) = IconButton(onClick = onClick) {
    Icon(
        imageVector = if (isVisible) LaIcons.Visibility else LaIcons.VisibilityOff,
        contentDescription = stringResource(
            id = if (isVisible) R.string.show else R.string.hide
        )
    )
}

@Composable
fun AccountBox(
    modifier: Modifier = Modifier,
    icon: Int,
    color: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(156.dp, 40.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
        content = {
            Icon(
                painter = painterResource(icon),
                contentDescription = "",
                tint = Color.White
            )
        }
    )
}

fun PaddingValues.copy(
    layoutDirection: LayoutDirection,
    start: Dp? = null,
    top: Dp? = null,
    end: Dp? = null,
    bottom: Dp? = null,
) = PaddingValues(
    start = start ?: calculateStartPadding(layoutDirection),
    top = top ?: calculateTopPadding(),
    end = end ?: calculateEndPadding(layoutDirection),
    bottom = bottom ?: calculateBottomPadding(),
)