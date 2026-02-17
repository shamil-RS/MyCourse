package com.example.course.feature.home

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
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.course.core.designsystem.component.Img
import com.example.course.core.designsystem.theme.DarkGray
import com.example.course.core.designsystem.theme.Glass
import com.example.course.core.designsystem.theme.Green
import com.example.course.core.designsystem.theme.Gunmetal
import com.example.course.core.model.Course
import com.example.course.core.navigation.CourseScreen
import com.example.course.designsystem.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navBackStack: NavBackStack<NavKey>) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val courseList by viewModel.courseList.collectAsStateWithLifecycle()

    val dpCacheWindow = LazyLayoutCacheWindow(behind = 150.dp, ahead = 100.dp)
    val state = rememberLazyListState(dpCacheWindow)

    when (uiState) {
        is HomeUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { CircularProgressIndicator() }
            )
        }

        is HomeUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = {
                    Text(text = stringResource(R.string.error) + " ${(uiState as HomeUiState.Error).message}")
                }
            )
        }

        is HomeUiState.Idle -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                TopBar()
                LazyColumn(
                    state = state,
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 16.dp)
                                .clickable { viewModel.setSortedDate() },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = stringResource(R.string.filter_date),
                                fontWeight = FontWeight.Bold,
                                color = Green
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Icon(
                                painter = painterResource(R.drawable.vector1),
                                contentDescription = "",
                                tint = Green
                            )
                        }
                    }

                    items(
                        items = courseList,
                        key = { course -> course.id }
                    ) { course ->
                        val img = Img.mock[course.id % Img.mock.size]
                        HomeItem(
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

        }
    }
}

@Composable
fun HomeItem(
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
            .padding(horizontal = 16.dp, vertical = 6.dp)
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

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .padding(vertical = 40.dp, horizontal = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedCard(
            modifier = Modifier
                .height(60.dp)
                .weight(1f),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.outlinedCardColors(containerColor = DarkGray)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.height(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
                Text(
                    text = stringResource(R.string.search),
                    color = Color.Gray
                )
            }
        }

        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Gunmetal),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.FilterAlt,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}
