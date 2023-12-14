package com.example.alacena;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alacena.DB.DBHelper;
import com.example.alacena.clases.Cifrado;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Login extends AppCompatActivity {


    EditText correo, contrasena;
    ImageButton btnBack;
    Button btnLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Asociación de los elementos de frontend con el backend
        btnBack = findViewById(R.id.btnBack);
        btnLog = findViewById(R.id.btnLog);
        contrasena = findViewById(R.id.editTextPassword);
        correo = findViewById(R.id.editTextEmailAddress);

        // Botón back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica del botón
                Intent irmain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(irmain);
                finish();
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica del botón
                if (!isValidEmail(correo.getText().toString())) {
                    // Mostrar Toast si no es un formato válido de correo
                    Toast.makeText(Login.this, getString(R.string.msjToaCorLog) , Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(contrasena.getText().toString())) {
                    // Mostrar Toast si no es un formato válido de contraseña
                    Toast.makeText(Login.this, getString(R.string.msjToaConLog), Toast.LENGTH_SHORT).show();
                } else {
/*********************************************************Vlidar exixtencia********************************************************/
                    try {
                        DBHelper dbHelper = new DBHelper(getApplicationContext());
                        SQLiteDatabase dbR = dbHelper.getReadableDatabase();

                        String datObt[] = {"id_dat","pass_dat"};
                        String argsSelDat[] = { correo.getText().toString()};
                        Cursor corrs = dbR.query("DatosUsuario",datObt,"corr_dat = ? " ,argsSelDat,null,null,null);

                        if(corrs != null && corrs.moveToFirst()){

                            String contra = Cifrado.descifrar(corrs.getString(corrs.getColumnIndexOrThrow("pass_dat")));
                            if(contra.equals(contrasena.getText().toString())){

                                String usuObt[] = {"id_rol","id_gfa"};
                                String argSelUsu[] ={corrs.getString(corrs.getColumnIndexOrThrow("id_dat"))};
                                Cursor usua = dbR.query("Usuario",usuObt,"id_dat = ?",argSelUsu,null,null,null);

                                if(usua != null && usua.moveToFirst()) {

                                    String grupFam = String.valueOf(usua.getInt(usua.getColumnIndexOrThrow("id_gfa")));
                                    Log.i("GrupoFamiliar",grupFam);
                                    String cifcorr = Cifrado.cifrar(correo.getText().toString());

                                    File ruta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                                    File sessionBol = new File(ruta, "sessionBol.dat");
                                    File sessionCor = new File(ruta, "sessionCor.dat");
                                    File sessionGru = new File(ruta, "sessionGru.dat");

                                    try {
                                        FileOutputStream fosBol = new FileOutputStream(sessionBol);
                                        fosBol.write("1".getBytes());

                                        FileOutputStream fosCor = new FileOutputStream(sessionCor);
                                        fosCor.write(cifcorr.getBytes());

                                        FileOutputStream fosGru = new FileOutputStream(sessionGru);
                                        fosGru.write(grupFam.getBytes());

                                        fosBol.close();
                                        fosCor.close();
                                        fosGru.close();

                                    } catch (FileNotFoundException e) {
                                        Log.e("ErrArchivo", String.valueOf(e));
                                    } catch (IOException e) {
                                        Log.e("ErrArchivo", String.valueOf(e));
                                    }
                                }else{
                                }
                                usua.close();
                                Intent irmenu = new Intent(getApplicationContext(), Menuprin.class);
                                startActivity(irmenu);
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(),getString(R.string.msjToaNonLog),Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(),getString(R.string.msjToaNonLog),Toast.LENGTH_SHORT).show();
                        }
                        corrs.close();
                        dbR.close();
                    }catch (SQLException e){
                        Log.e("SQLITE",String.valueOf(e));
                    }
/*********************************************************fin valid exixtencia********************************************************/
                }
            }
        });
    }

    // Función para verificar el formato de correo electrónico
    private boolean isValidEmail(String email) {
        // Puedes implementar aquí una validación más completa para el formato de correo
        return email.contains("@");
    }

    // Función para verificar el formato de contraseña
    private boolean isValidPassword(String password) {
        // Puedes implementar aquí una validación más compleja para el formato de contraseña
        return password.length() >= 8;
    }
}
