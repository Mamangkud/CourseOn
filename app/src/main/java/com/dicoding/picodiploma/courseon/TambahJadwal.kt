package com.dicoding.picodiploma.courseon

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_konfirmasi_tambah_jadwal.*
import kotlinx.android.synthetic.main.tambah_jadwal.*
import java.util.*

class TambahJadwal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val c = Calendar.getInstance()

        et_pilihtanggal.setOnClickListener() {
            val datePickerDialog = DatePickerDialog.OnDateSetListener{datePicker, mYear, mMonth, mDay ->
                c.set(Calendar.YEAR, mYear)
                c.set(Calendar.MONTH,mMonth)
                c.set(Calendar.DAY_OF_MONTH,mDay)
                et_pilihtanggal.setText("$mDay/$mMonth/$mYear")
            }
            DatePickerDialog(this, datePickerDialog, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }

        et_pilihwaktu.setOnClickListener() {
            val timePickerDialog = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)
                et_pilihwaktu.setText("$hour:$minute")
            }
            TimePickerDialog(this, timePickerDialog, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()
        }

        btn_TambahJadwal.setOnClickListener {
            val b = Bundle()
            val teks_to_send = et_pilihtanggal.text.toString()
            val teks_to_send2= et_pilihwaktu.text.toString()
            b.putString("SENT_TEXT", teks_to_send)
            b.putString("SENT_TEXT2", teks_to_send2)
            val dialog = FragmentKonfirmasiTambahJadwal()
            dialog.setArguments(b)
            dialog.show(supportFragmentManager,"customDialog")
        }

        //val fragment = KonfirmasiTambahJadwal()

        //fragment.SubmitButton.setOnClickListener{
        //saveData()
        //}
    }

//    private fun saveData() {
//        val tanggal = et_pilihtanggal.text.toString().trim()
//        val waktu = et_pilihwaktu.text.toString()
//
//        if (tanggal.isEmpty()){
//            et_pilihtanggal.error = "Silahkan pilih tanggal terlebih dahulu"
//        }
//
//        if(waktu.isEmpty()){
//            et_pilihwaktu.error = "Silahkan pilih waktu terlebih dahulu"
//        }

    //if(waktu == TambahJadwal(waktu)){
    //et_pilihwaktu.error = "Waktu sudah tersedia"
    //}

    //val ref = FirebaseDatabase.getInstance().getReference("Tambah Jadwal")
    //val jadwalId = ref.push().key

    //val tambahJadwal = TambahJadwal(jadwalId, tanggal, waktu)

    //if(jadwalId != null){
    //ref.child(jadwalId).setValue(tambahJadwal).addOnCompleteListener{
    //Toast.makeText(applicationContext, "Jadwal berhasil ditambahkan", Toast.LENGTH_SHORT).show()
    //}
    //}
}

//}