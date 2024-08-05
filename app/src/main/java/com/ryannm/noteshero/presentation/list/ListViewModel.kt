package com.ryannm.noteshero.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryannm.noteshero.data.NoteDatabase
import com.ryannm.noteshero.domain.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {
    private val _notes = MutableStateFlow(listOf<Note>())
    val notes = _notes.asStateFlow()

    init {
        viewModelScope.launch {
            _notes.value = NoteDatabase.getDao().getAll()
        }
    }

}