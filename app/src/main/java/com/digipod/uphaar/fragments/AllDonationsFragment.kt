package com.digipod.uphaar.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.digipod.uphaar.R
import com.digipod.uphaar.adapters.DonateAdapter
import com.digipod.uphaar.databinding.FragmentAllDonationsBinding
import com.digipod.uphaar.listeners.OnDonateItemClickListener
import com.digipod.uphaar.models.DonateModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class AllDonationsFragment : Fragment(), OnDonateItemClickListener {
    private var _binding: FragmentAllDonationsBinding? = null
    private val binding get() = _binding!!

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private lateinit var donationList: ArrayList<DonateModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAllDonationsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        donationList = arrayListOf()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter =
                DonateAdapter(donationList, this@AllDonationsFragment, R.layout.card_all_donation)
        }
        loadMyDonations()

    }

    private fun loadMyDonations() {
        donationList.clear()
        db.collection("donations").whereEqualTo("uid", auth.currentUser?.uid)
            .orderBy("donationPlace")
            .get()
            .addOnSuccessListener {
                if (it.size() > 0) {
                    for (document in it.documents) {
                        val item = document.toObject<DonateModel>()
                        item?.let { it1 -> donationList.add(it1) }
                    }
                    try {
                        binding.recyclerView.adapter?.notifyDataSetChanged()
                        binding.textHeader.text = "All Uphaars (${it.size()})"
                    } catch (e: Exception) {

                    }
                } else {
                    AlertDialog.Builder(requireContext()).apply {
                        setTitle("No Uphaar Made Yet")
                        setMessage("You can select a Orphanage from the home screen and send some donations")
                        setPositiveButton("Ok") { _, _ ->
                            findNavController().popBackStack()
                        }
                        create()
                        show()
                    }
                }
            }
            .addOnFailureListener {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("Error Loading your Donations")
                    setMessage(it.message.toString())
                    setPositiveButton("Retry") { _, _ -> loadMyDonations() }
                    create()
                    show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(v: View, d: DonateModel, pos: Int) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Donation Details")
            setMessage("${d.donationPlace} received ${d.qty} ${d.item} from ${d.pickupPlace}")
            setPositiveButton("Make your own Donation") { _, _ ->
                findNavController().navigate(R.id.action_allDonationsFragment_to_donate)
            }
            create()
            show()
        }
    }
}