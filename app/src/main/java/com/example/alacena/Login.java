package com.example.alacena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Login extends AppCompatActivity {



    EditText correo,contrasena;
    ImageButton btnBack;
    Button btnLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Asociación de los elementos de frontend con el backend
        btnBack = findViewById(R.id.btnBack);
        btnLog = findViewById(R.id.btnLog);
        contrasena=findViewById(R.id.editTextPassword);
        correo = findViewById(R.id.editTextEmailAddress);

        // Botón back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica del botón
                finish();
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica del botón
                if (!isValidEmail(correo.getText().toString())) {
                    // Mostrar Toast si no es un formato válido de correo
                    Toast.makeText(Login.this, "Formato de correo inválido", Toast.LENGTH_SHORT).show();
                } else {
                    // Enviar datos y cambiar de actividad
                    Intent irmenu = new Intent(getApplicationContext(), Menuprin.class);
                    startActivity(irmenu);
                }
            }
        });

        // Agrega un TextWatcher al EditText
        correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // No se necesita acción antes de cambiar el texto
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // No se necesita acción durante el cambio del texto
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Verifica si el texto contiene un arroba
                if (!editable.toString().contains("@")) {
                    // Si no contiene un arroba, agrégalo al final
                }
            }
        });
    }

    // Función para verificar el formato de correo electrónico
    private boolean isValidEmail(String email) {
        // Puedes implementar aquí una validación más completa para el formato de correo
        return email.contains("@");
    }
}
