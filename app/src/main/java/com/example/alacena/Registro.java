package com.example.alacena;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import android.os.Bundle;

public class Registro extends AppCompatActivity {

    ImageButton btnBack;
    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //asociacion de los elementos de el fontend con el backend
        btnBack = findViewById(R.id.btnBack);
        btnReg = findViewById(R.id.Registro);


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

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logica de el boton
                Intent elerol = new Intent(getApplicationContext(), ElecRol.class);
                startActivity(elerol);

            }
        });

    }
}