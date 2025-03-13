package pccoe.pune.agroproduct.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1762.Helper.ChangeNumberItemsListener
import com.example.project1762.Helper.ManagmentCart
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject
import pccoe.pune.agroproduct.Adapter.CartAdapter
import pccoe.pune.agroproduct.R
import pccoe.pune.agroproduct.databinding.FragmentCartBinding

class CartFragment : Fragment(),PaymentResultWithDataListener {
private lateinit var binding:FragmentCartBinding
private lateinit var managmentCart: ManagmentCart
private var tax:Double =0.0
    private var total:Double =0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        managmentCart = ManagmentCart(requireContext())

        setVariable()
        initCartList()
        calculateCart()




        Checkout.preload(requireContext())
        val co = Checkout()
        co.setKeyID("rzp_test_n5bif8Wp4cEalY")
binding.buyBtn.setOnClickListener {
    initpayment()
}

        return binding.root
    }
    private  fun initCartList(){
        val context = requireContext()
        binding.viewchart.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.viewchart.adapter = CartAdapter(managmentCart.getListCart(),context,object:ChangeNumberItemsListener{
            override fun onChanged() {
                initCartList() // Refresh cart list when an item is added/removed
                calculateCart() // Update total and tax
            }
        })
        with(binding){
            emptyTax.visibility =
                if(managmentCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollview.visibility =
                if(managmentCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun calculateCart(){
        val percentTax = 0.2
        val deliver = 10.0
        tax = String.format("%.2f", managmentCart.getTotalFee() * percentTax).toDouble()
        total = String.format("%.2f", (managmentCart.getTotalFee() + tax + deliver)).toDouble()
        val itemTotal = String.format("%.2f", managmentCart.getTotalFee()).toDouble()


        with(binding){
            totalfeetxt.text = "$itemTotal"
            Taxtxt.text ="$tax"
            delivertxt.text ="$deliver"
            totalTxt.text = "$total"
        }
    }
    private fun setVariable(){
        binding.backbtn.setOnClickListener{
            requireActivity().onBackPressed()
        }
    }
    private fun initpayment(){
        val activity = requireActivity()
        val co = Checkout()

        try {
            val amountInRupees = total // Get the total fee
            val amountInPaise = (amountInRupees * 100).toInt() // Convert to paise

            val options = JSONObject()
            options.put("name", "nikhil Puppalar")
            options.put("description", "Order Payment")
            options.put("image", "http://example.com/image/rzp.jpg")
            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put("amount", amountInPaise) // Set the actual cart amount

            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            val prefill = JSONObject()
            prefill.put("email", "nikhilPuppalwar@gmail.com")
            prefill.put("contact", "9876543210")

            options.put("prefill", prefill)
            co.open(requireActivity(), options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(requireContext(), "Payment successful", Toast.LENGTH_SHORT).show()

    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {

    }
}