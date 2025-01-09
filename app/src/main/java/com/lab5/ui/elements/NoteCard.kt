package com.lab5.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab5.data.database.entity.NotesEntity
import com.lab5.ui.theme.BackgroundSecondary
import com.lab5.ui.theme.JetBrainsMono
import com.lab5.ui.theme.TextMain
import com.lab5.ui.theme.TextSecondary
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun NoteCard(note: NotesEntity, onNoteClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(BackgroundSecondary, shape = RoundedCornerShape(10.dp))
            .clickable { onNoteClick(note.id) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
             Text(
                     text = note.title,
                     maxLines = 1,
                     fontFamily = JetBrainsMono,
                     color = TextMain,
                     overflow = TextOverflow.Ellipsis
                )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.text,
                maxLines = 3,
                fontFamily = JetBrainsMono,
                color = TextSecondary,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    .withZone(ZoneId.systemDefault())
                    .format(note.editTime.toInstant()),
                fontFamily = JetBrainsMono,
                color = TextSecondary,
                fontSize = 10.sp
            )
        }
    }
}