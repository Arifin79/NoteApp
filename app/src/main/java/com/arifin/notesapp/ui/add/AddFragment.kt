package com.arifin.notesapp.ui.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arifin.notesapp.R
import com.arifin.notesapp.model.ToDoData
import com.arifin.notesapp.viewmodel.SharedViewModel
import com.arifin.notesapp.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by viewModels()
    private val toDoViewModel: TodoViewModel by  viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)
        view.spiner_priorities.onItemSelectedListener = sharedViewModel.listener
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add){
            insertDatatoDo()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDatatoDo() {
        val mTitle = edt_title.text.toString()
        val mPriority = spiner_priorities.selectedItem.toString()
        val mDescription = edt_description.text.toString()

        val validation = sharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation){
            val newData = ToDoData(
                    0,
                    mTitle,
                    sharedViewModel.parsePriority(mPriority),
                    mDescription
            )
            toDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Data Berhasil Dibuat", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }

}