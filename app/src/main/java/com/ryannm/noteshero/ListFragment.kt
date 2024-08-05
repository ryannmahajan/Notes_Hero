package com.ryannm.noteshero

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ryannm.noteshero.domain.model.Note
import kotlinx.coroutines.launch

class ListFragment(
) : Fragment() {
    private val listViewModel: ListViewModel by viewModels()
    private var notes:List<Note> = listOf()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.note_list)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = NoteAdapter(notes)
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            listViewModel.notes.collect {
                notes = it
                adapter.submitList(it)
            }
        }
    }
}

class NoteAdapter(private var notes: List<Note>) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.text_view)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.note_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    fun submitList(newItems: List<Note>) {
        notes = newItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = notes[position].content
    }

    override fun getItemCount() = notes.size

}
