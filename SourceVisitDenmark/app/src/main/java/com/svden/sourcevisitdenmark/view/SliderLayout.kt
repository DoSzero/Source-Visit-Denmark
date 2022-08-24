package com.svden.sourcevisitdenmark.view

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.*
import com.svden.sourcevisitdenmark.model.infoList
import com.svden.sourcevisitdenmark.ui.theme.CorporateProRegular
import com.svden.sourcevisitdenmark.ui.theme.Orange200
import com.svden.sourcevisitdenmark.ui.theme.Sky200
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@ExperimentalPagerApi
@Preview
@Composable
fun ViewPagerSlider() {

    val pagerState  = rememberPagerState(
        pageCount = infoList.size,
        initialPage = 0
    )

    LaunchedEffect(Unit){
        while (true) {
            yield()
            delay(2500)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                animationSpec = tween(700)
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize()
        .background(color = Sky200)
        //.padding(8.dp, 8.dp, 0.dp, 8.dp)
    ) {
        Column(modifier = Modifier
            .height(70.dp)
            .padding(8.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,) {

            Text(
                text = "Source Visit Denmark",
                fontFamily = FontFamily.Cursive,
                color = Color.White,
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        HorizontalPager(state = pagerState, modifier = Modifier
                .background(color = Sky200 )
                .weight(1f)
                .padding(0.dp, 20.dp, 0.dp, 40.dp)) {

                page -> Card(modifier = Modifier.graphicsLayer {
                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )

                }
                .fillMaxWidth()
                .padding(25.dp, 0.dp, 25.dp, 0.dp),
                shape = RoundedCornerShape(40.dp)) {

                val newInfo = infoList[page]
                 Box(modifier = Modifier
                     .fillMaxSize()
                     .align(Alignment.Center)) {

                     Image(painter = painterResource(id = newInfo.imgUri),
                         contentDescription = "Image",
                         contentScale = ContentScale.Crop,
                         modifier = Modifier.fillMaxSize()
                     )

                     Column(modifier = Modifier
                         .background(Color.White, shape = RoundedCornerShape(13))
                         .align(Alignment.BottomStart)
                         .wrapContentSize(Alignment.Center)
                         // Large Content
                         //.padding(15.dp, 0.dp, 15.dp, 0.dp),
                         .padding(25.dp,0.dp,20.dp,5.dp)) {

                         Text(
                             text = newInfo.title,
                             fontFamily = CorporateProRegular,
                             textAlign = TextAlign.Center,
                             style = MaterialTheme.typography.h6,
                             color = Color(0xFF000000),
                             fontWeight = FontWeight.Bold,
                             modifier = Modifier.padding(15.dp,10.dp,0.dp,0.dp)
                         )

                         Text(
                             //textAlign = TextAlign.Center,
                             textAlign = TextAlign.Justify,
                             text = newInfo.desc,
                             style = MaterialTheme.typography.body1,
                             color = Color(0xFF000000),
                             fontFamily = CorporateProRegular,
                             fontSize = 17.sp,
                             // 8dp TOP
                             modifier = Modifier.padding(15.dp,8.dp,0.dp,15.dp)
                         )
                     }
                 }
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .background(color = Sky200 )
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
        )

    }

}