package com.example.course.feature.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.layout.LazyLayoutCacheWindow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.course.core.designsystem.component.Img
import com.example.course.core.designsystem.theme.DarkGray
import com.example.course.core.designsystem.theme.Glass
import com.example.course.core.designsystem.theme.Green
import com.example.course.core.model.Course
import com.example.course.core.navigation.CourseScreen
import com.example.course.designsystem.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(navBackStack: NavBackStack<NavKey>) {
    val viewModel: FavoriteViewModel = hiltViewModel()
    val favoriteCourses by viewModel.favoriteCourses.collectAsState()
    val dpCacheWindow = LazyLayoutCacheWindow(behind = 150.dp, ahead = 100.dp)
    val state = rememberLazyListState(dpCacheWindow)

    Scaffold(
        containerColor = Color.Black
    ) {
        LazyColumn(
            state = state,
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Text(
                    text = stringResource(R.string.favorite),
                    modifier = Modifier.padding(16.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            items(
                items = favoriteCourses,
                key = { course -> course.id }
            ) { course ->
                val img = Img.mock[course.id % Img.mock.size]
                FavoriteItem(
                    course = course,
                    imageRes = img.image,
                    isFavorite = course.hasLike,
                    favIconColor = Color.Black,
                    onClick = {
                        navBackStack.add(
                            CourseScreen(
                                course = course,
                                img = img.image,
                            )
                        )
                    },
                    onFavoriteClick = { viewModel.setFavorite(course.id) }
                )
            }
        }
    }
}

@Composable
fun FavoriteItem(
    course: Course,
    imageRes: Int,
    favIconColor: Color = Color.White,
    isFavorite: Boolean,
    onClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
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
                            tint = Green
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
            Text(
                text = course.text,
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp
            )
            Row {
                Text("${course.price} р", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.more_detailed),
                    fontWeight = FontWeight.Bold,
                    color = Green
                )
            }
        }
    }
}
