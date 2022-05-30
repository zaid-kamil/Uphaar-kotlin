package com.digipod.uphaar.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.digipod.uphaar.R
import com.digipod.uphaar.databinding.FragmentDonationBinding
import com.digipod.uphaar.models.DonateModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class DonationFragment : Fragment() {

    private var _binding: FragmentDonationBinding? = null
    private val binding get() = _binding!!

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage
    private lateinit var location: String
    private var photoUri: Uri? = null

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
            Toast.makeText(requireContext(), "select image", Toast.LENGTH_LONG).show()
        }
        binding.btnDonateConfirm.setOnClickListener {
            sendToCloud()
        }
        location = DonationFragmentArgs.fromBundle(requireArguments()).selectedLocation
        binding.textToLocation.setText(location)
    }

    private fun sendToCloud() {
        showUploadStatusUI()
        val place = location
        val title = binding.editItemTitle.text.toString().trim()
        val qty = binding.editQty.text.toString().trim()
        val pickup = binding.editPickupLocation.text.toString().trim()
        val uid = auth.currentUser?.uid
        val filename = photoUri?.getName(requireActivity())
        val folderRef = storage.reference.child("donations").child(filename.toString())
        photoUri?.let {
            folderRef.putFile(it).addOnProgressListener { task ->
                val progress = (task.bytesTransferred / task.totalByteCount) * 100
                binding.textUploadingVal.text =
                    String.format("UPLOADING... %.1f", progress.toFloat())
                Log.e(
                    "Uploading",
                    "=>${task.bytesTransferred}/${task.totalByteCount} ,${progress} $filename %"
                )
            }.addOnSuccessListener {
                folderRef.downloadUrl
                    .addOnSuccessListener { uri ->
                        db.collection("donations").add(
                            DonateModel(
                                uid!!,
                                location,
                                title,
                                uri.toString(),
                                pickup,
                                qty.toInt(),
                            )
                        ).addOnSuccessListener {
                            hideUploadStatusUI()
                            val builder = AlertDialog.Builder(requireContext())
                                .setTitle("Your Donation have been listed")
                                .setMessage("Someone from the $location will soon visit the $pickup place")
                                .setPositiveButton("Make Another", null)
                                .setNeutralButton(
                                    "Home",
                                    DialogInterface.OnClickListener { d, i ->
                                        findNavController().popBackStack()
                                    })
                                .setNegativeButton(
                                    "Your Donations",
                                    DialogInterface.OnClickListener { d, i ->
                                        findNavController().navigate(R.id.action_donate_to_mydonations)
                                    })
                                .setCancelable(false)
                                .create().show()
                        }
                    }
                    .addOnFailureListener {
                        hideUploadStatusUI()
                    }
            }.addOnFailureListener {
                hideUploadStatusUI()
            }
        }
    }

    private fun showUploadStatusUI() {
        binding.pb.visibility = View.VISIBLE
        binding.textUploadingVal.visibility = View.VISIBLE
    }

    private fun hideUploadStatusUI() {
        binding.pb.visibility = View.GONE
        binding.textUploadingVal.visibility = View.GONE
    }

    private val result = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            photoUri = it.data?.data
            Glide.with(requireActivity()).load(photoUri).into(binding.imgSelect)
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        result.launch(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun Uri.getName(context: Context): String {
        val returnCursor = context.contentResolver.query(this, null, null, null, null)
        val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor?.moveToFirst()
        val fileName = nameIndex?.let { returnCursor.getString(it) }
        returnCursor?.close()
        return fileName.toString()
    }
}

