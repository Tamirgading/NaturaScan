package com.farez.naturascan.ui.login.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farez.naturascan.R
import com.farez.naturascan.databinding.FragmentLoginBinding
import com.farez.naturascan.ui.main.MainActivity

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.apply {
            gotoregisterTextView.setOnClickListener {
                gotoRegister()
            }
            loginButton.setOnClickListener {
                gotoMain()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun gotoRegister() {
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, RegisterFragment(), RegisterFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }
    private fun gotoMain() {
        startActivity(Intent(activity, MainActivity::class.java))
    }
}