package com.ryannm.noteshero.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.ryannm.noteshero.R
import com.ryannm.noteshero.presentation.list.ListFragment


class DetailFragment : Fragment() {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var title:TextView
    private lateinit var content:TextView
    private lateinit var saveButton:Button
    private lateinit var deleteButton:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        detailViewModel=
            ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        detailViewModel.fetchNote()

        title = v.findViewById(R.id.noteTitle)
        content = v.findViewById(R.id.noteContent)
        saveButton = v.findViewById(R.id.saveButton)
        deleteButton = v.findViewById(R.id.deleteButton)

        title.text = detailViewModel.note.value.title
        content.text = detailViewModel.note.value.content

        title.addTextChangedListener {
            it?.let { detailViewModel.onEditTitle(it.toString())  }
        }
        content.addTextChangedListener {
            it?.let { detailViewModel.onEditContent(it.toString())  }
        }
        saveButton.setOnClickListener {
            detailViewModel.save {
                navigateToListFragment()
            }
        }
        deleteButton.setOnClickListener {
            detailViewModel.delete {
                navigateToListFragment()
            }
        }
    }

    private fun navigateToListFragment() {
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, ListFragment::class.java, null)
        transaction.addToBackStack(null) // Optional: adds the transaction to the back stack

        transaction.commit()
    }
}
