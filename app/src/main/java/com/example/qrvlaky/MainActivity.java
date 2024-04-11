package com.example.qrvlaky;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    //barvy: #e13e2b; #4b5666; #0497e9; #FFFFFF
    ImageButton scan_btn;
    ImageButton vlaky;
    ImageButton neco;
    ImageButton info;

    TextView textView;
    TextView textView2;
    private String balls;
    private void gay(View view){
        Intent intent = new Intent(MainActivity.this, Vlaky.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vlaky = findViewById(R.id.scanner);
        scan_btn = findViewById(R.id.button3);
        neco = findViewById(R.id.neco);
        info = findViewById(R.id.info);
        //textView = findViewById(R.id.text);
        //textView2 = findViewById(R.id.text2);

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setPrompt("Scan a QR Code");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();

            }
        });

        vlaky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Vlaky.class);
                intent.putExtra("pridat", "false");
                startActivity(intent);

            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bintent = new Intent(MainActivity.this, Info.class);
                startActivity(bintent);

            }
        });

        neco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Neco.class);

                startActivity(intent);

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null){
            String contents = intentResult.getContents();
            if(contents != null){
                boolean isFound = intentResult.getContents().contains("CZ-CD_94_54_1_971");
                boolean isFound2 = intentResult.getContents().contains("CZ-CD_94_54_1_471");
                boolean isFound3 = intentResult.getContents().contains("CZ-CD_94_54_1_071");

                //textView.setText(intentResult.getContents());
                int l = intentResult.getContents().length();
                if(l >= 15) {
                    if ( isFound || isFound2 || isFound3) {
                        String[] cislo = intentResult.getContents().split("(?!^)");
                        int[] id = new int[]{Integer.parseInt(cislo[21])* 100, Integer.parseInt(cislo[22])* 10 , Integer.parseInt(cislo[23])* 1 , Integer.parseInt(cislo[25])* 100, Integer.parseInt(cislo[26])* 10, Integer.parseInt(cislo[27])*1, Integer.parseInt(cislo[29])*1};
                        int[] sort = new int[]{Integer.parseInt(cislo[25]), Integer.parseInt(cislo[26]), Integer.parseInt(cislo[27])};
                        int s;
                        int c;
                        int cc;
                        int ccc;
                        c = id[0] + id[1]  + id[2];
                        cc = id[3] + id[4] + id[5];
                        ccc = id[6];
                        s =  sort[1]*100 + sort[2]*10;
                        if (Integer.parseInt(cislo[23]) == 9){s = s + 0;}
                        else if(Integer.parseInt(cislo[23]) == 0){s = s + 1;}
                        else{s = s + 2;}
                        String C = new Integer(c).toString();
                        String CC = new Integer(cc).toString();
                        String CCC = new Integer(ccc).toString();
                        if(Integer.parseInt(cislo[25]) == 0){
                            if(Integer.parseInt(cislo[26]) == 0){balls = C + " 00" + CC + "-" + CCC;}
                            else{balls = C + " 0" + CC + "-" + CCC;}
                        }

                        else{balls = C + " " + CC + "-" + CCC;}
                        String ballsort = new Integer(s).toString();
                        //textView2.setText(balls);
                        Intent bintent = new Intent(MainActivity.this, PridatVlak.class);
                        bintent.putExtra("cislo", balls);
                        bintent.putExtra("sort", ballsort);
                        startActivity(bintent);


                    }
                    else {
                        //textView2.setText("QR kód není z vlaku");
                        Toast.makeText(MainActivity.this, "QR kód není ze City Elephantu", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //textView2.setText("QR kód není z vlaku");
                    Toast.makeText(MainActivity.this, "QR kód není z vlaku", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}