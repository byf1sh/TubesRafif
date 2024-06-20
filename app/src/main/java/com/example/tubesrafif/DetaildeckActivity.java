package com.example.tubesrafif;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DetaildeckActivity extends AppCompatActivity {

    private TextView textViewId, textViewNamaDeck, textViewHarga;
    // Anda bisa menambahkan ImageView untuk urlFoto jika diperlukan

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detaildeck);

        textViewNamaDeck = findViewById(R.id.h2_dbd);
        textViewHarga = findViewById(R.id.harga);
        // Inisialisasi ImageView jika diperlukan

        // Mengambil data dari intent
        Intent intent = getIntent();
        if (intent != null) {
            String namaDeck = intent.getStringExtra("title");
            String harga = intent.getStringExtra("harga");

            // Mengatur data ke TextView
            textViewNamaDeck.setText(namaDeck);
            textViewHarga.setText(harga);
            // Anda bisa menggunakan Picasso atau Glide untuk memuat gambar ke ImageView
        }
    }
}
