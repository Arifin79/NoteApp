package com.arifin.notesapp.ui.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arifin.notesapp.R
import com.arifin.notesapp.databinding.FragmentUpdateBinding
import com.arifin.notesapp.model.ToDoData
import com.arifin.notesapp.viewmodel.SharedViewModel
import com.arifin.notesapp.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.fragment_update.*

class UpdateFragment : Fragment() {

    private val args: UpdateFragmentArgs by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: TodoViewModel by viewModels()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args
        binding.spinerPrioritiesUpdate.onItemSelectedListener = mSharedViewModel.listener
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        AlertDialog.Builder(requireContext())
                .setTitle("Delete '${args.currentItem.title}'?")
                .setMessage("Apakah anda yakin Menghapus Data ini '${args.currentItem.title}'")
                .setPositiveButton("yes"){_,_ ->
                    mToDoViewModel.deleteData(args.currentItem)
                    Toast.makeText(requireContext(),"Berhasil DiHapus", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_updateFragment_to_listFragment)
                }
                .setNegativeButton("No"){_,_ ->}
                .create()
                .show()

    }

    private fun updateItem() {
        val mTitle = edt_title_update.text.toString()
        val mPriority = spiner_priorities_update.selectedItem.toString()
        val mDescription = edt_description_update.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation){
            val newData = ToDoData(
                    args.currentItem.id,
                    mTitle,
                    mSharedViewModel.parsePriority(mPriority),
                    mDescription
            )
            mToDoViewModel.updateData(newData)
            Toast.makeText(requireContext(), "Data Berhasil Dibuat", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}