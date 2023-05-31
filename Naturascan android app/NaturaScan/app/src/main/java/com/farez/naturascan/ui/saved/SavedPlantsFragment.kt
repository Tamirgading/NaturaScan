package com.farez.naturascan.ui.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.farez.naturascan.data.local.model.SamplePlantList
import com.farez.naturascan.databinding.FragmentSavedPlantsBinding

class SavedPlantsFragment : Fragment() {
    private var _binding: FragmentSavedPlantsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedPlantsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.apply {
            val adapter = SavedPlantAdapter(SamplePlantList.sampleList)
            rv.adapter = adapter
            rv.layoutManager = LinearLayoutManager(context)
            adapter.notifyDataSetChanged()

        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

