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
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AgreList extends AppCompatActivity {

    ImageButton btnback;
    Button guardad;
    EditText ingreCan;
    TextInputLayout ingNom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agre_list);
        btnback = findViewById(R.id.btnBack);
        ingreCan = findViewById(R.id.IngreCan);
        ingNom = findViewById(R.id.IngNom);
        guardad = findViewById(R.id.guardar);




        guardad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //obtencion de el archivo de session de grupo
                Charset character = StandardCharsets.UTF_8;
                File ruta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                File sessionGru = new File(ruta,"sessionGru.dat");
                String contentg;

                //creacion de la coneccion de la DB
                DBHelper db = new DBHelper(getApplicationContext());
                SQLiteDatabase dbr = db.getReadableDatabase();
                SQLiteDatabase dbw = db.getWritableDatabase();

                try {

                    //obtencion de el id del grupo
                    FileInputStream fisGru = new FileInputStream(sessionGru);
                    byte[] bytesGru = new byte[(int) sessionGru.length()];
                    fisGru.read(bytesGru);
                    contentg = new String(bytesGru,character);

                    //obtencion de el "id_lic" para la validacion de existencia de la ListaCompras
                    String selidlic[] = {"id_lic"};
                    String argslic[] = {contentg};
                    Cursor cridlic = dbr.query("ListaCompras",selidlic,"id_gfa = ?",argslic,null,null,null);

                    //condicional de existencia ListaCompras
                    if(cridlic != null && cridlic.moveToFirst()){
                        String idlic = String.valueOf(cridlic.getInt(cridlic.getColumnIndexOrThrow("id_lic")));

                        //si existe el inventario buscar existencia del ingrediente
                        String selinglic[] = {"id_ingli",};
                        String argsinglic[] = {idlic,ingNom.getEditText().getText().toString()};
                        Cursor valing = dbr.query("IngredienteLista",selinglic,"id_lic = ? AND nom_ingli = ?",argsinglic,null,null,null);

                        //condicional de existencia del ingredinete
                        if (valing != null && valing.moveToFirst()){
                            //si ya existe avisar al usuario
                            Toast.makeText(getApplicationContext(),getString(R.string.msjToaExiInv),Toast.LENGTH_SHORT).show();
                        }else{
                            //ingresar los datos principales
                            ContentValues ininglic = new ContentValues();
                            ininglic.put("nom_ingli",ingNom.getEditText().getText().toString());
                            ininglic.put("can_ingli",Integer.parseInt(ingreCan.getText().toString()));
                            ininglic.put("bol_ingli","0");
                            ininglic.put("id_lic",idlic);
                            dbw.insert("IngredienteLista",null,ininglic);
                            Intent backmenu = new Intent(getApplicationContext(), Menuprin.class);
                            backmenu.putExtra("selrec","List");
                            startActivity(backmenu);
                            finish();
                        }
                        valing.close();
                    }else{
                        //crear el Lista de compras
                        ContentValues inslic = new ContentValues();
                        inslic.put("id_gfa",contentg);
                        dbw.insert("ListaCompras",null,inslic);

                        //obtener el id_inv a partir de el id_gfa
                        Cursor crinlic = dbr.query("ListaCompras",selidlic,"id_gfa = ?",argslic,null,null,null);
                        crinlic.moveToFirst();
                        int idinv = crinlic.getInt(cridlic.getColumnIndexOrThrow("id_lic"));

                        //ingresar los datos principales
                        ContentValues ininglic = new ContentValues();
                        ininglic.put("nom_ingli",ingNom.getEditText().getText().toString());
                        ininglic.put("can_ingli",Integer.parseInt(ingreCan.getText().toString()));
                        ininglic.put("bol_ingli","0");
                        ininglic.put("id_lic",idinv);
                        dbw.insert("IngredienteLista",null,ininglic);

                        Intent backmenu = new Intent(getApplicationContext(), Menuprin.class);
                        backmenu.putExtra("selrec","List");
                        startActivity(backmenu);
                        finish();
                    }


                }catch (Exception e){
                    Log.e("ErrList",e.toString());
                }







                dbr.close();
                dbw.close();
            }
        });















        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntentAct = new Intent(getApplicationContext(), Menuprin.class);
                getIntentAct.putExtra("selrec","List");
                startActivity(getIntentAct);
                finish();
            }
        });
    }

    public void onBackPressed(){
        Intent getIntentAct = new Intent(getApplicationContext(), Menuprin.class);
        getIntentAct.putExtra("selrec","List");
        startActivity(getIntentAct);
        finish();
    }
}