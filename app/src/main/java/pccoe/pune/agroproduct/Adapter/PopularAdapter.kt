package pccoe.pune.agroproduct.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import pccoe.pune.agroproduct.DataClass.ItemsModel
import pccoe.pune.agroproduct.R
import pccoe.pune.agroproduct.activity.DetailActivity
import pccoe.pune.agroproduct.databinding.ViewholderPopularBinding

public class PopularAdapter(val items:MutableList<ItemsModel>):
    RecyclerView.Adapter<PopularAdapter.ViewHolder>(){
    private var context:Context?=null

    class  ViewHolder(val binding:ViewholderPopularBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.ViewHolder {
        context = parent.context
        val binding =ViewholderPopularBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularAdapter.ViewHolder, position: Int) {
        holder.binding.titleText.text = items[position].title
        holder.binding.priceText.text = "₹" + items[position].price.toString() + "/KG"

        val imageUrl = items[position].picUrl.getOrNull(0) // Get first image URL if available
        if (!imageUrl.isNullOrEmpty()) {
            Picasso.get()
                .load(imageUrl)
                .into(holder.binding.pic)
        } else {
            holder.binding.pic.setImageResource(R.drawable.avatar_background) // Default image
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailActivity::class.java)
            intent.putExtra("object",items[position])
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = items.size


    fun updateData(newItems: MutableList<ItemsModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

}