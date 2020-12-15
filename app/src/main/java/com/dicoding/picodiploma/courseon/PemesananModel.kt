package com.dicoding.picodiploma.courseon

import android.os.Parcelable
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PemesananModel(
    val pesananId: String?,
    val alamat_pemesanan: String?,
    val matpel: String,
    val nama: String?,
    val nama_pemesan: String?,
    val status: String?,
    val tanggal: String?,
    val tipe_pemesanan: String?,
    val waktu: String?,
    val priority: String?
) : Parcelable {
    constructor() : this(
        "", "", "", "",
        "", "", "", "", "", ""
    )
}