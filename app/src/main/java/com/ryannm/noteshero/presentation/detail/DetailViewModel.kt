package com.ryannm.noteshero.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryannm.noteshero.data.NoteDatabase
import com.ryannm.noteshero.domain.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "DetailVM"
class DetailViewModel(
) : ViewModel() {
    private val _note = MutableStateFlow(Note())
    val note = _note.asStateFlow()
    private var noteID:Int? = null

    private val noteDao = NoteDatabase.getDao()

    fun onEditTitle(it:String) {
        _note.value = _note.value.copy(title = it)
    }

    fun onEditContent(it:String) {
        _note.value = _note.value.copy(content = it)
    }

    fun save(onComplete:()->Unit) = viewModelScope.launch {
        if (noteID==null) noteDao.insert(note.value)
        else noteDao.update(note.value)

        onComplete()
    }

    fun delete(onComplete: () -> Unit) = viewModelScope.launch {
        noteDao.delete(note.value)
        onComplete()
    }

    fun setNoteID(id: Int?) {
        noteID = id
    }

    fun fetchNote() {
        Log.d(TAG, "Received noteID: $noteID")
        viewModelScope.launch {
            _note.value = noteID?.let {
                 noteDao.getNoteById(it)
            } ?: Note()
        }
    }
}
