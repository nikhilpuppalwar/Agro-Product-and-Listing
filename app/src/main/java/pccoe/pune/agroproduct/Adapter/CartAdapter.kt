package pccoe.pune.agroproduct.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project1762.Helper.ChangeNumberItemsListener
import com.example.project1762.Helper.ManagmentCart
import com.squareup.picasso.Picasso
import pccoe.pune.agroproduct.DataClass.ItemsModel
import pccoe.pune.agroproduct.databinding.ViewholderCartBinding

class CartAdapter (
    private val listItemSelected:ArrayList<ItemsModel>,
    private val context: Context,
    private var changeNumberItemsListener: ChangeNumberItemsListener?=null
):RecyclerView.Adapter<CartAdapter.ViewHolder>(){

    class ViewHolder(val binding:ViewholderCartBinding):RecyclerView.ViewHolder(binding.root)

        private val managementCart = ManagmentCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val item = listItemSelected[position]
        holder.binding.titletxt.text=item.title
        holder.binding.feeEachItem.text = "₹${item.price}"
        holder.binding.totalEachitem.text = "₹${item.numberInCart * item.price}"
        holder.binding.numberitemtxt.text = item.numberInCart.toString()

        Picasso.get()
            .load(item.picUrl[0])
            .into(holder.binding.pic)

        holder.binding.pluscartBtn.setOnClickListener {
            managementCart.plusItem(listItemSelected,position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }
            })
        }
        holder.binding.minusCartBtn.setOnClickListener {
            managementCart.minusItem(listItemSelected,position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }
            })
        }
    }

    override fun getItemCount(): Int = listItemSelected.size
}