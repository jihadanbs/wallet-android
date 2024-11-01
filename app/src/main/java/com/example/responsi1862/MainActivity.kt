package com.example.responsi1862

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up dialing feature on textHallo2
        val textHallo2 = findViewById<TextView>(R.id.textHallo2)
        textHallo2.setOnClickListener {
            val phoneNumber = "088215178312"
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(dialIntent)
        }

        // Intent to open TopUpActivity
        val menu1 = findViewById<TextView>(R.id.menu1)
        menu1.setOnClickListener {
            val intent = Intent(this, TopUpActivity::class.java)
            startActivity(intent)
        }

        // Intent to open OutcomeActivity
        val menu2 = findViewById<TextView>(R.id.menu2)
        menu2.setOnClickListener {
            val intent = Intent(this, OutcomeActivity::class.java)
            startActivity(intent)
        }

        // Intent to open HistoryActivity
        val menu3 = findViewById<TextView>(R.id.menu3)
        menu3.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}
