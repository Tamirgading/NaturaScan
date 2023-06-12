package com.farez.naturascan.ui.login.fragment

import android.content.Context
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
import com.farez.naturascan.databinding.FragmentRegisterBinding
import com.farez.naturascan.di.Injection

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "TOKEN")

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel
    private lateinit var vmFactory: AuthVMFactory
    private lateinit var userPref: DataStore<Preferences>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        userPref = requireContext().dataStore
        setupViewModel()

        binding.apply {
            gotologinTextView.setOnClickListener {
                gotoLogin()
            }
            registerButton.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = password.text.toString()
                if (!validEmail(email) || !validPass(password)) {
                    Toast.makeText(
                        context,
                        "Masukkan semua bagian dengan benar",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.register(email, password).observe(viewLifecycleOwner) {
                        when (it) {
                            is Status.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(context, "${it.error}", Toast.LENGTH_SHORT).show()
                            }
                            is Status.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Status.Success -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    context,
                                    "Akun berhasil dibuat, silahkan login",
                                    Toast.LENGTH_SHORT
                                ).show()
                                gotoLogin()
                            }
                        }
                    }
                }
            }
        }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupViewModel() {
        vmFactory =
            AuthVMFactory(Injection.provideUserRepository(), UserPreferences.getInstance(userPref))
        viewModel = ViewModelProvider(requireActivity(), vmFactory)[AuthViewModel::class.java]
    }

    private fun gotoLogin() {
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, LoginFragment(), LoginFragment::class.java.simpleName)
            commit()
        }
    }

    fun validEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validPass(pass: String): Boolean {
        return (pass.length >= 6)
    }

}