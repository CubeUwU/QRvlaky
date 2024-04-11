package com.example.qrvlaky;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Vlaky extends AppCompatActivity implements TrainAdapter.OnItemClickListener {
        private RecyclerView recyclerView;
        private TrainAdapter adapter;
        private List<Train> trainList;
        private DatabaseHelper dbHelper;
        ImageButton scan_btn;
        ImageButton vlaky;
        ImageButton neco;
        ImageButton info;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_vlaky);


                vlaky = findViewById(R.id.scanner);
                scan_btn = findViewById(R.id.button3);
                neco = findViewById(R.id.neco);
                info = findViewById(R.id.info);

                vlaky.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent bintent = new Intent(Vlaky.this, Vlaky.class);
                                startActivity(bintent);

                        }
                });
                scan_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent bintent = new Intent(Vlaky.this, MainActivity.class);
                                startActivity(bintent);

                        }
                });
                info.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent bintent = new Intent(Vlaky.this, Info.class);
                                startActivity(bintent);

                        }
                });
                neco.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent bintent = new Intent(Vlaky.this, Neco.class);
                                startActivity(bintent);

                        }
                });




                dbHelper = new DatabaseHelper(this);

                recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                trainList = new ArrayList<>();
                adapter = new TrainAdapter(trainList, this);

                recyclerView.setAdapter(adapter);

                // Load data from the database and populate trainList
                loadDataFromDatabase();
        }

        private void loadDataFromDatabase() {
                SQLiteDatabase database = dbHelper.getReadableDatabase();

                String[] projection = {
                        DatabaseHelper.COLUMN_ID,
                        DatabaseHelper.COLUMN_CISLO,
                        DatabaseHelper.COLUMN_POZNAMKA,
                        DatabaseHelper.COLUMN_DATUM,
                        DatabaseHelper.COLUMN_SORT
                };

                Cursor cursor = database.query(
                        DatabaseHelper.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null
                );

                while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                        String cislo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CISLO));
                        String poznamka = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_POZNAMKA));
                        String datum = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATUM));
                        String sort = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SORT));

                        Train train = new Train(id, cislo, poznamka, datum, sort);
                        trainList.add(train);
                }
                cursor.close();
                adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemClick(int trainId) {
                String arse = Integer.toString(trainId);
                Intent intent = new Intent(this, VlakEdit.class);
                intent.putExtra("trainId", trainId);
                //intent.putExtra("cislo", arse);
                //intent.putExtra("sort", "blembis");
                startActivity(intent);
        }
}
