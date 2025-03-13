package pccoe.pune.agroproduct.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import pccoe.pune.agroproduct.R
import pccoe.pune.agroproduct.databinding.ActivityHomeBinding
import pccoe.pune.agroproduct.ui.CartFragment
import pccoe.pune.agroproduct.ui.ExplorlarFragment
import pccoe.pune.agroproduct.ui.HomeFragment
import pccoe.pune.agroproduct.ui.OrdersFragment
import pccoe.pune.agroproduct.ui.ProfileFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
replaceFragment(HomeFragment())
        binding.navView.selectedItemId = R.id.navigation_home
       binding.navView.setOnItemSelectedListener { item ->
when(item.itemId){
     R.id.navigation_home ->{
         replaceFragment(HomeFragment())
         true
     }
     R.id.navigation_Cart ->{
        replaceFragment(CartFragment())
         true
     }
      R.id.navigation_Orders ->{
        replaceFragment(OrdersFragment())
          true
     }
      R.id.navigation_Profile ->{
        replaceFragment(ProfileFragment())
          true
     }
    R.id.navigation_Explore ->{
        replaceFragment(ExplorlarFragment())
        true
    }
    else -> false
}
       }

        if (intent.getBooleanExtra("open_cart", false)) {
            openCartFragment()
        }
    }
    private fun replaceFragment(fragment:Fragment){
        val fragmentTransaction =supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_layout,fragment)
        fragmentTransaction.commit()

    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.getBooleanExtra("open_cart", false) == true) {
            openCartFragment()
        }
    }

    private fun openCartFragment() {
        val fragment = CartFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout, fragment)
            .addToBackStack(null)
            .commit()
    }
}