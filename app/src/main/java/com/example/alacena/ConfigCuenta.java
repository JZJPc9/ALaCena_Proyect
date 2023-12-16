package com.example.alacena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ConfigCuenta extends AppCompatActivity {

    ImageButton btnback;

    TextInputLayout nombre, apellidop, apellidom, con, confcon;
    Button camnom, camcor, camcon;
    EditText cor;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_cuenta);
        btnback = findViewById(R.id.btnBack);
        camnom = findViewById(R.id.btnConfCamDat);
        camcor = findViewById(R.id.btnConfCamCor);
        camnom = findViewById(R.id.btnConfCamCon);
        cor = findViewById(R.id.TxtCor);






        //cambio de correo
        camcor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevoCor = cor.getText().toString();
                DBHelper db = new DBHelper(getApplicationContext());
                SQLiteDatabase dbr = db.getReadableDatabase();
                SQLiteDatabase dbw = db.getWritableDatabase();
                Charset character = StandardCharsets.UTF_8;
                File ruta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                File sessionCor = new File(ruta,"sessionCor.dat");
                String contentc;
                //OBTENCION DEL CORREO
                try {

                    FileInputStream fisCor = new FileInputStream(sessionCor);
                    byte[] bytesCor = new byte[(int) sessionCor.length()];
                    fisCor.read(bytesCor);
                    contentc = new String(bytesCor,character);

                    //obtencion de id_dat a traves de la session
                    String datusuid[]={"id_dat"};
                    String corDes = Cifrado.descifrar(contentc);
                    String args []= {corDes};
                    Cursor iddatusu = dbr.query("DatosUsuario",datusuid,"corr_dat = ?",args,null,null,null);
                    if (!nuevoCor.isEmpty() && nuevoCor.contains("@")) { // Verifica si el correo contiene el carácter '@'
                        ContentValues valuesDU = new ContentValues();
                        valuesDU.put("corr_dat",nuevoCor);
                        //lo pasamos a string
                        iddatusu.moveToFirst();
                        String iddat = String.valueOf(iddatusu.getInt(iddatusu.getColumnIndexOrThrow("id_dat")));
                        String argsupd[] = {iddat};
                        //vamos a insertarlo
                        dbw.update("DatosUsuario",valuesDU,"id_dat = ? ",argsupd);
                        //wamos a cifrar el nuevo correo
                        String corcif = Cifrado.cifrar(nuevoCor);
                        //ponemos el nuevo correo
                        FileOutputStream fosCor = new FileOutputStream(sessionCor);
                        fosCor.write(corcif.getBytes());
                        fosCor.close();
                        fisCor.close();
                        db.close();
                        dbw.close();
                        dbr.close();
                    } else {
                        Toast.makeText(ConfigCuenta.this, "@string/msjToaCorReg", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.e("SESSION","error al obtener archivo");
                }
            }
        });









        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });

    }
}