package tw.edu.pu.csim.tcyang.s1131212

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RoleScreen(
    modifier: Modifier = Modifier,
    viewModel: ExamViewModel = viewModel(
        factory = ExamViewModelFactory(
            LocalContext.current.applicationContext as android.app.Application
        )
    )
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    // 螢幕寬度和高度（dp）
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp

    // 將 300px 轉換為 dp
    val iconSizePx = 300
    val iconSizeDp = with(density) { iconSizePx.toDp() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        // 中間圓形圖片和文字資訊
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ExamScreen()
        }

        // 左上角 - 嬰幼兒圖示（role0.png）
        Image(
            painter = painterResource(id = R.drawable.role0),
            contentDescription = "嬰幼兒",
            modifier = Modifier
                .size(iconSizeDp)
                .align(Alignment.TopStart)
                .offset(y = screenHeightDp / 2 - iconSizeDp),
            contentScale = ContentScale.Fit
        )

        // 左下角 - 兒童圖示（role2.png）
        Image(
            painter = painterResource(id = R.drawable.role2),
            contentDescription = "兒童",
            modifier = Modifier
                .size(iconSizeDp)
                .align(Alignment.BottomStart),
            contentScale = ContentScale.Fit
        )

        // 右上角 - 成人圖示（role1.png）
        Image(
            painter = painterResource(id = R.drawable.role1),
            contentDescription = "成人",
            modifier = Modifier
                .size(iconSizeDp)
                .align(Alignment.TopEnd)
                .offset(y = screenHeightDp / 2 - iconSizeDp),
            contentScale = ContentScale.Fit
        )

        // 右下角 - 一般民眾圖示（role3.png）
        Image(
            painter = painterResource(id = R.drawable.role3),
            contentDescription = "一般民眾",
            modifier = Modifier
                .size(iconSizeDp)
                .align(Alignment.BottomEnd),
            contentScale = ContentScale.Fit
        )
    }
}