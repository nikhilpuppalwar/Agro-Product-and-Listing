package pccoe.pune.agroproduct.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import pccoe.pune.agroproduct.Login_singup.loginActivity
import pccoe.pune.agroproduct.R
import pccoe.pune.agroproduct.activity.HomeActivity
import pccoe.pune.agroproduct.databinding.FragmentProfileBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var googleSignInClient: GoogleSignInClient
    private val PICK_IMAGE_REQUEST = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Initialize SharedPreferences
        sharedPreferences =
            requireActivity().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val savedName = sharedPreferences.getString("name", "Your Name")
        val savedEmail = sharedPreferences.getString("email", "your@email.com")
        val savedImagePath = sharedPreferences.getString("profileImage", null)
        binding.usernametxt.text = savedName
        binding.emailtxt.text = savedEmail

        if (savedImagePath != null) {
            val bitmap = BitmapFactory.decodeFile(savedImagePath)
            binding.profileImage.setImageBitmap(bitmap)
        }

        binding.profileImage.setOnClickListener {
            selectImageFromGallery()
        }
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        binding.Logoutbtn.setOnClickListener {
            if (account != null) {
                // Google Logout
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
                googleSignInClient.signOut().addOnCompleteListener {
                    startActivity(Intent(requireContext(), loginActivity::class.java))
                    requireActivity().finish()
                }
            } else {
                // Direct Login -> Redirect to Login Page
                startActivity(Intent(requireContext(), loginActivity::class.java))
                requireActivity().finish()
            }
        }

        // Edit Profile Button - Show AlertDialog
        binding.editbtn.setOnClickListener {
            showEditProfileDialog()
        }
        return binding.root
    }

    // Function to open gallery
    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Handling image selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.data != null) {
            val imageUri: Uri = data.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
            binding.profileImage.setImageBitmap(bitmap)

            // Save image to internal storage
            val imagePath = saveImageToInternalStorage(bitmap)
            sharedPreferences.edit().putString("profileImage", imagePath).apply()
        }
    }

    // Save image to internal storage
    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        val file = File(requireContext().filesDir, "profile_image.jpg")
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file.absolutePath
    }

    private fun showEditProfileDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_profile, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.editName)
        val editTextEmail = dialogView.findViewById<EditText>(R.id.editEmail)

        editTextName.setText(sharedPreferences.getString("name", ""))
        editTextEmail.setText(sharedPreferences.getString("email", ""))

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Edit Profile")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newName = editTextName.text.toString().trim()
                val newEmail = editTextEmail.text.toString().trim()

                if (newName.isNotEmpty() && newEmail.isNotEmpty()) {
                    sharedPreferences.edit().apply {
                        putString("name", newName)
                        putString("email", newEmail)
                        apply()
                    }
                    binding.usernametxt.text = newName
                    binding.emailtxt.text = newEmail
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}