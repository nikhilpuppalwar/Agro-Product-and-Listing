package pccoe.pune.agroproduct.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import pccoe.pune.agroproduct.Login_singup.loginActivity
import pccoe.pune.agroproduct.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.shopingbtn.setOnClickListener {
            startActivity(Intent(this, loginActivity::class.java))
        }
binding.listingbtn.setOnClickListener {
    startActivity(Intent(this, ListingActivity::class.java))

}
    }
}