package com.example.suratbebaspinjam.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.suratbebaspinjam.R
import com.example.suratbebaspinjam.databinding.FragmentSignUpBinding
import com.example.suratbebaspinjam.shared.AuthenticationResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeStatusBarColor(R.color.white)
        animate()
        button()
    }

    private fun changeStatusBarColor(colorResId: Int) {
        val color = ContextCompat.getColor(requireContext(), colorResId)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            activity?.window?.statusBarColor = color
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowInsetsController = activity?.window?.insetsController
            windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
    }

    private fun button() { binding.apply {
        signUpTextView.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
        }

        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (!validateInput(email, password, confirmPassword)) {
                return@setOnClickListener
            }

            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.signUpEmail(name, email, password).collect {
                        handleOnAuth(it)
                    }
                }
            }
        }
    }}

    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Please fill all the fields")
            return false
        }
        if (password != confirmPassword) {
            showToast("Password does not match")
            return false
        }
        return true
    }

    private fun handleOnAuth(result: AuthenticationResponse) {
        when(result) {
            is AuthenticationResponse.Error -> {
                onLoading(false)
                showToast(result.message)
            }
            is AuthenticationResponse.Loading -> {
                onLoading(true)
            }
            is AuthenticationResponse.Success -> {
                onLoading(false)
                showToast("Successfully signed up")
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
            }
        }
    }

    private fun onLoading(isLoading: Boolean) {
        binding.lpiLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun animate() { binding.apply {
        signUpTextView.text = Html.fromHtml("Already have an account? <b>Sign In</b>", Html.FROM_HTML_MODE_COMPACT)

        ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(titleTextView, View.ALPHA, 1f).setDuration(100)
        val message = ObjectAnimator.ofFloat(messageTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout = ObjectAnimator.ofFloat(nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout = ObjectAnimator.ofFloat(emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val confirmPasswordEditTextLayout = ObjectAnimator.ofFloat(confirmPasswordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(signUpTextView, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(signUpCard, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title, message,
                nameEditTextLayout,
                emailEditTextLayout,
                passwordEditTextLayout,
                confirmPasswordEditTextLayout,
                login, signup
            )
            startDelay = 100
        }.start()
    }}

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
