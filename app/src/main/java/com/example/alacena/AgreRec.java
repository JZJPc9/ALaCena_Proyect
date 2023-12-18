package com.example.alacena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AgreRec extends AppCompatActivity {

    private ImageButton btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agre_rec);
        btnback = findViewById(R.id.btnBack);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntentAct = new Intent(getApplicationContext(), Menuprin.class);
                getIntentAct.putExtra("selrec","Receta");
                startActivity(getIntentAct);
                finish();
            }
        });
    }

    public void onBackPressed(){
        Intent getIntentAct = new Intent(getApplicationContext(), Menuprin.class);
        getIntentAct.putExtra("selrec","Receta");
        startActivity(getIntentAct);
        finish();
    }
}