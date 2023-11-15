package com.example.alacena;
/*
COMENTARIO PARA CONFIRMAR QUE ESTE BIEN VENIDO
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ElecRol extends AppCompatActivity {


    Button btnIng;
    Button btnCre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elec_rol);
        //asociacion de el frontend y backend
        btnIng = findViewById(R.id.ingresar);
        btnCre = findViewById(R.id.crear);

        btnCre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logica de el boton
                Intent irmenu = new Intent(getApplicationContext(),Menuprin.class);
                startActivity(irmenu);
            }
        });

        btnIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logica de el boton
                Intent irmenu = new Intent(getApplicationContext(),Menuprin.class);
                startActivity(irmenu);
            }
        });

    }
}