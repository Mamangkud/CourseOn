package com.dicoding.picodiploma.courseon

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatpelModel(
    val photo: Int? = 0,
    val name: String?
): Parcelable