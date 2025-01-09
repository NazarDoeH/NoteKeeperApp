package com.lab5.ui.screens.tagEditor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp

@Composable
fun TagEditorScreen(
    viewModel: TagEditorViewModel,
    onSave: () -> Unit
) {
    val tags by viewModel.tags.collectAsState(initial = emptyList())
    var tagName by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Редактор тегів")

        // Поле для вводу нового тегу
        OutlinedTextField(
            value = tagName,
            onValueChange = { tagName = it },
            label = { Text("Новий тег") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Кнопка для додавання нового тегу
        Button(
            onClick = {
                //viewModel.tagsManager.addNewTag(tagName)
                tagName = ""
            },

        ) {
            Text("Додати")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Список тегів
        LazyColumn {
            items(tags) { tag ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = tag.name)
                    IconButton(
                        onClick = { //viewModel.tagsManager.deleteTag(tag)
                            }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Видалити")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка "Зберегти"
        Button(
            onClick = { onSave() },
        ) {
            Text("Зберегти")
        }
    }
}
