package com.example.ui_login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.example.ui_login.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import extensions.launchAndRepeatWithViewLifecycle
import timber.log.Timber
import java.lang.Exception

class LoginFragment : Fragment() {


    lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    private var savedStateHandle: SavedStateHandle? = null

    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            FirebaseApp.initializeApp(it)
        }
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
                    is LoginResult.SUCCESS -> {
                        savedStateHandle?.set(LOGIN_SUCCESSFUL, true)
                        findNavController().popBackStack()
                    }
                    is LoginResult.FAILURE -> {
                        savedStateHandle?.set(LOGIN_SUCCESSFUL, false)
                        Timber.e("Sign in failed -> ${it.exception ?: ""}")
                    }
                }
            }
        }
    }

}