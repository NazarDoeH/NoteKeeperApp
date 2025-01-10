package com.lab5.ui.screens.notesList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lab5.backend.Region
import com.lab5.ui.elements.NoteCard
import com.lab5.ui.elements.RegionInfoColumn
import com.lab5.ui.elements.TagChip
import com.lab5.ui.theme.*
import org.koin.androidx.compose.getViewModel

@Composable
fun NotesListScreen(
    viewModel: NotesListViewModel = getViewModel(),
    onNoteClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onEditTag: () -> Unit
) {
    val regionName = Region.fromUid(viewModel.regionUidStateFlow.collectAsState().value as Int) as String
    val regionState = viewModel.regionState.collectAsState().value as String
    val notes = viewModel.notesStateFlow.collectAsState().value
    val tags = viewModel.tagsStateFlow.collectAsState().value
    val selectedTags = viewModel.selectedTagsStateFlow.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundMain)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        RegionInfoColumn(regionName, regionState, uid = 27, context = LocalContext.current)
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        {
            Box(
                modifier = Modifier
                    .background(BackgroundSecondary, shape = RoundedCornerShape(5.dp))
            )
            {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(35.dp)
                        .padding(5.dp)
                        .clickable { onEditTag() },
                    tint = TextMain
                )
            }
            Spacer(Modifier.width(8.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tags) { tag ->
                    TagChip(tag, isSelected = selectedTags.contains(tag.id)) {
                        viewModel.toggleTagSelection(tag.id)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Список нотаток які можна добавляти та редагувати лаб 2, 3, 4 (використання БД для збереження нотаток)
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
        ) {
            items(notes) { note ->
                NoteCard(note, onNoteClick)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        FloatingActionButton(
            onClick = {
                onAddClick();
            },
            containerColor = MainColor,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            contentColor = TextMain
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Note"
            )
        }
    }
}