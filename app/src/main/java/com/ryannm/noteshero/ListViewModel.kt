package com.ryannm.noteshero

import androidx.lifecycle.ViewModel
import com.ryannm.noteshero.domain.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListViewModel : ViewModel() {
    private val _notes = MutableStateFlow(listOf<Note>())
    val notes = _notes.asStateFlow()

    init {
        _notes.value = listOf(
            Note(content = "content")
        )

    }

}