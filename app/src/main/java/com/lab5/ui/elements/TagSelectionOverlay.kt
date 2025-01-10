package com.lab5.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import com.lab5.data.database.entity.TagsEntity
import com.lab5.ui.theme.BackgroundSecondary
import com.lab5.ui.theme.JetBrainsMono
import com.lab5.ui.theme.TextMain


@Composable
fun TagSelectionOverlay(
    tags: List<TagsEntity>,
    appliedTags: List<TagsEntity>,
    onTagSelected: (TagsEntity) -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .clickable(onClick = onDismiss)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = "Select Tags",
                fontFamily = JetBrainsMono,
                color = TextMain,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(tags) { tag ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = BackgroundSecondary, shape = RoundedCornerShape(10.dp))
                    )
                    {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                                .clickable { onTagSelected(tag) }
                        ) {
                            Checkbox(
                                checked = appliedTags.contains(tag),
                                onCheckedChange = { onTagSelected(tag) }
                            )
                            Text(
                                text = tag.name,
                                fontFamily = JetBrainsMono,
                                maxLines = 1,
                                color = TextMain,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    }
                    Spacer(Modifier.padding(4.dp))
                }
            }
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp)
            ) {
                Text(text = "Close")
            }
        }
    }
}
