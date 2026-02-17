package com.example.course.core.designsystem.component

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CustomTextButton(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) = TextButton(
    modifier = modifier,
    onClick = onClick,
    enabled = enabled
) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        color = color
    )
}