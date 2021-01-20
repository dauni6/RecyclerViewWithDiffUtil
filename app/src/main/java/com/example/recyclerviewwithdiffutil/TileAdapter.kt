package com.example.recyclerviewwithdiffutil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewwithdiffutil.databinding.TileItemBinding

class TileAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val tileAdapterModel = TileAdapterModel(this)

    fun shuffle() {
        tileAdapterModel.shuffle()
    }

    fun eraseOneTile() {
        tileAdapterModel.eraseOneTile()
    }

    fun addOneTile() {
        tileAdapterModel.addOneTile()
    }

    fun eraseThreeTile() {
        tileAdapterModel.eraseThreeTile()
    }

    fun addThreeTile() {
        tileAdapterModel.addThreeTile()
    }

    override fun getItemCount(): Int = tileAdapterModel.size()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TileItemBinding.inflate(inflater, parent, false)
        return TileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? TileViewHolder)?.bind(tileAdapterModel.get(position))
    }

    inner class TileViewHolder(private val binding: TileItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tile: Tile) {
            itemView.run {
                binding.numberTextView.text = tile.number.toString()
                binding.backgroundView.setBackgroundColor(tile.color)
            }
        }

    }

}
