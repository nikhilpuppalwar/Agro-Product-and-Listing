package pccoe.pune.agroproduct

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pccoe.pune.agroproduct.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webview.loadUrl("file:///android_asset/agro.html")
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webViewClient= WebViewClient()
    }
}