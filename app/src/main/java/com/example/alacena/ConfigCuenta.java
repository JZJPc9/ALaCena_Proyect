package com.example.alacena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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

    TextInputLayout nombre, apellidop, apellidom, conact, con, confcon;
    Button camnom, camcor, camcon;
    EditText cor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_cuenta);
        btnback = findViewById(R.id.btnBack);

        camnom = findViewById(R.id.btnConfCamDat);
        camcor = findViewById(R.id.btnConfCamCor);
        camcon = findViewById(R.id.btnConfCamCon);

        nombre = findViewById(R.id.editnom);
        apellidop = findViewById(R.id.editpat);
        apellidom = findViewById(R.id.editmat);
        conact = findViewById(R.id.ingcon);
        con = findViewById(R.id.newcon);
        confcon = findViewById(R.id.confnewcon);
        cor = findViewById(R.id.TxtCor);

        //dar los valores a los campos
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
            contentc = new String(bytesCor, character);
            String corDes = Cifrado.descifrar(contentc);
            cor.setText(corDes);

            //obtencion de id_dat a traves de la session
            String datusuid[] = {"id_dat"};
            String args[] = {corDes};
            Cursor iddatusu = dbr.query("DatosUsuario", datusuid, "corr_dat = ?", args, null, null, null);
            iddatusu.moveToFirst();
            String iddat = String.valueOf(iddatusu.getInt(iddatusu.getColumnIndexOrThrow("id_dat")));

            String obtcolums[] = {"nom_usu","app_usu","apm_usu"};
            String argsobt[] = {iddat};
            Cursor nombres = dbr.query("Usuario",obtcolums,"id_dat = ?",argsobt,null,null,null);
            nombres.moveToFirst();

            nombre.getEditText().setText(nombres.getString(nombres.getColumnIndexOrThrow("nom_usu")));
            apellidop.getEditText().setText(nombres.getString(nombres.getColumnIndexOrThrow("app_usu")));
            apellidom.getEditText().setText(nombres.getString(nombres.getColumnIndexOrThrow("apm_usu")));


        }catch (Exception e){
            Log.e("ErrCofigC",e.toString());
        }


        camnom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper db = new DBHelper(getApplicationContext());
                SQLiteDatabase dbr = db.getReadableDatabase();
                SQLiteDatabase dbw = db.getWritableDatabase();
                Charset character = StandardCharsets.UTF_8;
                File ruta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                File sessionNom= new File(ruta,"sessionCor.dat");
                String contentc;

                try {
                    FileInputStream fisNom = new FileInputStream(sessionNom);
                    byte[] bytesCor = new byte[(int) sessionNom.length()];
                    fisNom.read(bytesCor);
                    contentc = new String(bytesCor,character);
                    String datusui[]={"id_dat"};
                    String nuevoNombre = nombre.getEditText().getText().toString();
                    String nuevoApellidoPaterno = apellidop.getEditText().getText().toString();
                    String nuevoApellidoMaterno = apellidom.getEditText().getText().toString();
                    contentc = new String(bytesCor,character);
                    String args[] = {Cifrado.descifrar(contentc)};
                    Cursor iddatusu = dbr.query("DatosUsuario",datusui," corr_dat = ?",args,null,null,null);

                    if (!nuevoNombre.isEmpty() && !nuevoApellidoPaterno.isEmpty() && !nuevoApellidoMaterno.isEmpty()) {
                        iddatusu.moveToFirst();
                        String iddat = String.valueOf(iddatusu.getInt(iddatusu.getColumnIndexOrThrow("id_dat")));

                        ContentValues val = new ContentValues();
                        val.put("nom_usu",nuevoNombre);
                        val.put("app_usu",nuevoApellidoPaterno);
                        val.put("apm_usu",nuevoApellidoMaterno);
                        String argsupd[] = {iddat};
                        dbw.update("Usuario",val,"id_dat = ?",argsupd);
                        Intent intent = new Intent(getApplicationContext(), Menuprin.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(ConfigCuenta.this,getString(R.string.msjToaTodosReg) , Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Log.e("SESSION","error al obtener archivo");
                }

            }
        });


        camcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(getApplicationContext());
                SQLiteDatabase dbr = db.getReadableDatabase();
                SQLiteDatabase dbw = db.getWritableDatabase();
                Charset character = StandardCharsets.UTF_8;
                File ruta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                File sessionCon = new File(ruta,"sessionCor.dat");
                String nuevacontrasena = con.getEditText().getText().toString();
                String confirmarContrasena = confcon.getEditText().getText().toString();
                String contentc;
                //volvemos a obtener el id
                try {
                    FileInputStream fisCor = new FileInputStream(sessionCon);
                    byte[] bytesCor = new byte[(int) sessionCon.length()];
                    fisCor.read(bytesCor);
                    contentc = new String(bytesCor, character);
                    String datusuid[]={"id_dat"};
                    String conCif = Cifrado.descifrar(contentc);
                    String args []= {conCif};
                    Cursor iddatusu = dbr.query("DatosUsuario",datusuid,"corr_dat = ?",args,null,null,null);
                    iddatusu.moveToFirst();
                    String iddat = String.valueOf(iddatusu.getInt(iddatusu.getColumnIndexOrThrow("id_dat")));
                    if (!nuevacontrasena.isEmpty() && !confirmarContrasena.isEmpty() && nuevacontrasena.equals(confirmarContrasena)) {

                        String corr[] = {"corr_dat"};
                        String argsconf[] = {iddat,Cifrado.cifrar(conact.getEditText().getText().toString())};
                        Cursor confconact = dbr.query("DatosUsuario",corr,"id_dat = ? AND pass_dat = ?",argsconf,null,null,null);

                        if(confconact != null && confconact.moveToFirst()) {

                            String argsupd[] = {iddat};
                            //lo insertamos
                            ContentValues values = new ContentValues();
                            values.put("pass_dat", Cifrado.cifrar(nuevacontrasena));
                            dbw.update("DatosUsuario", values, "id_dat = ? ", argsupd);
                            //guardamos el archivo
                            FileOutputStream fosCon = new FileOutputStream(sessionCon);
                            fosCon.write(conCif.getBytes());
                            fosCon.close();
                            fisCor.close();
                            db.close();
                            dbw.close();
                            dbr.close();
                            db.close();
                            Intent intent = new Intent(getApplicationContext(), Menuprin.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),getString(R.string.msjToaConInv),Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ConfigCuenta.this, getString(R.string.msjToaConReg), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.e("SESSION","error al obtener archivo");
                }

            }
        });




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
                        Intent intent = new Intent(getApplicationContext(), Menuprin.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ConfigCuenta.this, getString(R.string.msjToaNoValidCorr), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.e("SESSION","error al obtener archivo");
                }
            }
        });









        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Menuprin.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Menuprin.class);
        startActivity(intent);
        finish();
    }
}