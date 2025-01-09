package com.lab5.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lab5.backend.TagsManager
import com.lab5.ui.screens.noteEditor.NoteEditorScreen
import com.lab5.ui.screens.notesList.NotesListScreen
import com.lab5.ui.screens.tagEditor.TagEditorScreen
import org.koin.androidx.compose.getViewModel
import org.koin.compose.getKoin

const val SCREEN_NOTES_LIST = "notesList"
const val SCREEN_EDIT_NOTE = "editNote"
const val SCREEN_ADD_NOTE = "addNote"
const val SCREEN_EDIT_TAGS = "editTag"

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SCREEN_NOTES_LIST
    ) {
        // Notes List Screen
        composable(
            route = SCREEN_NOTES_LIST
        ) {
            NotesListScreen(
                viewModel = getViewModel(),
                onNoteClick = { noteId ->
                    navController.navigate("$SCREEN_EDIT_NOTE/$noteId")
                },
                onAddClick = {
                    navController.navigate(SCREEN_ADD_NOTE) // Navigate to add new note screen
                },
                onEditTag = {
                    navController.navigate(SCREEN_EDIT_TAGS)
                }
            )
        }

        // Edit Note Screen
        composable(
            route = "$SCREEN_EDIT_NOTE/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
            val tagsManager: TagsManager = getKoin().get()
            NoteEditorScreen(
                noteId = noteId,
                viewModel = getViewModel(),
                tagsManager = tagsManager,
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() },
            )
        }

        // Add New Note Screen (NoteEditor)
        composable(
            route = SCREEN_ADD_NOTE // This route will handle adding new notes
        ) {
            val tagsManager: TagsManager = getKoin().get()
            NoteEditorScreen(
                noteId = 0, // Set noteId to 0 for new note
                viewModel = getViewModel(),
                tagsManager = tagsManager,
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() },
            )
        }

        composable(
            route = SCREEN_EDIT_TAGS
        ){
            TagEditorScreen(
                viewModel = getViewModel(),
                onSave = { navController.popBackStack() }
            )
        }
    }
}
