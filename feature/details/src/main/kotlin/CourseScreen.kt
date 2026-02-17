package com.example.course.feature.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.course.core.designsystem.theme.Glass
import com.example.course.core.designsystem.theme.Green
import com.example.course.core.designsystem.theme.Gunmetal
import com.example.course.core.model.Course
import com.example.course.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseScreen(
    course: Course,
    img: Int,
    navBackStack: NavBackStack<NavKey>
) {
    val viewModel: CourseViewModel = hiltViewModel()
    val dbCourseState by viewModel.courseState.collectAsStateWithLifecycle()
    val currentCourse = dbCourseState ?: course
    val isFavorite = currentCourse.hasLike
    val icon = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder
    val iconColor = if (isFavorite) Green else Color.Black
    val verticalScroll = rememberScrollState()

    LaunchedEffect(course.id) {
        viewModel.setCourse(course.id)
    }

    Column(
        modifier = Modifier
            .background(Color.Black)
            .verticalScroll(verticalScroll),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(id = img),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    onClick = { navBackStack.removeLastOrNull() },
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.White),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.Black,
                        contentDescription = ""
                    )
                }

                FilledIconButton(
                    onClick = { viewModel.setFavorite() },
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.White),
                ) {
                    Icon(
                        imageVector = icon,
                        tint = iconColor,
                        contentDescription = ""
                    )
                }
            }

            Row(
                modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(40.dp)
                        .clip(CircleShape)
                        .background(Glass),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "",
                            tint = Green
                        )
                        Text("${currentCourse.rate}", color = Color.White)
                    }
                }

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .height(40.dp)
                        .clip(CircleShape)
                        .background(Glass),
                    contentAlignment = Alignment.Center,
                    content = {
                        Text(
                            text = currentCourse.publishDate,
                            color = Color.White,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                )
            }
        }

        AboutCourse(currentCourse.title, currentCourse.text)
    }
}

@Composable
fun AboutCourse(title: String, text: String) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(text = title, fontSize = 24.sp, color = Color.White)

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.image),
                modifier = Modifier.size(40.dp),
                contentDescription = ""
            )
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.author), color = Color.Gray)
                Text(
                    text = stringResource(R.string.academy),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .size(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green),
            onClick = {},
            content = {
                Text(
                    text = stringResource(R.string.start_course),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .size(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Gunmetal),
            onClick = {}
        ) {
            Text(
                text = stringResource(R.string.nav_to_platform),
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Text(text = stringResource(R.string.about_course), color = Color.White)
        Text(text = text, color = Color.Gray)
    }
}