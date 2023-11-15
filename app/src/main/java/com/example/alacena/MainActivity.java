package com.example.alacena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnLog;
    Button btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLog = findViewById(R.id.btnLog);
        btnReg = findViewById(R.id.btnReg);

        //listener y evento de el boton
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Crear la logica de el boton
                Intent irreg = new Intent(getApplicationContext(), Registro.class);
                startActivity(irreg);

            }
        });
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Crear la logica de el boton
                Intent irlog = new Intent(getApplicationContext(),Login.class);
                startActivity(irlog);
            }
        });

    }
}