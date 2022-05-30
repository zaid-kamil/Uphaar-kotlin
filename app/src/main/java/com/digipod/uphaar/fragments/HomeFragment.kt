package com.digipod.uphaar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.digipod.uphaar.R
import com.digipod.uphaar.adapters.PlaceAdapter
import com.digipod.uphaar.databinding.FragmentHomeBinding
import com.digipod.uphaar.listeners.OnDonationPlaceSelectionListener
import com.digipod.uphaar.models.DonationPlace
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class HomeFragment : Fragment(),OnDonationPlaceSelectionListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var placeList:ArrayList<DonationPlace>
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        placeList = arrayListOf()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = PlaceAdapter(placeList,this@HomeFragment,R.layout.card_place)
        }
        loadData()

    }

    private fun loadData() {
        db.collection("places").get()
            .addOnFailureListener {
                showSnack(it.message.toString())
            }.addOnSuccessListener {
                if(it.size()>0){
                    for (document in it.documents) {
                        val item = document.toObject<DonationPlace>()
                        if (item != null) {
                            placeList.add(item)
                        }
                    }
                    try {
                        binding.recyclerView.adapter?.notifyDataSetChanged()
                    }catch (e:Exception){

                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDonationPlaceSelected(v: View, place: DonationPlace, pos: Int) {
      findNavController().navigate(HomeFragmentDirections.actionFirstFragmentToSecondFragment(place.name))
    }
    private fun showSnack(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }
}