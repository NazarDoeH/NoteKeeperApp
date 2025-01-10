package com.lab5.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import com.lab5.backend.AlertManager
import com.lab5.ui.theme.JetBrainsMono
import com.lab5.ui.theme.TextMain
import com.lab5.ui.theme.TextSecondary
import org.koin.compose.getKoin
import androidx.compose.runtime.*
import com.lab5.backend.toAlarmState
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.unit.sp
import com.lab5.ui.theme.DangerColor
import com.lab5.ui.theme.MainColor
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RegionInfoColumn(
    regionName: String,
    state: String,
    uid: Int, // Додаємо uid для перевірки статусу
    onClickIcon: () -> Unit = {},
    context: Context // Додаємо контекст для доступу до SharedPreferences
) {
    val alertManager: AlertManager = getKoin().get() // Використовуємо get() для отримання AlertManager
    val sharedPreferences = context.getSharedPreferences("RegionInfoPrefs", Context.MODE_PRIVATE)

    // Відновлюємо стан та час останнього оновлення з SharedPreferences
    var currentState by remember { mutableStateOf(getStoredState(sharedPreferences)) } // Стан тривоги
    var lastUpdateTime by remember { mutableStateOf(getLastUpdateTime(sharedPreferences)) } // Час останнього оновлення

    // Запускаємо таймер при ініціалізації компонента
    LaunchedEffect(uid) {
        alertManager.startTimer(uid) { newState ->
            currentState = newState // Оновлюємо стан при кожному новому статусі
            lastUpdateTime = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date()) // Оновлюємо час
            saveState(sharedPreferences, newState, lastUpdateTime) // Зберігаємо новий стан та час
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Колонка з назвою регіону і поточним станом
        Column(
        ) {
            Text(
                text = regionName,
                fontFamily = JetBrainsMono,
                color = TextMain
            )
            Text(
                text = currentState.toAlarmState(),
                fontFamily = JetBrainsMono,
                color = if(currentState == "A")
                {
                    DangerColor
                }
                else
                {
                    MainColor
                }
            )
            Text(
                text = "Оновлено: $lastUpdateTime", // Виведення часу останнього оновлення
                fontFamily = JetBrainsMono,
                color = TextSecondary,
                fontSize = 12.sp
            )
        }
    }
}

// Функція для збереження стану та часу в SharedPreferences
private fun saveState(sharedPreferences: SharedPreferences, state: String, lastUpdateTime: String) {
    val editor = sharedPreferences.edit()
    editor.putString("state", state)
    editor.putString("lastUpdateTime", lastUpdateTime)
    editor.apply()
}

// Функція для отримання збереженого стану з SharedPreferences
private fun getStoredState(sharedPreferences: SharedPreferences): String {
    return sharedPreferences.getString("state", "Inactive") ?: "Inactive" // Якщо немає збереженого стану, повертаємо "Inactive"
}

// Функція для отримання часу останнього оновлення з SharedPreferences
private fun getLastUpdateTime(sharedPreferences: SharedPreferences): String {
    return sharedPreferences.getString("lastUpdateTime", "N/A") ?: "N/A" // Якщо часу немає, повертаємо "N/A"
}