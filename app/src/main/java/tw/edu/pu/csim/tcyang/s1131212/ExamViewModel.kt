package tw.edu.pu.csim.tcyang.s1131212

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ExamViewModel(application: Application) : AndroidViewModel(application) {

    // 螢幕寬度與高度（單位：px）
    var screenWidth by mutableIntStateOf(0)
        private set

    var screenHeight by mutableIntStateOf(0)
        private set

    // 學生資訊
    var studentName by mutableStateOf("王奕翔")
        private set

    var studentId by mutableStateOf("1131456")
        private set

    var className by mutableStateOf("資管二A")
        private set

    // 成績
    var score by mutableIntStateOf(0)
        private set

    init {
        // 取得螢幕尺寸（單位：px）
        val displayMetrics = application.resources.displayMetrics
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels
    }

    // 更新學生資訊
    fun updateStudentInfo(name: String, id: String, cls: String) {
        studentName = name
        studentId = id
        className = cls
    }

    // 更新成績
    fun updateScore(newScore: Int) {
        score = newScore
    }
}

// ViewModel Factory
class ExamViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExamViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExamViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}