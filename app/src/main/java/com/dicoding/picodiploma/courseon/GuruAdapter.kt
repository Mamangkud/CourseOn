package com.dicoding.picodiploma.courseon

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_cardview_guru.view.*
import kotlinx.android.synthetic.main.item_matpel.view.*

class GuruAdapter(options: FirestoreRecyclerOptions<GuruModel>) : FirestoreRecyclerAdapter<GuruModel, GuruAdapter.GuruAdapterVH>(options){
    internal var guru = arrayListOf<GuruModel>()

//    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bind(guru: GuruModel) {
//            with(itemView) {
//                tv_nama_guru.text = guru.name.toString()
//                tv_waktu_pesanan.text =  guru.waktu.toString()
//                tv_tanggal_pesanan.text = guru.tanggal.toString()
//
//                itemView.setOnClickListener {
//                    Toast.makeText(
//                        itemView.context,
//                        "Kamu memilih " + guru.name.toString(),
//                        Toast.LENGTH_SHORT
//                    ).show()
////                    val moveDetail = Intent(itemView.context, MatpelActivity::class.java)
////                    context.startActivity(moveDetail)
//                }
//            }
//        }
//    }

    class GuruAdapterVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nama = itemView.tv_nama_guru
        var tanggal = itemView.tv_tanggal_pesanan
        var waktu = itemView.tv_waktu_pesanan
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuruAdapterVH {
        return GuruAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.item_cardview_guru, parent, false))
//        var view: View =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_cardview_guru, parent, false)
//        return CardViewHolder(view)
    }

//    override fun getItemCount(): Int = guruList.size

//    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
//        holder.bind(guruList[position])
//    }

    override fun onBindViewHolder(
        holder: GuruAdapter.GuruAdapterVH,
        position: Int,
        model: GuruModel
    ) {
        holder.nama.text = "Nama: " + model.nama
        holder.tanggal.text = "Tanggal: " + model.tanggal
        holder.waktu.text = "Waktu: " + model.waktu
    }

}