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

// tato aktivita přidává vlaky do databáze

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


        // tlacitka fungují jako v MainActivity
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

        // Cislo a cislo pro sortovaní passnute z MainActivity je uloženo do stringu
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cislo = extras.getString("cislo");
            sort = extras.getString("sort");
            textView.setText(cislo);
        }


        // tato fuunkce vezme imput od uživatele a získá monentální datum a čas a pošle je do databáze pomocí funkce pro insert
        buttonOdeslat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentDate = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date());


                String poznamka = editTextPoznamka.getText().toString();


                insertDataIntoDatabase(cislo, poznamka, currentDate, sort);


                Intent bintent = new Intent(PridatVlak.this, Vlaky.class);
                startActivity(bintent);
                finish();
            }
        });
    }

    private void insertDataIntoDatabase(String cislo, String poznamka, String datum, String sort) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // kontrola zda už nebylo toto číslo vlaku uloženo
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " +
                DatabaseHelper.COLUMN_CISLO + "=?", new String[]{cislo});

        if (cursor.getCount() > 0) {

            Toast.makeText(this, "Train number already exists", Toast.LENGTH_SHORT).show();
        } else {
            // číslo vlaku je nové a toto insertne data do databáze
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