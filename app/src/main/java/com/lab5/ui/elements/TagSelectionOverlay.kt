package com.lab5.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import com.lab5.backend.TagsManager
import com.lab5.data.database.entity.TagsEntity


@Composable
fun TagSelectionOverlay(
    tagsManager: TagsManager, // Клас для роботи з тегами
    appliedTags: List<TagsEntity>, // Список вже застосованих тегів
    onTagSelected: (TagsEntity) -> Unit, // Колбек для вибору тегу
    onDismiss: () -> Unit // Колбек для закриття оверлея
) {
    val allTags by tagsManager.getAllTagsFlow().collectAsState(initial = emptyList())
    val selectedTags = remember { mutableStateListOf(*appliedTags.toTypedArray()) }
    var newTagName by remember { mutableStateOf("") }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(onClick = onDismiss)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.Center)
        ) {
            Text(
                text = "Виберіть теги",
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn {
                items(allTags) { tag ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "tag.name",
                            Modifier.padding(start = 8.dp),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newTagName,
                    onValueChange = { newTagName = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Новий тег") }
                )
                Button(
                    onClick = {
                        if (newTagName.isNotBlank()) {
                            newTagName = ""
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Додати")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Закрити")
            }
        }
    }
}
