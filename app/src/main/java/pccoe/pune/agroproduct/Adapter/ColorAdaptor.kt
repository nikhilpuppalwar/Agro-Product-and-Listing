package pccoe.pune.agroproduct.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import pccoe.pune.agroproduct.R
import pccoe.pune.agroproduct.databinding.ViewholderColorBinding

class ColorAdaptor( private val items:MutableList<String>):
RecyclerView.Adapter<ColorAdaptor.Viewholder>(){

    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    private lateinit var context: Context

    inner class Viewholder(val binding:ViewholderColorBinding):
    RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorAdaptor.Viewholder {
context = parent.context
        val binding = ViewholderColorBinding.inflate(LayoutInflater.from(context),parent,false)
    return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: ColorAdaptor.Viewholder, position: Int ) {


        Picasso.get()
            .load(items[position])
            .fit()
            .centerCrop()
            .into(holder.binding.pic)

        holder.binding.root.setOnClickListener{
                if (selectedPosition!=position) {
                    lastSelectedPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(lastSelectedPosition)
                    notifyItemChanged(selectedPosition)
                }
        }

        if (selectedPosition == position){
            holder.binding.colorLayout.setBackgroundResource(R.drawable.fh_greh_bg)

        }
        else{
            holder.binding.colorLayout.setBackgroundResource(R.drawable.fh_buy_bg)
        }

    }

    override fun getItemCount(): Int = items.size
}