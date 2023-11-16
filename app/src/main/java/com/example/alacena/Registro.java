package com.example.alacena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;

public class Registro extends AppCompatActivity {

    EditText correo,contrasena,confContrasena;
    TextInputLayout nombre, apellidop, apellidom;
    ImageButton btnBack;
    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //asociacion de los elementos de el fontend con el backend
        btnBack = findViewById(R.id.btnBack);
        btnReg = findViewById(R.id.Registro);
        nombre = findViewById(R.id.textInputName);
        apellidop = findViewById(R.id.textInputPat);
        apellidom = findViewById(R.id.textInputMat);
        contrasena = findViewById(R.id.editTextPassword);
        confContrasena = findViewById(R.id.editTextConfPassword);
        correo =findViewById(R.id.editTextEmailAddress);


        //boton back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logica de el boton
                finish();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica del botón de registro
                if (camposLlenos() && isValidEmail(correo.getText().toString()) && contrasenasIguales()) {
                    // Todos los campos requeridos están llenos, el correo tiene un formato válido,
                    // y las contraseñas coinciden, puedes avanzar a la siguiente actividad
                    Intent elerol = new Intent(getApplicationContext(), ElecRol.class);
                    startActivity(elerol);
                } else {
                    // Muestra un mensaje de error apropiado según la condición que no se cumple
                    if (!contrasenasIguales()) {
                        Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Registro.this, "Completa todos los campos requeridos y verifica el formato del correo", Toast.LENGTH_SHORT).show();
                    }
                }
            }



            private boolean contrasenasIguales() {
                String contrasenaText = contrasena.getText().toString();
                String concontrasenaText = confContrasena.getText().toString();

                // Verifica si las contraseñas coinciden
                return contrasenaText.equals(concontrasenaText);
            }

            private boolean camposLlenos() {
                String nombreText = nombre.getEditText().getText().toString();
                String apellidopText = apellidop.getEditText().getText().toString();
                String apellidomText = apellidom.getEditText().getText().toString();
                String contrasenaText = contrasena.getText().toString();

                String concontrasenaText = confContrasena.getText().toString();
                String correoText = correo.getText().toString();

                // Verifica si los campos requeridos están llenos
                return !TextUtils.isEmpty(nombreText) &&
                        !TextUtils.isEmpty(apellidopText) &&
                        !TextUtils.isEmpty(apellidomText) &&
                        !TextUtils.isEmpty(contrasenaText) &&
                        !TextUtils.isEmpty(concontrasenaText) &&
                        !TextUtils.isEmpty(correoText);
            }

            private boolean isValidEmail(String email) {
                // Puedes implementar aquí una validación más completa para el formato de correo
                return email.contains("@");
            }
        });

    }
}