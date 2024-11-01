package com.example.responsi1862

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OutcomeActivity : AppCompatActivity() {
    private lateinit var editTextDesc: EditText
    private lateinit var editTextOutcome: EditText
    private lateinit var buttonSave: Button
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREFS_NAME = "UserBalancePrefs"
        private const val BALANCE_KEY = "user_balance"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outcome)

        // Inisialisasi tampilan
        editTextDesc = findViewById(R.id.editTextDesc)
        editTextOutcome = findViewById(R.id.editTextTopUp)
        buttonSave = findViewById(R.id.buttonSave)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Menyiapkan listener untuk tombol
        buttonSave.setOnClickListener { processOutcome() }
    }

    private fun processOutcome() {
        val description = editTextDesc.text.toString().trim()
        val outcomeAmountStr = editTextOutcome.text.toString().trim()

        if (outcomeAmountStr.isEmpty()) {
            Toast.makeText(this, "Silakan masukkan jumlah pengeluaran", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // Mengonversi jumlah pengeluaran ke integer
            val outcomeAmount = outcomeAmountStr.toInt()

            // Mengambil saldo saat ini
            val currentBalance = sharedPreferences.getInt(BALANCE_KEY, 0)

            // Memeriksa apakah saldo cukup
            if (outcomeAmount > currentBalance) {
                Toast.makeText(this, "Saldo tidak cukup", Toast.LENGTH_SHORT).show()
                return
            }

            // Mengurangi saldo
            val newBalance = currentBalance - outcomeAmount

            // Menyimpan saldo yang diperbarui
            with(sharedPreferences.edit()) {
                putInt(BALANCE_KEY, newBalance)
                apply()
            }

            // Menampilkan pesan konfirmasi
            Toast.makeText(this, "Pengeluaran tersimpan. Saldo baru: Rp $newBalance", Toast.LENGTH_SHORT).show()

            // Mengosongkan kolom input setelah disimpan
            editTextDesc.setText("")
            editTextOutcome.setText("")

            // Tambahkan outcome ke RecyclerView di MainActivity (menggunakan intent)
            setResult(RESULT_OK)
            finish()

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Jumlah pengeluaran tidak valid", Toast.LENGTH_SHORT).show()
        }
    }
}
