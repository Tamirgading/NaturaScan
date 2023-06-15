package com.farez.naturascan.ui.main.fragment.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farez.naturascan.databinding.FragmentSavedPlantsBinding
import com.farez.naturascan.di.Injection


class SavedPlantsFragment : Fragment() {
    private var _binding: FragmentSavedPlantsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SavedPlantViewModel
    private lateinit var vmFactory: SavedPlantVMFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupViewModel()
        _binding = FragmentSavedPlantsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.apply {
            //nanti ganti kalo cloud udah siap
            val adapter = SavedPlantAdapter(requireActivity().application)
            viewModel.getPlantLIst().observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    adapter.setPlantList(it)
                    rv.adapter = adapter
                    rv.layoutManager = LinearLayoutManager(context)
                    rv.visibility = View.VISIBLE
                    emptyTextView.visibility = View.GONE
                } else {
                    emptyTextView.visibility = View.VISIBLE
                    rv.visibility = View.GONE
                }
            }

        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupViewModel() {
        vmFactory = SavedPlantVMFactory(
            Injection.providePlantRepository(
                requireActivity().application
            )
        )
        viewModel = ViewModelProvider(this, vmFactory)[SavedPlantViewModel::class.java]
    }


}

