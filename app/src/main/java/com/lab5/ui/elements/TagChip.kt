package com.lab5.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lab5.data.database.entity.TagsEntity
import com.lab5.ui.theme.MainColor
import com.lab5.ui.theme.TextMain


@Composable
fun TagChip(tag: TagsEntity, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                if (isSelected) MainColor else Color.Transparent,
                shape = RoundedCornerShape(5.dp)
            )
            .border(2.dp, MainColor, RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() },
    ) {
        Text(
            text = "#${tag.name}",
            color = if (isSelected) Color.White else TextMain,
        )
    }
}


