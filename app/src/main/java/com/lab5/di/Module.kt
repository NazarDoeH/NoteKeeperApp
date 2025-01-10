package com.lab5.di

import android.content.Context
import androidx.room.Room
import com.lab5.backend.AlertManager
import com.lab5.backend.TagsManager
import com.lab5.data.database.db.NotekeeperDatabase
import com.lab5.data.sever_api.ServerAPI
import com.lab5.ui.screens.noteEditor.NoteEditorViewModel
import com.lab5.ui.screens.notesList.NotesListViewModel
import com.lab5.ui.screens.tagEditor.TagEditorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val BASE_URL = "https://api.alerts.in.ua/"

val appModule = module {
    // Retrofit instance
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ServerAPI instance
    single<ServerAPI> {
        get<Retrofit>().create()
    }

    // AlertManager instance
    single { AlertManager(get()) }

    // Database Module
    single<NotekeeperDatabase> {
        Room.databaseBuilder(
            get<Context>(),
            NotekeeperDatabase::class.java, "NotekeeperDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    // TagsManager instance
    single { TagsManager(get()) }

    // ViewModel Module
    viewModel { NotesListViewModel(get(), get()) }
    viewModel { NoteEditorViewModel(get(), get()) }
    viewModel { TagEditorViewModel(get(), get()) }
}