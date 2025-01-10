package com.lab5.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.lab5.data.database.entity.TagsEntity
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lab5.ui.theme.TextMain
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import com.lab5.ui.theme.MainColor

@Composable
fun TagsSection(
    tags: List<TagsEntity>,
    appliedTags: SnapshotStateList<TagsEntity>,
    onTagSelected: (TagsEntity) -> Unit
) {
    var showTagList by remember { mutableStateOf(false) }

    Column {
        Row()
        {
                Box(
                    modifier = Modifier
                        .background(color = MainColor, shape = RoundedCornerShape(10.dp))
                        .clickable(onClick = { showTagList = !showTagList })
                )
                {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        tint = TextMain,
                        contentDescription = "Select tags",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            Spacer(Modifier.padding(4.dp))
            LazyRow(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                items(appliedTags) { tag ->
                    TagChip(tag = tag, false) {
                        onTagSelected(tag)
                    }
                    Spacer(Modifier.padding(4.dp))
                }
            }
        }
        if (showTagList) {
            TagSelectionOverlay(
                tags = tags,
                appliedTags = appliedTags,
                onTagSelected = onTagSelected,
                onDismiss = { showTagList = false }
            )
        }
    }
}
