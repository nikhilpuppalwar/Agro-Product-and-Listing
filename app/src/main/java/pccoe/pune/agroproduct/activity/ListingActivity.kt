package pccoe.pune.agroproduct.activity

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pccoe.pune.agroproduct.R
import pccoe.pune.agroproduct.databinding.ActivityListingBinding

class ListingActivity : AppCompatActivity() {
    private lateinit var titleEdt: EditText
    private lateinit var descriptionEdt: EditText
    private lateinit var priceEdt: EditText
    private lateinit var ratingEdt: EditText
    private lateinit var picUrlEdt: EditText
    private lateinit var kgsEdt: EditText

    private lateinit var titleTxt: TextView
    private lateinit var descriptionTxt: TextView
    private lateinit var priceTxt: TextView
    private lateinit var ratingTxt: TextView
    private lateinit var picUrlTxt: TextView
    private lateinit var kgsTxt: TextView

    private lateinit var database: DatabaseReference

    private val picUrls = mutableListOf<String>()
    private val kgsList = mutableListOf<String>()

    private lateinit var binding:ActivityListingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance().getReference("Items")

        // Initialize Views
        titleEdt = binding.titleEdt
        descriptionEdt = binding.descriptionEdt
        priceEdt = binding.PriceEdt
        ratingEdt = binding.RatingEdt
        picUrlEdt = binding.picUrlEdt
        kgsEdt = binding.kgsEdt

        titleTxt = binding.titleTxt
        descriptionTxt = binding.descriptionTxt
        priceTxt = binding.priceTxt
        ratingTxt = binding.ratingTxt
        picUrlTxt = binding.PicTxt
        kgsTxt = binding.kgsTxt

        val btnCheck = binding.checkBtn
        val btnReset = binding.resetbtn
        val btnAddPicUrl = binding.imageBtn
        val btnAddKgs = binding.kgsBtn
        val btnUpload = binding.btnUpload

        // Add picUrl to list
        btnAddPicUrl.setOnClickListener {
            val url = picUrlEdt.text.toString().trim()
            if (url.isNotEmpty()) {
                picUrls.add(url)
                picUrlTxt.text = "Image URLs: ${picUrls.joinToString()}"
                picUrlEdt.text.clear()
            }
        }

        // Add kgs to list
        btnAddKgs.setOnClickListener {
            val kg = kgsEdt.text.toString().trim()
            if (kg.isNotEmpty()) {
                kgsList.add(kg)
                kgsTxt.text = "Kgs: ${kgsList.joinToString()}"
                kgsEdt.text.clear()
            }
        }

        // Check Button: Display entered values
        btnCheck.setOnClickListener {
            titleTxt.text = "Title: ${titleEdt.text}"
            descriptionTxt.text = "Description: ${descriptionEdt.text}"
            priceTxt.text = "Price: ${priceEdt.text}"
            ratingTxt.text = "Rating: ${ratingEdt.text}"
        }

        // Reset Button: Clear all fields
        btnReset.setOnClickListener {
            titleEdt.text.clear()
            descriptionEdt.text.clear()
            priceEdt.text.clear()
            ratingEdt.text.clear()
            picUrlEdt.text.clear()
            kgsEdt.text.clear()

            titleTxt.text = "Title: "
            descriptionTxt.text = "Description: "
            priceTxt.text = "Price: "
            ratingTxt.text = "Rating: "
            picUrlTxt.text = "Image URLs: "
            kgsTxt.text = "Kgs: "

            picUrls.clear()
            kgsList.clear()
        }

        btnUpload.setOnClickListener {
            getNextOrderNumber { nextOrder ->
                val item = hashMapOf(
                    "title" to titleEdt.text.toString(),
                    "description" to descriptionEdt.text.toString(),
                    "price" to priceEdt.text.toString().toIntOrNull(),
                    "rating" to ratingEdt.text.toString().toDoubleOrNull(),
                    "picUrl" to picUrls,
                    "kgs" to kgsList
                )

                database.child(nextOrder.toString()).setValue(item)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Item added with Order: $nextOrder", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to upload", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    // Function to get the next order number
    private fun getNextOrderNumber(callback: (Int) -> Unit) {
        database.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lastOrder = snapshot.children.lastOrNull()?.key?.toIntOrNull() ?: -1
                callback(lastOrder + 1)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Failed to read last order number", error.toException())
                callback(0)  // Default to 0 if there's an error
            }
        })
    }
}