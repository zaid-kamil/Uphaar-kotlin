package com.digipod.uphaar.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.digipod.uphaar.R
import com.digipod.uphaar.databinding.FragmentDonationBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DonationFragment : Fragment() {

    private var _binding: FragmentDonationBinding? = null
    private val binding get() = _binding!!

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage
    private  lateinit var location:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDonationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgSelect.setOnClickListener {
            selectImage()
        }
        binding.btnDonateConfirm.setOnClickListener {
            sendToCloud()
        }
        location = DonationFragmentArgs.fromBundle(requireArguments()).selectedLocation
    }

    private fun sendToCloud() {
        val imgUri = binding.imgSelect.tag as Uri
        val place = location
        val title = binding.editItemTitle.text.toString().trim()
        val qty = binding.editQty.text.toString().trim()
        val pickup = binding.editPickupLocation.text.toString().trim()
        

    }

    private fun selectImage() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}