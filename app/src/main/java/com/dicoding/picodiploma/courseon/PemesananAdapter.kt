package com.dicoding.picodiploma.courseon

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.item_cardview_pesanan.view.*


class PemesananAdapter(
    options: FirestoreRecyclerOptions<PemesananModel>,
    val mCtx: Context
) :
    FirestoreRecyclerAdapter<PemesananModel, PemesananAdapter.PemesananAdapterVH>(options) {
//    internal var guru = arrayListOf<GuruModel>()
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val builder = AlertDialog.Builder(mCtx)
    val inflater = LayoutInflater.from(mCtx)
    val db = Firebase.firestore
    var pesananId = ""

    class PemesananAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nama = itemView.tv_nama_guru_log
        var matpel = itemView.tv_mata_pelajaran_log
        var tanggal = itemView.tv_tanggal_log
        var waktu = itemView.tv_waktu_log
        var status = itemView.tv_status_log
        var btnCancel = itemView.ib_cancel_log
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PemesananAdapterVH {
        return PemesananAdapterVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cardview_pesanan, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: PemesananAdapter.PemesananAdapterVH,
        position: Int,
        model: PemesananModel
    ) {
        holder.nama.text = "Nama: " + model.nama
        holder.matpel.text = "Matpel : " + model.matpel
        holder.tanggal.text = "Tanggal: " + model.tanggal
        holder.waktu.text = "Waktu: " + model.waktu
        holder.status.text = model.status

//        pesananId = getSnapshots().getSnapshot(position).getId();
        pesananId = db.collection("pemesanan").id
//        alamat_pemesanan: String?,
//        val matpel: String,
//        val nama: String?,
//        val nama_pemesan: String?,
//        val status: String?,
//        val tanggal: String?,
//        val tipe_pemesanan: String?
//        val waktu: String?

        holder.btnCancel.setOnClickListener {
            showDialog(model)
        }
    }

    private fun showDialog(pemesanan: PemesananModel) {



        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Konfirmasi Pembatalan")
        builder.setPositiveButton("Ya") { dialogInterface, id ->
//            db.collection("pemesanan").whereEqualTo("status", "Menunggu Konfirmasi")
//                .set(hashMapOf("status" to "Dibatalkan"))
            Toast.makeText(mCtx, pesananId, Toast.LENGTH_LONG).show()
            view.context.startActivity(Intent(mCtx, LihatLogActivity::class.java))
        }
        builder.setNegativeButton("Tidak") { dialogInterface, id ->
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }


}