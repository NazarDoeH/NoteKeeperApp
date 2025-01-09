package com.lab5.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab5.data.database.entity.TagsEntity
import com.lab5.ui.theme.MainColor
import com.lab5.ui.theme.TextMain

@Composable
fun TagChipNoteEditor(
    text: String,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(MainColor)
            .padding(8.dp)
    ) {
        Text(
            text = text,
            color = TextMain,
            fontSize = 14.sp,
            modifier = Modifier.padding(end = 8.dp)
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Remove",
            modifier = Modifier
                .size(16.dp)
                .clickable { onRemove() },
            tint = TextMain
        )
    }
}