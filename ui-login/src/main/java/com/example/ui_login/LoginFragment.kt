package com.example.ui_login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.example.ui_login.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private var savedStateHandle: SavedStateHandle? = null

    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navController = findNavController()

        savedStateHandle = navController.previousBackStackEntry?.savedStateHandle

        binding.email.editText?.setText("oz@ozben.com")
        binding.password.editText?.setText("1234QWER")

        binding.loginButton.setOnClickListener {

            val userEmail: String = binding.email.editText?.text.toString()
            val userPassword: String = binding.password.editText?.text.toString()

            viewModel.login(userEmail, userPassword)
        }

        launchAndRepeatWithViewLifecycle {

            viewModel.loginState.collect {
                when (it) {
                    true -> {
                        savedStateHandle?.set(LOGIN_SUCCESSFUL, true)
                        navController.popBackStack()
                    }
                    false -> {
                        savedStateHandle?.set(LOGIN_SUCCESSFUL, false)
                    }
                }
            }
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }
}