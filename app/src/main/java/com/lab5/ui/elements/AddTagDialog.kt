package com.lab5.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab5.ui.theme.JetBrainsMono
import com.lab5.ui.theme.MainColor
import com.lab5.ui.theme.TextMain

@Composable
fun AddTagDialog(
    isDialogOpen: Boolean,
    onDismissRequest: () -> Unit,
    onAddTag: (String) -> Unit
) {
    if (isDialogOpen) {
        var tagName by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(
                text = "Новий тег",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = JetBrainsMono,
                color = TextMain,
                fontSize = 16.sp
                ) },
            text = {
                Column {
                    // Поле для введення нового тегу
                    OutlinedTextField(
                        value = tagName,
                        onValueChange = { tagName = it },
                        label = { Text("Новий тег") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (tagName.isNotBlank()) {
                            onAddTag(tagName)
                            tagName = ""
                            onDismissRequest()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                ) {
                    Text(
                        text = "Додати",
                        fontFamily = JetBrainsMono,
                        color = TextMain,
                        fontSize = 16.sp)
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor)
                ) {
                    Text(text = "Скасувати",
                        fontFamily = JetBrainsMono,
                        color = TextMain,
                        fontSize = 16.sp)
                }
            }
        )
    }
}

