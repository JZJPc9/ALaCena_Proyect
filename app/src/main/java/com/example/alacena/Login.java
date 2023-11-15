package com.example.alacena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Login extends AppCompatActivity {

    ImageButton btnBack;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //asociacion de los elementos de el fontend con el backend
        btnBack = findViewById(R.id.btnBack);
        btnLogin = findViewById(R.id.btnLogin);
        //boton back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logica de el boton

                /*Preguntas al Profesor si hacer de nuevo el intent es una buena practica por que
                es como si se agregara a la pila multiples pantallas de la misma actividad.

                Intent back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back);*/

                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irmenu = new Intent(getApplicationContext(),Menuprin.class);
                startActivity(irmenu);
            }
        });

    }
}