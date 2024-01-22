package id.ac.sttpyk.tokohijab.helper

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.sttpyk.tokohijab.R
import id.ac.sttpyk.tokohijab.databinding.ItemHijabBinding
import id.ac.sttpyk.tokohijab.model.HijabModel

class AdapterHijab(private val items:ArrayList<HijabModel.Data>, val listener: OnAdapterListener):
    RecyclerView.Adapter<AdapterHijab.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemHijabBinding) :RecyclerView.ViewHolder(binding.root) {
        fun init(data: HijabModel.Data){
            binding.namabarang.text=data.hijab
            binding.kapasitas.text=data.harga.toString()
            binding.imgDelete.setOnClickListener{
                onItemClickCallback.onItemClicked(data)
                Log.e("TAG", "init: Tombol di klik",)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val V = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hijab, parent, false)
        val binding = ItemHijabBinding.bind(V)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(items[position])
        holder.itemView.setOnClickListener {
            listener.OnClick(items[position])
        }
    }

    private interface OnItemClickCallback {
        fun onItemClicked(data: HijabModel.Data)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(body: (HijabModel.Data) -> Unit) {
        onItemClickCallback = object : OnItemClickCallback {
            override fun onItemClicked(data: HijabModel.Data){
                body(data)
            }
        }
    }
    interface OnAdapterListener{
        fun OnClick(hijab: HijabModel.Data)
    }
}