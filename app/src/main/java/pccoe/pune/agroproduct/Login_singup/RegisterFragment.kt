package pccoe.pune.agroproduct.Login_singup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pccoe.pune.agroproduct.activity.HomeActivity
import pccoe.pune.agroproduct.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var binding: FragmentRegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding!!.Registerbtn.setOnClickListener {


            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }


        return binding!!.root
    }

}