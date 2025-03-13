package pccoe.pune.agroproduct.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.project1762.Helper.ManagmentCart
import pccoe.pune.agroproduct.Adapter.KgsAdaptor
import pccoe.pune.agroproduct.DataClass.ItemsModel
import pccoe.pune.agroproduct.R
import pccoe.pune.agroproduct.databinding.ActivityDetailBinding
import pccoe.pune.agroproduct.ui.CartFragment

class DetailActivity : AppCompatActivity() {
    private  lateinit var binding:ActivityDetailBinding
    private lateinit var item:ItemsModel
    private lateinit var managmentCart: ManagmentCart
    private var numberOder = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)
        getBundle()

    }

    private fun getBundle(){
        item = intent.getParcelableExtra("object")!!

        binding.titleTxt.text = item.title
        binding.descriptionTxt.text = item.description
        binding.priceTxt.text = "₹"+ item.price
        binding.ratingtxt.text="${item.rating} Rating"

        initLists()
        binding.addtocartbtn.setOnClickListener{
            item.numberInCart = numberOder
            managmentCart.insertFood(item)
        }


        binding.backBtn.setOnClickListener{finish()}
        binding.cartbtn.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("open_cart", true) // Sending a flag to HomeActivity
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish() // Close DetailActivity
        }
    }

    private fun initLists(){
        val sizeList = ArrayList<String>()
        for (size in item.kgs) {
            sizeList.add(size.toString())
        }
        binding.kgslist.adapter = KgsAdaptor(sizeList)
        binding.kgslist.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

//        val colorlist = ArrayList<String>()
//        for (imageUrl in item.picUrl){
//            colorlist.add(imageUrl)
//        }
//        binding.colorlist.adapter =ColorAdaptor(colorlist)
//        binding.colorlist.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
slider()
    }

    private fun slider(){
        val imageList = ArrayList<SlideModel>()
        for (pic in item.picUrl) {
            imageList.add(SlideModel(pic, ScaleTypes.FIT)) // Corrected syntax
        }

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }
}