package com.example.qrvlaky;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PridatVlak extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private EditText editTextPoznamka;
    private TextView textView;
    private Button buttonOdeslat;

    private String cislo;
    private String sort;
    ImageButton scan_btn;
    ImageButton vlaky;
    ImageButton neco;
    ImageButton info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridat_vlak);
        vlaky = findViewById(R.id.scanner);
        scan_btn = findViewById(R.id.button3);
        neco = findViewById(R.id.neco);
        info = findViewById(R.id.info);

        vlaky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bintent = new Intent(PridatVlak.this, Vlaky.class);
                startActivity(bintent);

            }
        });
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bintent = new Intent(PridatVlak.this, MainActivity.class);
                startActivity(bintent);

            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bintent = new Intent(PridatVlak.this, Info.class);
                startActivity(bintent);

            }
        });
        neco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bintent = new Intent(PridatVlak.this, Neco.class);
                startActivity(bintent);

            }
        });

        dbHelper = new DatabaseHelper(this);
        editTextPoznamka = findViewById(R.id.editTextPoznamka);
        textView = findViewById(R.id.idvlaku);
        buttonOdeslat = findViewById(R.id.buttonOdeslat);

        // Get the "cislo" passed from MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cislo = extras.getString("cislo");
            sort = extras.getString("sort");
            textView.setText(cislo);
        }

        buttonOdeslat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve current date and time
                String currentDate = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date());

                // Get the text from the EditText field
                String poznamka = editTextPoznamka.getText().toString();

                // Insert data into the database
                insertDataIntoDatabase(cislo, poznamka, currentDate, sort);

                // Finish the activity
                Intent bintent = new Intent(PridatVlak.this, Vlaky.class);
                startActivity(bintent);
                finish();
            }
        });
    }

    private void insertDataIntoDatabase(String cislo, String poznamka, String datum, String sort) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Check if the train number already exists in the database
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " +
                DatabaseHelper.COLUMN_CISLO + "=?", new String[]{cislo});

        if (cursor.getCount() > 0) {
            // Train number already exists, show toast message
            Toast.makeText(this, "Train number already exists", Toast.LENGTH_SHORT).show();
        } else {
            // Train number doesn't exist, insert new data into the database
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_CISLO, cislo);
            values.put(DatabaseHelper.COLUMN_POZNAMKA, poznamka);
            values.put(DatabaseHelper.COLUMN_DATUM, datum);
            values.put(DatabaseHelper.COLUMN_SORT, sort);
            database.insert(DatabaseHelper.TABLE_NAME, null, values);
        }

        cursor.close();
    }
}