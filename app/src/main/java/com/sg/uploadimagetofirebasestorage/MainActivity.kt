package com.sg.uploadimagetofirebasestorage

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sg.uploadimagetofirebasestorage.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null

    private lateinit var imageUri: Uri

    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
   // var imageUri: Uri?=null
    var myUrl=""

    private  var storageProfilePickerRef:StorageReference? = null
    val POST_IMAGE="Post_Image"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageProfilePickerRef=FirebaseStorage.getInstance().reference.child(POST_IMAGE)


        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        binding.btnChooseImage.setOnClickListener { selectImage() }
        binding.btnUploadImage.setOnClickListener { uploadImage() }
        binding.btnDownloadImage.setOnClickListener { downloadImage() }

       /* binding.btnChooseImage.setOnClickListener { launchGallery() }
        binding.btnUploadImage.setOnClickListener { uploadImage() }
        binding.btnDownloadImage.setOnClickListener { downloadImage() }*/


     //   binding.btnUploadImage.setOnClickListener { uploadImage() }
      //  binding.btnDownloadImage.setOnClickListener { downloadImage() }

    }

    private fun selectImage() {
      //  Log.i("gg","Its line 50   filePath =$filePath")
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("gg","Its line 58   filePath =$filePath")
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {

            imageUri=data?.data!!
            binding.imagePreview.setImageURI(imageUri)

          /*  if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                binding.imagePreview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }*/
        }
    }


    private fun downloadImage() {
        val fileName = "1234"
        val storageReferenc=FirebaseStorage.getInstance().getReference("images/$fileName")
        val localfile=File.createTempFile("template","jpg")
        storageReferenc.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.fireBaseImage.setImageBitmap(bitmap
            )

        }.addOnFailureListener {
            Toast.makeText(this," Cannot download  image",Toast.LENGTH_LONG).show()

        }




      /*  val ref = storageReference?.child("myImages/" +imageName.toString())
        val uploadTask = ref?.putFile(filePath!!)

      *//*  val storageRef = FirebaseStorage.getInstance().reference
            .child("myImages/$imageName.jpg")*//*
//        Log.i("gg","  myImages/$imageName.jpg =               myImages/$imageName.jpg ")
        val localFile = File.createTempFile("tempImage", "jpg")
        if (ref != null) {
            ref.getFile(localFile)
                .addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    binding.imagePreview.setImageBitmap(bitmap)
                }.addOnFailureListener {

                }
        }*/


    }

    private fun launchGallery() {
        Log.i("gg","Its line 50   filePath =$filePath")
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    private fun uploadImage(){
        val  formator = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss",Locale.getDefault())
        val now=Date()
        //val fileName=formator.format(now)
        val fileName="1234"
        val storageReferenc=FirebaseStorage.getInstance().getReference("images/$fileName")
        storageReferenc.putFile(imageUri).
                addOnSuccessListener {
                    binding.fireBaseImage.setImageURI(null)
                    Toast.makeText(this,"Succsess in uploding image",Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
            Toast.makeText(this,"NOOOT !!! Succsess in uploding image",Toast.LENGTH_LONG).show()

        }




      /*  Log.i("gg","Its line 73   filePath =$filePath")
        val num=120
        if(filePath != null){
         //  val ref = storageReference?.child("myImages/" + UUID.randomUUID().toString())
            val ref = storageReference?.child("myImages/" + num.toString())
            val uploadTask = ref?.putFile(filePath!!)

        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }*/
    }
}