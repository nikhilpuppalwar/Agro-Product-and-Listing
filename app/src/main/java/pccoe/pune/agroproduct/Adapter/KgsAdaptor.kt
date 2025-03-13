package pccoe.pune.agroproduct.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pccoe.pune.agroproduct.R
import pccoe.pune.agroproduct.databinding.ViewholderKgsBinding

class KgsAdaptor(val items: MutableList<String>) :
    RecyclerView.Adapter<KgsAdaptor.Viewholder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class Viewholder(val binding: ViewholderKgsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding = ViewholderKgsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.binding.textView10.text = items[position]

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

        if (selectedPosition == position) {
            holder.binding.kgslayout.setBackgroundResource(R.drawable.fh_greh_bg)
            holder.binding.textView10.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
        } else {
            holder.binding.kgslayout.setBackgroundResource(R.drawable.fh_buy_bg)
            holder.binding.textView10.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
        }
    }

    override fun getItemCount(): Int = items.size
}
