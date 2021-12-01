package kr.co.yeaeun.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GunplaAdapter(
    private val model: GunplaViewModel,
    private val onClick: (GunplaViewModel.Mechanic) -> Unit
): RecyclerView.Adapter<GunplaAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(android.R.id.text1)

        init {
            itemView.setOnClickListener {
                onClick(model.getGunpla(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            android.R.layout.simple_list_item_1,
            parent,
            false)
        return ViewHolder(view)
    }

    // onCreateViewHolder에서 생성한 것 바인딩
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = model.getGunpla(position)
        val str = "${item.model} (${item.name})"
        holder.text.text = str
    }

    override fun getItemCount(): Int {
        return model.getSize()
    }
}