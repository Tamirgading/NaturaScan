package com.farez.naturascan.ui.login.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.farez.naturascan.R
import com.farez.naturascan.data.Status
import com.farez.naturascan.data.local.preferences.UserPreferences
import com.farez.naturascan.databinding.FragmentLoginBinding
import com.farez.naturascan.di.Injection
import com.farez.naturascan.ui.main.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "TOKEN")

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel
    private lateinit var vmFactory: AuthVMFactory
    private lateinit var userPref: DataStore<Preferences>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        userPref = requireContext().dataStore
        setupViewModel()
        binding.apply {
            gotoregisterTextView.setOnClickListener {
                gotoRegister()
            }
            loginButton.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = password.text.toString()
                if (!validEmail(email) || !validPass(password)) {
                    Toast.makeText(
                        context,
                        "Masukkan semua bagian dengan benar",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.login(email, password).observe(viewLifecycleOwner) {
                        when (it) {
                            is Status.Error -> {
                                progressBar2.visibility = View.GONE
                                Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                            }
                            is Status.Loading -> {
                                progressBar2.visibility = View.VISIBLE
                            }
                            is Status.Success -> {
                                progressBar2.visibility = View.GONE
                                viewModel.setAuth(true)
                                viewModel.saveLoginInfo(it.data.token, email)
                                gotoMain()
                            }
                        }
                    }
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
        vmFactory =
            AuthVMFactory(Injection.provideUserRepository(), UserPreferences.getInstance(userPref))
        viewModel = ViewModelProvider(requireActivity(), vmFactory)[AuthViewModel::class.java]
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

    private fun validEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validPass(pass: String): Boolean {
        return (pass.length >= 6)
    }
}