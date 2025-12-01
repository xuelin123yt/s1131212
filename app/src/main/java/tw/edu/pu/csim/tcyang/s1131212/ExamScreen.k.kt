package tw.edu.pu.csim.tcyang.s1131212

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ExamScreen(
    modifier: Modifier = Modifier,
    viewModel: ExamViewModel = viewModel(
        factory = ExamViewModelFactory(
            LocalContext.current.applicationContext as android.app.Application
        )
    )
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 圓形圖片（不含文字）
            Image(
                painter = painterResource(id = R.drawable.happy),
                contentDescription = "Happy Image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 課程名稱
            Text(
                text = "瑪莉亞基金會服務大考驗",
                color = Color.Black,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 作者資訊
            Text(
                text = "作者：${viewModel.className} ${viewModel.studentName}",
                color = Color.Black,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 螢幕尺寸資訊（單位：px）
            Text(
                text = "螢幕大小：${viewModel.screenWidth} * ${viewModel.screenHeight}",
                color = Color.Black,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 成績
            Text(
                text = "成績：${viewModel.score}分",
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}