package com.dicoding.picodiploma.courseon

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PemesananModel(
    val alamat_pemesanan: String?,
    val matpel: String,
    val nama: String?,
    val nama_pemesan: String?,
    val status: String?,
    val tanggal: String?,
    val tipe_pemesanan: String?,
    val waktu: String?
) : Parcelable