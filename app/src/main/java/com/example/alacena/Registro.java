package com.example.alacena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alacena.DB.DBHelper;
import com.example.alacena.clases.Cifrado;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                    SQLiteDatabase dbW = dbHelper.getWritableDatabase();
                    SQLiteDatabase dbR = dbHelper.getReadableDatabase();
                    //insertar datos de usuario
                    String corr = correo.getText().toString();
                    String conEnc = Cifrado.cifrar(contrasena.getText().toString());

                    //validacion de existencia
                    


                    ContentValues valuesDU = new ContentValues();
                    valuesDU.put("corr_dat",corr);
                    valuesDU.put("pass_dat",conEnc);
                    dbW.insert("DatosUsuario",null,valuesDU);
                    //lectura para saber id datauser
                    String obtDat[] = {"id_dat"};
                    String argDat[] = {corr};
                    Cursor iddat = dbR.query("DatosUsuario",obtDat,"corr_dat = ?",argDat,null,null,null);
                    //insercion en usuario
                    if (iddat != null && iddat.moveToFirst()){
                        ContentValues valuesU = new ContentValues();
                        valuesU.put("nom_usu",nombre.getEditText().toString());
                        valuesU.put("app_usu",apellidop.getEditText().toString());
                        valuesU.put("apm_usu",apellidom.getEditText().toString());
                        valuesU.put("id_dat",iddat.getInt(iddat.getColumnIndexOrThrow("id_dat")));
                        dbW.insert("Usuario",null,valuesU);

                        //creacion de la sesion parciol
                        File ruta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                        File sessionBol = new File(ruta, "sessionBol.dat");
                        File sessionCor = new File(ruta, "sessionCor.dat");
                        try {
                            FileOutputStream fosBol = new FileOutputStream(sessionBol);
                            fosBol.write("1".getBytes());

                            FileOutputStream fosCor = new FileOutputStream(sessionCor);
                            fosCor.write(Cifrado.cifrar(corr).getBytes());

                            fosBol.close();
                            fosCor.close();
                        }catch (FileNotFoundException e) {
                            Log.e("ErrArchiv", String.valueOf(e));
                        } catch (IOException e) {
                            Log.e("ErrArchiv", String.valueOf(e));
                        }



                    }
                    iddat.close();
                    dbW.close();
                    dbR.close();

                    Intent irEle = new Intent(getApplicationContext(), ElecRol.class);
                    startActivity(irEle);

                } else {
                    // Muestra un mensaje de error apropiado según la condición que no se cumple
                    if (!contrasenasIguales()) {
                        Toast.makeText(Registro.this, getString(R.string.msjToaConReg), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Registro.this, getString(R.string.msjToCamReg), Toast.LENGTH_SHORT).show();
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
                String nombreText = nombre.getEditText().toString();
                String apellidopText = apellidop.getEditText().toString();
                String apellidomText = apellidom.getEditText().toString();
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