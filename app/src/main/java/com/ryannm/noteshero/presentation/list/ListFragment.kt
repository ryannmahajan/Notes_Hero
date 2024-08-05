package com.ryannm.noteshero.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ryannm.noteshero.R
import com.ryannm.noteshero.domain.model.Note
import com.ryannm.noteshero.presentation.detail.DetailFragment
import com.ryannm.noteshero.presentation.detail.DetailViewModel
import kotlinx.coroutines.launch


class ListFragment(
) : Fragment() {
    private val listViewModel: ListViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var saveButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerView)
        saveButton = view.findViewById(R.id.addNoteButton)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = NoteAdapter(listViewModel.notes.value) {
            navigateToDetailFragment(it)
        }

        recyclerView.adapter = adapter

        saveButton.setOnClickListener {
            navigateToDetailFragment()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            listViewModel.notes.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun navigateToDetailFragment(id: Int? = null) {
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()

        val detailVM: DetailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        if (id != null) {
            detailVM.setNoteID(id)
        }

        transaction.replace(R.id.fragment_container, DetailFragment::class.java, null)
        transaction.addToBackStack(null)

        transaction.commit()
    }
}

class NoteAdapter(private var notes: List<Note>, private val onClick:(Int)->Unit) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(private val view: View, private val onClick: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        private var noteTitleView: TextView = view.findViewById(R.id.noteTitle)
        private var noteContentView: TextView = view.findViewById(R.id.noteContentPreview)

        fun bind(note: Note) {
            noteTitleView.text = note.title
            noteContentView.text = note.content

            view.setOnClickListener {
                onClick(note.id)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_note, viewGroup, false)

        return ViewHolder(view, onClick)
    }

    fun submitList(newItems: List<Note>) {
        notes = newItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(notes[position])
    }

    override fun getItemCount() = notes.size
}
