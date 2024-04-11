package com.example.qrvlaky;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VlakEdit extends AppCompatActivity {
    private TextView textViewCislo;
    private TextView textViewDatum;
    private TextView textViewPoznamka;
    private Button buttonDelete;
    private Button buttonEdit;
    private int trainId;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlak_edit);

        textViewCislo = findViewById(R.id.textViewCislo);
        textViewDatum = findViewById(R.id.textViewDatum);
        textViewPoznamka = findViewById(R.id.textViewPoznamka);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonEdit = findViewById(R.id.buttonEdit);

        dbHelper = new DatabaseHelper(this);

        // Retrieve the train ID from intent
        trainId = getIntent().getIntExtra("trainId", -1);

        // Load train details from the database
        loadTrainDetails();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTrain();
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VlakEdit.this, VlakEditTrue.class);
                intent.putExtra("trainId", trainId);
                startActivity(intent);
            }
        });
    }

    private void loadTrainDetails() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_CISLO,
                DatabaseHelper.COLUMN_DATUM,
                DatabaseHelper.COLUMN_POZNAMKA
        };

        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(trainId)};

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String cislo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CISLO));
            String datum = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATUM));
            String poznamka = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_POZNAMKA));

            textViewCislo.setText(cislo);
            textViewDatum.setText(datum);
            textViewPoznamka.setText(poznamka);
        }

        cursor.close();
    }

    private void deleteTrain() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(trainId)};
        database.delete(DatabaseHelper.TABLE_NAME, selection, selectionArgs);
        Intent bintent = new Intent(VlakEdit.this, Vlaky.class);
        startActivity(bintent);
        finish();
    }
}
