package com.junior.EXC2.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.google.android.material.chip.Chip
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.junior.EXC2.databinding.ActivityAddNoteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var openCameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var firestore: FirebaseFirestore
    private var firebaseAuth = Firebase.auth  // Inicializa firebaseAuth aquí
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        firestore = Firebase.firestore
        setContentView(binding.root)

        binding.btnTakePhoto.setOnClickListener{
            if (permissionValidated()){
                takePicture()
                }
            }
        openCameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK){
                val photo = result.data?.extras?.get("data") as Bitmap
                binding.imgPhoto.setImageBitmap(photo)
            }
        }
        binding.btnSalir.setOnClickListener {
            signOut()
        }


        binding.btnShowAddress.setOnClickListener(){
            val addressUri = Uri.parse( "geo:0,0?q=-12.1189716,-77.029266")
            val intent = Intent(Intent.ACTION_VIEW, addressUri)
            intent.setPackage("com.google.android.apps.maps")
            intent.resolveActivity(packageManager).let{
                startActivity(intent)
            }
            startActivity(Intent(this, AddNoteActivity::class.java))
        }
        binding.btnAddLabels.setOnClickListener{
        val label = binding.tilLabelsCinenote.editText?.text.toString()
            if (label.isNotEmpty()){
                val chip = Chip(this)
                chip.text = label
                binding.cgLabels.addView(chip)
                binding.tilLabelsCinenote.editText?.setText("")

            }
        }
        binding.btnRegisterNote.setOnClickListener{
            val tittle = binding.tilTitleNote.editText?.text.toString()
            val content = binding.tilIdNote.editText?.text.toString()
            val hasLabels = binding.cgLabels.childCount>0
            val color = binding.tilColorNote.editText?.text.toString()
            val isFavorite = binding.swichtFavorite.isChecked
            if(tittle.isNotEmpty() && content.isNotEmpty() && hasLabels && color.isNotEmpty()){
                addToFirestore(tittle, content, color, isFavorite)
            }


        }
        getNotesFromFirestore()
    }

    private fun addToFirestore(tittle: String, content: String, color: String, favorite: Boolean) {
        val labels: ArrayList<String> = getLabels()
        val newCnote = hashMapOf<String, Any>(
            "tittle" to tittle,
            "content" to content,
            "color" to color,
            "isFavorite" to favorite,
            "createOn" to Timestamp.now(),
            "labels" to labels
        )
        firestore.collection("note").add(newCnote)
            .addOnSuccessListener {
                Toast.makeText(this, "Nota agregada con id: ${it.id}",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                 Toast.makeText(this, "Ocurrio un error", Toast.LENGTH_SHORT).show()
            }

    }

    private fun  getNotesFromFirestore(){
        firestore.collection("note").whereEqualTo("isFavorite", true).get().addOnSuccessListener {
            for(document in it.documents){
                Log.d("Notas de cine", document.id)
            }
        }
    }
    private fun signOut() {
        firebaseAuth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun getLabels():ArrayList<String>{
        val labels = ArrayList<String>()
        val labelsCount =  binding.cgLabels.childCount
        for (counter in 0 until labelsCount){
            val chip = binding.cgLabels.getChildAt(counter) as Chip
            labels.add(chip.text.toString())
        }
        return  labels
    }

    private fun takePicture() {
        val intent = Intent()
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE)
        openCameraLauncher.launch(intent)
    }

    private fun permissionValidated(): Boolean{
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val permissionList: MutableList<String> = mutableListOf()
        if (cameraPermission !== PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.CAMERA)
        }
        if(permissionList.isNotEmpty()){
            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), 1000)
            return false
        }
        return true

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            1000 -> {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    takePicture()
                }
            }
        }
    }

}