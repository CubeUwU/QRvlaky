package com.example.qrvlaky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


// tato aktivita má pouze textview s informacema o aplikaci, tlačítka fungují stejně jako v MainActivity.
public class Info extends AppCompatActivity {
    ImageButton scan_btn;
    ImageButton vlaky;
    ImageButton neco;
    ImageButton info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        vlaky = findViewById(R.id.scanner);
        scan_btn = findViewById(R.id.button3);
        neco = findViewById(R.id.neco);
        info = findViewById(R.id.info);
        vlaky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bintent = new Intent(Info.this, Vlaky.class);
                startActivity(bintent);

            }
        });
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bintent = new Intent(Info.this, MainActivity.class);
                startActivity(bintent);

            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bintent = new Intent(Info.this, Info.class);
                startActivity(bintent);

            }
        });
        neco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bintent = new Intent(Info.this, Neco.class);
                startActivity(bintent);

            }
        });
    }
}