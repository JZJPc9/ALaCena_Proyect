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
import android.widget.Toast;

import com.example.alacena.DB.DBHelper;
import com.example.alacena.clases.Cifrado;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class ElecRol extends AppCompatActivity {

    //botones
    Button btnIng;
    Button btnCre;
    TextInputLayout txtcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elec_rol);
        //asociacion de el frontend y backend
        btnIng = findViewById(R.id.ingresar);
        btnCre = findViewById(R.id.crear);
        txtcode = findViewById(R.id.textiputcode);





        btnCre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logica de el boton
                DBHelper db = new DBHelper(getApplicationContext());
                SQLiteDatabase dbr = db.getReadableDatabase();
                SQLiteDatabase dbw = db.getWritableDatabase();

                String code;
                boolean b = true;
                String busqueda[] = {"id_gfa"};

                while(b) {
                    code = genereador();
                    String argsbusq[] = {code};
                    Cursor comprovacion = dbr.query("GrupoFamiliar", busqueda, "cod_gfa = ?", argsbusq, null, null, null);
                    if(comprovacion != null && comprovacion.moveToFirst()){
                        Log.i("GENGRUP","codigo ya existente");
                    }else{
                        b = false;

                        ContentValues datgrup = new ContentValues();
                        datgrup.put("cod_gfa",code);
                        datgrup.put("nom_gfa","namepred");
                        dbw.insert("GrupoFamiliar",null,datgrup);

                        String idgrupo[] = {"id_gfa"};
                        String argsid[] = {code};
                        Cursor cidgrup = dbr.query("GrupoFamiliar",idgrupo,"cod_gfa = ?",argsid,null,null,null);
                        cidgrup.moveToFirst();
                        ContentValues validgrup = new ContentValues();
                        validgrup.put("id_gfa",cidgrup.getInt(cidgrup.getColumnIndexOrThrow("id_gfa")));

                        //OBTENCION DEL CORREO
                        Charset character = StandardCharsets.UTF_8;
                        File ruta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                        File sessionCor = new File(ruta,"sessionCor.dat");
                        File sessionGru = new File(ruta,"sessionGru.dat");
                        String contentc;
                        try {
                            FileOutputStream fosGru = new FileOutputStream(sessionGru);
                            FileInputStream fisCor = new FileInputStream(sessionCor);
                            byte[] bytesCor = new byte[(int) sessionCor.length()];
                            fisCor.read(bytesCor);
                            contentc = new String(bytesCor,character);

                            //obtencion de id_dat a traves de la session
                            String datusuid[]={"id_dat"};
                            String argsselect[]={Cifrado.descifrar(contentc)};
                            Cursor iddatusu = dbr.query("DatosUsuario",datusuid,"corr_dat = ?",argsselect,null,null,null);
                            iddatusu.moveToFirst();
                            int iddat = iddatusu.getInt(iddatusu.getColumnIndexOrThrow("id_dat"));

                            //actualiza los datos de usuario
                            String argsupd[] = {String.valueOf(iddat)};
                            dbw.update("Usuario",validgrup,"id_dat = ?",argsupd);
                            String idg = String.valueOf(cidgrup.getInt(cidgrup.getColumnIndexOrThrow("id_gfa")));
                            fosGru.write(idg.getBytes());


                            iddatusu.close();
                            fisCor.close();
                            fosGru.close();
                        }catch (Exception e){
                            Log.e("SESSION","error al obtener archivo");
                        }
                    }
                    comprovacion.close();
                }

                dbr.close();
                dbw.close();
                Intent irmenu = new Intent(getApplicationContext(),Menuprin.class);
                startActivity(irmenu);
                finish();
            }
        });

        btnIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logica de el boton
                DBHelper db = new DBHelper(getApplicationContext());
                SQLiteDatabase dbr = db.getReadableDatabase();
                SQLiteDatabase dbw = db.getWritableDatabase();
                String code = txtcode.getEditText().getText().toString();


                String columnobt[] = {"id_gfa"};
                String argsselect[] = {code};
                Cursor busqueda = dbr.query("GrupoFamiliar",columnobt,"cod_gfa = ?",argsselect,null,null,null);

                if(busqueda != null && busqueda.moveToFirst()){

                    Charset character = StandardCharsets.UTF_8;
                    File ruta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                    File sessionCor = new File(ruta,"sessionCor.dat");
                    File sessionGru = new File(ruta,"sessionGru.dat");
                    String contentc;
                    try{

                        FileInputStream fisCor = new FileInputStream(sessionCor);
                        FileOutputStream fosgru = new FileOutputStream(sessionGru);
                        byte[] bytesCor = new byte[(int) sessionCor.length()];
                        fisCor.read(bytesCor);
                        contentc = new String(bytesCor,character);

                        String coldat[] = {"id_dat"};
                        String argsdat[] = {Cifrado.descifrar(contentc)};
                        Cursor iddato = dbr.query("DatosUsuario",coldat,"corr_dat = ?",argsdat,null,null,null);
                        iddato.moveToFirst();
                        String iddat = String.valueOf(iddato.getInt(iddato.getColumnIndexOrThrow("id_dat")));


                        String id = String.valueOf(busqueda.getInt(busqueda.getColumnIndexOrThrow("id_gfa")));
                        ContentValues inst = new ContentValues();
                        inst.put("id_gfa",id);
                        String argsupd[] = {iddat};
                        dbw.update("Usuario",inst,"id_dat = ?",argsupd);

                        fosgru.write(id.getBytes());

                        fisCor.close();
                        fosgru.close();


                    }catch (Exception e){

                    }
                    Intent irmenu = new Intent(getApplicationContext(),Menuprin.class);
                    startActivity(irmenu);
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(),getString(R.string.msjToaNotGru),Toast.LENGTH_SHORT).show();
                }


                busqueda.close();
                dbr.close();
                dbw.close();
            }
        });

    }

    public String genereador(){
        final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(CARACTERES.charAt(rnd.nextInt(CARACTERES.length())));
        }
        return sb.toString();
    }


}