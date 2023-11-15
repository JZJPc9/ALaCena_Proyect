package com.example.alacena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Login extends AppCompatActivity {

    ImageButton btnBack;

    Button btnLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //asociacion de los elementos de el fontend con el backend
        btnBack = findViewById(R.id.btnBack);
        btnLog = findViewById(R.id.btnLog);

        //boton back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logica de el boton
                finish();
            }
        });
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logica de el boton
                Intent irmenu = new Intent(getApplicationContext(),Menuprin.class);
                startActivity(irmenu);
            }
        });
    }
}