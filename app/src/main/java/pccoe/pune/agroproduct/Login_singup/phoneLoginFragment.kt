package pccoe.pune.agroproduct.Login_singup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import pccoe.pune.agroproduct.activity.HomeActivity
import pccoe.pune.agroproduct.R
import pccoe.pune.agroproduct.databinding.FragmentPhoneLoginBinding

class phoneLoginFragment : Fragment() {

    private lateinit var binding: FragmentPhoneLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhoneLoginBinding.inflate(inflater, container, false)

        // Email Login Button Click
        binding.loginButton.setOnClickListener {
            val loginUser = binding.usernameEditText.text.toString().trim()
            val loginPass = binding.passwordEditText.text.toString().trim()

            if (loginUser.isNotBlank() && loginPass.isNotBlank()) {
                startActivity(Intent(activity, HomeActivity::class.java))
                activity?.finish()
                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
            }
        }



        // Forgot Password Navigation
        binding.forgotPasswordText.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.contanier, RegisterFragment()) // Ensure correct container ID
                .addToBackStack(null)
                .commit()
        }


        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(),gso)
        binding.loginGoogle.setOnClickListener{
            val signInclient = googleSignInClient.signInIntent
            launcher.launch(signInclient)
        }

        return binding.root
    }
    private val launcher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful){
                val account: GoogleSignInAccount?=task.result
                val credential  = GoogleAuthProvider.getCredential(account?.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(requireContext(), "Google Login Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(requireContext(), HomeActivity::class.java))
                    }else{
                        Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
