package com.digipod.uphaar.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digipod.uphaar.MainActivity
import com.digipod.uphaar.R
import com.digipod.uphaar.databinding.FragmentLoginBinding
import com.digipod.uphaar.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()
            val cPassword = binding.editConfirmPassword.text.toString().trim()
            if (email.length > 11) {
                if (password == cPassword) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            updateUI(it.user)
                        }.addOnFailureListener {
                            showSnack(it.message.toString())
                        }
                } else {
                    showSnack("Passwords do not match")
                }
            } else {
                showSnack("Email in invalid, use correct email")
            }

        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSnack(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser)
    }
}