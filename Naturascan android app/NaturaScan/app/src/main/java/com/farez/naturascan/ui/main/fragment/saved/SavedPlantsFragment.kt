package com.farez.naturascan.ui.main.fragment.saved

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farez.naturascan.data.local.model.Plant
import com.farez.naturascan.data.local.model.SamplePlantList
import com.farez.naturascan.data.local.preferences.UserPreferences
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
            val adapter = SavedPlantAdapter()
            addSamplePlant(SamplePlantList.sampleList)
            viewModel.getPlantLIst().observe(viewLifecycleOwner) {
                if (it != null) {
                    adapter.setPlantList(it)
                    rv.adapter = adapter
                    rv.layoutManager = LinearLayoutManager(context)
                }
                else {
                    Toast.makeText(context, "Plant List is Empty", Toast.LENGTH_SHORT).show()
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
        vmFactory = SavedPlantVMFactory(Injection.provideRepository(requireContext(), requireActivity().application))
        viewModel = ViewModelProvider(this, vmFactory)[SavedPlantViewModel::class.java]
    }
    fun addSamplePlant(listPlant : List<Plant>) {
        listPlant.forEach { plant ->
            viewModel.insertPlant(plant)
        }
    }


}

