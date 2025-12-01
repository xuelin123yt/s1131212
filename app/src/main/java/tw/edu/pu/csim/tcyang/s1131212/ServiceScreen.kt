package tw.edu.pu.csim.tcyang.s1131212

import android.widget.Toast
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

// 服務對應的正確角色
val serviceCorrectRole = mapOf(
    0 to "嬰幼兒", // service0 對應 嬰幼兒
    1 to "兒童",   // service1 對應 兒童
    2 to "成人",   // service2 對應 成人
    3 to "一般民眾" // service3 對應 一般民眾
)

// 碰撞檢測函數
fun checkCollision(
    serviceX: Float,
    serviceY: Float,
    serviceSize: Int,
    screenWidth: Int,
    screenHeight: Int,
    viewModel: ExamViewModel,
    currentServiceIndex: Int,
    context: android.content.Context
): Boolean {
    val roleSize = 300 // 角色圖示大小
    val serviceCenterX = serviceX + serviceSize / 2
    val serviceCenterY = serviceY + serviceSize / 2

    // 檢查是否碰到下方邊界
    if (serviceY + serviceSize >= screenHeight - 50) {
        viewModel.updateMsg("掉到最下方")
        viewModel.updateScore(viewModel.score - 1) // 扣1分
        return true
    }

    // 左上角 - 嬰幼兒 (role0)
    val role0X = 0f
    val role0Y = screenHeight / 2f - roleSize
    if (isColliding(serviceCenterX, serviceCenterY, role0X, role0Y, roleSize.toFloat())) {
        val correctRole = serviceCorrectRole[currentServiceIndex] ?: ""
        if (correctRole == "嬰幼兒") {
            viewModel.updateMsg("碰撞嬰幼兒")
            viewModel.updateScore(viewModel.score + 1) // 答對加1分
        } else {
            viewModel.updateMsg("碰撞嬰幼兒")
            viewModel.updateScore(viewModel.score - 1) // 答錯扣1分
        }
        // 顯示Toast
        Toast.makeText(context, "極早期療育，屬於嬰幼兒方面的服務", Toast.LENGTH_SHORT).show()
        return true
    }

    // 右上角 - 成人 (role1)
    val role1X = screenWidth - roleSize.toFloat()
    val role1Y = screenHeight / 2f - roleSize
    if (isColliding(serviceCenterX, serviceCenterY, role1X, role1Y, roleSize.toFloat())) {
        val correctRole = serviceCorrectRole[currentServiceIndex] ?: ""
        if (correctRole == "成人") {
            viewModel.updateMsg("碰撞成人")
            viewModel.updateScore(viewModel.score + 1)
        } else {
            viewModel.updateMsg("碰撞成人")
            viewModel.updateScore(viewModel.score - 1)
        }
        Toast.makeText(context, "庇護工場，屬於成人方面的服務", Toast.LENGTH_SHORT).show()
        return true
    }

    // 左下角 - 兒童 (role2)
    val role2X = 0f
    val role2Y = screenHeight - roleSize.toFloat()
    if (isColliding(serviceCenterX, serviceCenterY, role2X, role2Y, roleSize.toFloat())) {
        val correctRole = serviceCorrectRole[currentServiceIndex] ?: ""
        if (correctRole == "兒童") {
            viewModel.updateMsg("碰撞兒童")
            viewModel.updateScore(viewModel.score + 1)
        } else {
            viewModel.updateMsg("碰撞兒童")
            viewModel.updateScore(viewModel.score - 1)
        }
        Toast.makeText(context, "發展學園，屬於兒童方面的服務", Toast.LENGTH_SHORT).show()
        return true
    }

    // 右下角 - 一般民眾 (role3)
    val role3X = screenWidth - roleSize.toFloat()
    val role3Y = screenHeight - roleSize.toFloat()
    if (isColliding(serviceCenterX, serviceCenterY, role3X, role3Y, roleSize.toFloat())) {
        val correctRole = serviceCorrectRole[currentServiceIndex] ?: ""
        if (correctRole == "一般民眾") {
            viewModel.updateMsg("碰撞一般民眾")
            viewModel.updateScore(viewModel.score + 1)
        } else {
            viewModel.updateMsg("碰撞一般民眾")
            viewModel.updateScore(viewModel.score - 1)
        }
        Toast.makeText(context, "輔具服務，屬於一般民眾方面的服務", Toast.LENGTH_SHORT).show()
        return true
    }

    return false
}

// 判斷是否碰撞
fun isColliding(
    serviceCenterX: Float,
    serviceCenterY: Float,
    roleX: Float,
    roleY: Float,
    roleSize: Float
): Boolean {
    val roleCenterX = roleX + roleSize / 2
    val roleCenterY = roleY + roleSize / 2
    val distance = kotlin.math.sqrt(
        (serviceCenterX - roleCenterX) * (serviceCenterX - roleCenterX) +
                (serviceCenterY - roleCenterY) * (serviceCenterY - roleCenterY)
    )
    return distance < (roleSize / 2 + 100) // 100 是服務圖示半徑
}

@Composable
fun ServiceScreen(
    modifier: Modifier = Modifier,
    viewModel: ExamViewModel = viewModel(
        factory = ExamViewModelFactory(
            LocalContext.current.applicationContext as android.app.Application
        )
    )
) {
    val context = LocalContext.current
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

    // 碰撞標記
    var hasCollided by remember { mutableIntStateOf(0) }

    // 每0.1秒往下掉20px
    LaunchedEffect(hasCollided) {
        while (true) {
            delay(100) // 0.1秒
            serviceY += 20f // 往下掉20px

            // 檢查碰撞
            val collided = checkCollision(
                serviceX, serviceY, serviceIconSizePx,
                screenWidthPx, screenHeightPx,
                viewModel, currentServiceIndex, context
            )

            // 如果發生碰撞或碰到螢幕下方，等待3秒後重新開始
            if (collided || serviceY >= screenHeightPx - serviceIconSizePx) {
                delay(3000) // 暫停3秒
                serviceY = 100f
                serviceX = (screenWidthPx / 2 - serviceIconSizePx / 2).toFloat()
                currentServiceIndex = Random.nextInt(serviceImages.size)
                viewModel.updateMsg("") // 清空訊息
                hasCollided++ // 改變狀態以重新啟動 LaunchedEffect
                break
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