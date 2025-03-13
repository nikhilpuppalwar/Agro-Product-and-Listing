package pccoe.pune.agroproduct.Login_singup

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import pccoe.pune.agroproduct.R
import pccoe.pune.agroproduct.databinding.ActivityLoginBinding

class loginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val manager =supportFragmentManager
        val tr = manager.beginTransaction()
        tr.replace(R.id.contanier ,phoneLoginFragment())
        tr.commit()

        binding.Logintxt.setOnClickListener{
            binding.Logintxt.setTextColor(ContextCompat.getColor(this, R.color.green))
            binding.Registertxt.setTextColor(ContextCompat.getColor(this, R.color.black))
            val manager =supportFragmentManager
            val tr = manager.beginTransaction()
            tr.replace(R.id.contanier ,phoneLoginFragment())
            tr.commit()
        }
        binding.Registertxt.setOnClickListener{
            binding.Logintxt.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.Registertxt.setTextColor(ContextCompat.getColor(this, R.color.green))

            val manager =supportFragmentManager
            val tr = manager.beginTransaction()
            tr.replace(R.id.contanier ,RegisterFragment())
            tr.commit()
        }
    }
}