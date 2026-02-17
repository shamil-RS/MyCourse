package com.example.course.feature.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.course.core.designsystem.component.Img
import com.example.course.core.designsystem.theme.AlmostBlack
import com.example.course.core.designsystem.theme.DarkGray
import com.example.course.core.designsystem.theme.Glass
import com.example.course.core.designsystem.theme.Green
import com.example.course.core.model.Course
import com.example.course.core.navigation.CourseScreen
import com.example.course.core.navigation.SignIn
import com.example.course.designsystem.R

@Composable
fun UserScreen(navBackStack: NavBackStack<NavKey>) {

    val viewModel: UserViewModel = hiltViewModel()
    val state by viewModel.userState.collectAsState()
    val userCourses by viewModel.userCourse.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BlockItem(
                list = state.userBlockList,
                onClick = { item ->
                    when (item.action) {
                        UserAction.OPEN_PROFILE -> {}
                        UserAction.OPEN_SETTINGS -> {}
                        UserAction.LOGOUT -> navBackStack.add(SignIn)
                    }
                }
            )

            Text(
                text = stringResource(R.string.your_course),
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.White
            )
        }

        items(
            items = userCourses.take(2),
            key = { course -> course.id }
        ) { course ->
            val img = Img.mock[course.id % Img.mock.size]

            MyCourse(
                course = course,
                imageRes = img.image,
                isFavorite = course.hasLike,
                onClick = {
                    navBackStack.add(
                        CourseScreen(
                            course = course,
                            img = img.image,
                        )
                    )
                },
                onFavoriteClick = { viewModel.setFavorite(course) }
            )
        }
    }
}

@Composable
fun BlockItem(
    list: List<UserBlockItem>,
    onClick: (UserBlockItem) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(stringResource(R.string.profile), color = Color.White)

        Spacer(modifier = Modifier.padding(16.dp))

        list.forEachIndexed { index, item ->

            val isFirst = index == 0
            val isLast = index == list.lastIndex

            val shape: Shape = RoundedCornerShape(
                topStart = if (isFirst) 12.dp else 0.dp,
                topEnd = if (isFirst) 12.dp else 0.dp,
                bottomStart = if (isLast) 12.dp else 0.dp,
                bottomEnd = if (isLast) 12.dp else 0.dp
            )

            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .clip(shape)
                    .background(AlmostBlack)
                    .clickable {
                        onClick(item)
                    }
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.text, color = Color.White)
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            modifier = Modifier.size(12.dp),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                    if (!isLast) HorizontalDivider(color = DarkGray)
                }
            }
        }
    }
}

@Composable
fun MyCourse(
    course: Course,
    imageRes: Int,
    favIconColor: Color = Color.White,
    isFavorite: Boolean,
    onClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(bottom = 6.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val icon = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder
        val iconColor = if (isFavorite) Green else favIconColor

        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black)
                .clickable { onClick() },
            colors = CardDefaults.cardColors(containerColor = DarkGray)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxSize()
                        .heightIn(max = 150.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                FilledIconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Glass),
                    onClick = { onFavoriteClick() }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        tint = iconColor
                    )
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
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "",
                                tint = Color(0xFF12B956)
                            )
                            Text("${course.rate}", color = Color.White)
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
                                text = course.publishDate,
                                modifier = Modifier.padding(8.dp),
                                color = Color.White
                            )
                        }
                    )
                }
            }

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = course.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )

                ProgressCourse(
                    progressPercent = stringResource(R.string.progress_percent),
                    progress = 0.5f,
                    completedLesson = stringResource(R.string.completed_lesson),
                    totalLesson = stringResource(R.string.total_lesson)
                )
            }
        }
    }
}

@Composable
fun ProgressCourse(
    progressPercent: String,
    progress: Float,
    completedLesson: String,
    totalLesson: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(progressPercent, color = Green)
        Spacer(modifier = Modifier.weight(1f))
        Text(completedLesson, color = Green)
        Text(totalLesson, color = Color.LightGray)
    }

    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier.fillMaxWidth(),
        color = Green,
        trackColor = Color.LightGray
    )
}