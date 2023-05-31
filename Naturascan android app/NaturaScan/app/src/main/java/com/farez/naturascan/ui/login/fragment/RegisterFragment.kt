package com.farez.naturascan.ui.login.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farez.naturascan.R
import com.farez.naturascan.databinding.FragmentRegisterBinding
import com.farez.naturascan.ui.login.LoginActivity
import com.farez.naturascan.ui.main.MainActivity

class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.apply {
            gotologinTextView.setOnClickListener {
                gotoLogin()
            }
            registerButton.setOnClickListener {
                gotoMain()
            }
        }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun gotoLogin() {
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, LoginFragment(), LoginFragment::class.java.simpleName)
            commit()
        }
    }
    private fun gotoMain() {
        startActivity(Intent(activity, MainActivity::class.java))
        val loginActivity = activity as LoginActivity
        val manager = loginActivity.supportFragmentManager
        manager.beginTransaction().remove(RegisterFragment()).commit()
        manager.popBackStack()
    }

}