package com.example.qrvlaky;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class VlakEditTrue extends AppCompatActivity {
    private EditText editTextPoznamka;
    private Button buttonSave;
    private int trainId;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlak_edit_true);

        editTextPoznamka = findViewById(R.id.editTextPoznamka);
        buttonSave = findViewById(R.id.buttonSave);

        dbHelper = new DatabaseHelper(this);

        // Retrieve the train ID from intent
        trainId = getIntent().getIntExtra("trainId", -1);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePoznamka();
            }
        });
    }

    private void updatePoznamka() {
        String newPoznamka = editTextPoznamka.getText().toString().trim();
        if (!newPoznamka.isEmpty()) {
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_POZNAMKA, newPoznamka);

            String selection = DatabaseHelper.COLUMN_ID + " = ?";
            String[] selectionArgs = {String.valueOf(trainId)};

            int rowsAffected = database.update(DatabaseHelper.TABLE_NAME, values, selection, selectionArgs);

            if (rowsAffected > 0) {

                Intent bintent = new Intent(VlakEditTrue.this, Vlaky.class);
                startActivity(bintent);
                finish();
            } else {

            }
        }
    }
}
