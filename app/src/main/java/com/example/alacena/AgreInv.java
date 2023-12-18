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
import com.example.alacena.clases.IngRec;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AgreInv extends AppCompatActivity {
    ImageButton btnback;

    EditText ingreCan,diaexp,mesexp,yeaexp;
    TextInputLayout ingNom;

    Button guardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agre_inv);
        btnback = findViewById(R.id.btnBackInv);
        ingreCan = findViewById(R.id.IngreCan);
        diaexp = findViewById(R.id.diaexp);
        mesexp = findViewById(R.id.mesexp);
        yeaexp = findViewById(R.id.yeaexp);
        ingNom = findViewById(R.id.IngNom);
        guardar = findViewById(R.id.guardar);


        guardar.setOnClickListener(new View.OnClickListener() {
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



                /* **************************************************************************** */
                try{

                    //obtencion de el id del grupo
                    FileInputStream fisGru = new FileInputStream(sessionGru);
                    byte[] bytesGru = new byte[(int) sessionGru.length()];
                    fisGru.read(bytesGru);
                    contentg = new String(bytesGru,character);

                    //obtencion de el "id_inv" para la validacion de existencia de el Inventario
                    String selidinv[] = {"id_inv"};
                    String argsinv[] = {contentg};
                    Cursor cridinv = dbr.query("Inventario",selidinv,"id_gfa = ?",argsinv,null,null,null);

                    //condicional de existencia de inventario
                    if (cridinv != null && cridinv.moveToFirst()){
                        String idinv = String.valueOf(cridinv.getInt(cridinv.getColumnIndexOrThrow("id_inv")));

                        //si existe el inventario buscar existencia del ingrediente
                        String selingin[] = {"id_ingin",};
                        String argsingin[] = {idinv,ingNom.getEditText().getText().toString()};
                        Cursor valing = dbr.query("IngredienteInventario",selingin,"id_inv = ? AND nom_ingin = ?",argsingin,null,null,null);

                        //condicional de existencia del ingrediente
                        if (valing != null && valing.moveToFirst()){
                            //si ya existe avisar al usuario
                            Toast.makeText(getApplicationContext(),getString(R.string.msjToaExiInv),Toast.LENGTH_SHORT).show();
                        }else{
                            //ingresar los datos principales
                            ContentValues ininginv = new ContentValues();
                            ininginv.put("nom_ingin",ingNom.getEditText().getText().toString());
                            ininginv.put("can_ingin",Integer.parseInt(ingreCan.getText().toString()));
                            ininginv.put("id_inv",idinv);
                            dbw.insert("IngredienteInventario",null,ininginv);

                            //obtencion de el id del ingrediente
                            Cursor idingin = dbr.query("IngredienteInventario",selingin,"id_inv = ? AND nom_ingin = ?",argsingin,null,null,null);
                            idingin.moveToFirst();
                            int idining = idingin.getInt(idingin.getColumnIndexOrThrow("id_ingin"));

                            //creacion de lote de o fecha de exp
                            ContentValues inslotin = new ContentValues();
                            String fecha = yeaexp.getText().toString() + "-" + mesexp.getText().toString() + "-" + diaexp.getText().toString();
                            inslotin.put("fec_lotin",fecha);
                            inslotin.put("id_ingin",idining);
                            dbw.insert("LoteIngrediente",null,inslotin);


                            idingin.close();
                            Intent getIntentAct = new Intent(getApplicationContext(), Menuprin.class);
                            startActivity(getIntentAct);
                            finish();


                        }
                        valing.close();
                    }else{
                        //si no existe el inventario crearlo
                        ContentValues insinv = new ContentValues();
                        insinv.put("id_gfa",contentg);
                        dbw.insert("Inventario",null,insinv);

                        //obtener el id_inv a partir de el id_gfa
                        Cursor crinvid = dbr.query("Inventario",selidinv,"id_gfa = ?",argsinv,null,null,null);
                        crinvid.moveToFirst();
                        int idinv = crinvid.getInt(cridinv.getColumnIndexOrThrow("id_inv"));

                        //ingresar los datos principales
                        ContentValues ininginv = new ContentValues();
                        ininginv.put("nom_ingin",ingNom.getEditText().getText().toString());
                        ininginv.put("can_ingin",Integer.parseInt(ingreCan.getText().toString()));
                        ininginv.put("id_inv",idinv);
                        dbw.insert("IngredienteInventario",null,ininginv);

                        //obtencion de el id del ingrediente
                        String selingin[] = {"id_ingin"};
                        String argsingin[] = {String.valueOf(idinv),ingNom.getEditText().getText().toString()};
                        Cursor idingin = dbr.query("IngredienteInventario",selingin,"id_inv = ? AND nom_ingin = ?",argsingin,null,null,null);
                        idingin.moveToFirst();
                        int idining = idingin.getInt(idingin.getColumnIndexOrThrow("id_ingin"));

                        //creacion de lote de o fecha de exp
                        ContentValues inslotin = new ContentValues();
                        String fecha = yeaexp.getText().toString() + "-" + mesexp.getText().toString() + "-" + diaexp.getText().toString();
                        inslotin.put("fec_lotin",fecha);
                        inslotin.put("id_ingin",idining);
                        dbw.insert("LoteIngrediente",null,inslotin);


                        idingin.close();
                        Intent getIntentAct = new Intent(getApplicationContext(), Menuprin.class);
                        startActivity(getIntentAct);
                        finish();

                    }
                    cridinv.close();

                }catch (Exception e){
                    Log.e("ErrorInsert",e.toString());
                }
                dbr.close();
                dbw.close();


                /* **************************************************************************** */

            }
        });


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntentAct = new Intent(getApplicationContext(), Menuprin.class);
                startActivity(getIntentAct);
                finish();
            }
        });
    }

    public void onBackPressed(){
        Intent getIntentAct = new Intent(getApplicationContext(), Menuprin.class);
        startActivity(getIntentAct);
        finish();
    }


}