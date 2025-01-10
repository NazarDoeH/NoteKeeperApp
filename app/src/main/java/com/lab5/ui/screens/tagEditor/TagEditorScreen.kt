package com.lab5.ui.screens.tagEditor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.lab5.ui.elements.AddTagDialog
import com.lab5.ui.theme.BackgroundMain
import com.lab5.ui.theme.BackgroundSecondary
import com.lab5.ui.theme.JetBrainsMono
import com.lab5.ui.theme.MainColor
import com.lab5.ui.theme.TextMain
import com.lab5.ui.theme.TextSecondary
import java.sql.Date

@Composable
fun TagEditorScreen(
    viewModel: TagEditorViewModel,
    back: () -> Unit
) {
    val tags by viewModel.tags.collectAsState(initial = emptyList())
    var tagName by remember { mutableStateOf("") }
    var isDialogOpen by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundMain)
    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable(onClick = {
                       back()
                    })
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Назад",
                        tint = TextMain
                    )
                }
            }
        // Список тегів
        LazyColumn {
            items(tags) { tag ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(BackgroundSecondary, shape = RoundedCornerShape(10.dp))
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = tag.name,
                        maxLines = 1,
                        fontFamily = JetBrainsMono,
                        color = TextMain,
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 16.dp)
                        )
                    IconButton(
                        onClick = { viewModel.deleteTag(tag)
                            }
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Видалити",
                            tint = TextMain)
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .background(BackgroundSecondary, shape = RoundedCornerShape(10.dp))
                .padding(8.dp)
                .clickable( onClick = { isDialogOpen = true })) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(color = MainColor, shape = RoundedCornerShape(10.dp))
            )
            {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = TextMain,
                    contentDescription = "Add Tag",
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
        AddTagDialog(
            isDialogOpen = isDialogOpen,
            onDismissRequest = { isDialogOpen = false },
            onAddTag = { newTag ->
                viewModel.addTag(newTag) // Виклик функції ViewModel
            }
        )
    }
}
