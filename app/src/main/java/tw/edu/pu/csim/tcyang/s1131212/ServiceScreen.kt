package tw.edu.pu.csim.tcyang.s1131212

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun ServiceScreen(
    modifier: Modifier = Modifier,
    viewModel: ExamViewModel = viewModel(
        factory = ExamViewModelFactory(
            LocalContext.current.applicationContext as android.app.Application
        )
    )
) {
    val density = LocalDensity.current

    // 螢幕寬度和高度（px）
    val screenWidthPx = viewModel.screenWidth
    val screenHeightPx = viewModel.screenHeight

    // 服務圖示列表
    val serviceImages = listOf(
        R.drawable.service0,
        R.drawable.service1,
        R.drawable.service2,
        R.drawable.service3
    )

    // 當前顯示的服務圖示索引
    var currentServiceIndex by remember { mutableIntStateOf(Random.nextInt(serviceImages.size)) }

    // 服務圖示大小（px）
    val serviceIconSizePx = 200
    val serviceIconSizeDp = with(density) { serviceIconSizePx.toDp() }

    // 圖示的 X 和 Y 座標（px）
    var serviceX by remember {
        mutableFloatStateOf((screenWidthPx / 2 - serviceIconSizePx / 2).toFloat())
    }
    var serviceY by remember { mutableFloatStateOf(100f) }

    // 每0.1秒往下掉20px
    LaunchedEffect(currentServiceIndex) {
        while (true) {
            delay(100) // 0.1秒
            serviceY += 20f // 往下掉20px

            // 如果碰到螢幕下方，重新開始
            if (serviceY >= screenHeightPx - serviceIconSizePx) {
                serviceY = 100f
                serviceX = (screenWidthPx / 2 - serviceIconSizePx / 2).toFloat()
                currentServiceIndex = Random.nextInt(serviceImages.size)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // 顯示 RoleScreen（四個角色 + 中間內容）
        RoleScreen()

        // 掉落的服務圖示（可左右拖曳）
        Image(
            painter = painterResource(id = serviceImages[currentServiceIndex]),
            contentDescription = "服務圖示",
            modifier = Modifier
                .size(serviceIconSizeDp)
                .offset {
                    IntOffset(
                        x = serviceX.toInt(),
                        y = serviceY.toInt()
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        // 只允許左右拖曳
                        serviceX = (serviceX + dragAmount.x).coerceIn(
                            0f,
                            (screenWidthPx - serviceIconSizePx).toFloat()
                        )
                    }
                },
            contentScale = ContentScale.Fit
        )
    }
}