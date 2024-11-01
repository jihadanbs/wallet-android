package com.example.responsi1862

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TopUpActivity : AppCompatActivity() {
    private lateinit var editTextDesc: EditText
    private lateinit var editTextTopUp: EditText
    private lateinit var buttonSave: Button
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        // Key untuk menyimpan saldo di SharedPreferences
        private const val PREFS_NAME = "UserBalancePrefs"
        private const val BALANCE_KEY = "user_balance"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up)

        // Inisialisasi tampilan
        editTextDesc = findViewById(R.id.editTextDesc)
        editTextTopUp = findViewById(R.id.editTextTopUp)
        buttonSave = findViewById(R.id.buttonSave)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Menyiapkan listener tombol
        buttonSave.setOnClickListener { topUpBalance() }
    }

    private fun topUpBalance() {
        val description = editTextDesc.text.toString().trim()
        val topUpAmountStr = editTextTopUp.text.toString().trim()

        if (topUpAmountStr.isEmpty()) {
            Toast.makeText(this, "Silakan masukkan jumlah top-up", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // Mengonversi jumlah top-up ke integer
            val topUpAmount = topUpAmountStr.toInt()

            // Mengambil saldo saat ini
            val currentBalance = sharedPreferences.getInt(BALANCE_KEY, 0)

            // Mengupdate saldo
            val newBalance = currentBalance + topUpAmount

            // Menyimpan saldo yang telah diperbarui
            with(sharedPreferences.edit()) {
                putInt(BALANCE_KEY, newBalance)
                apply()
            }

            // Menampilkan pesan konfirmasi
            Toast.makeText(this, "Saldo diperbarui. Saldo baru: Rp $newBalance", Toast.LENGTH_SHORT).show()

            // Mengosongkan kolom input setelah disimpan
            editTextDesc.setText("")
            editTextTopUp.setText("")

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Jumlah top-up tidak valid", Toast.LENGTH_SHORT).show()
        }
    }
}
