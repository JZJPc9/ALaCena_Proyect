package com.example.alacena;

import android.content.ContentValues;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alacena.DB.DBHelper;
import com.example.alacena.clases.IngListCom;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Lista extends Fragment {
    public Lista() {}
    View view;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ImageButton btnAgregIng;
        Button btnAgreInv;
        RecyclerView recyclerLisCom;
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_lista, container, false);

        //asociar
        btnAgreInv = view.findViewById(R.id.btnAgreInv);
        btnAgregIng = view.findViewById(R.id.btnAgregIng);
        recyclerLisCom = view.findViewById(R.id.recyclerLisCom);
        recyclerLisCom.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<IngListCom>inglistcom = new ArrayList<>();
        //obtencion de el archivo de session de grupo
        Charset character = StandardCharsets.UTF_8;
        ContextWrapper ctw = new ContextWrapper(getContext());
        File ruta = ctw.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File sessionGru = new File(ruta,"sessionGru.dat");
        String contentg;

        //creacion de la coneccion de la DB
        DBHelper db = new DBHelper(getContext());
        SQLiteDatabase dbr = db.getReadableDatabase();
        SQLiteDatabase dbw = db.getWritableDatabase();
        try {
            //obtencion de el id del grupo
            FileInputStream fisGru = new FileInputStream(sessionGru);
            byte[] bytesGru = new byte[(int) sessionGru.length()];
            fisGru.read(bytesGru);
            contentg = new String(bytesGru, character);

            //obtenemos el id del inventario a partir de el id de el grupo familiar
            String idlic[] ={"id_lic"};
            String argsellic[] = {contentg};
            Cursor obtlic = dbr.query("ListaCompras",idlic,"id_gfa = ?",argsellic,null,null,null);

            if (obtlic != null && obtlic.moveToFirst()){
                //existe
                String licid = String.valueOf(obtlic.getInt(obtlic.getColumnIndexOrThrow("id_lic")));

                //obtenemos todos los ingredientes del inventario
                String ingli[] = {"id_ingli","nom_ingli","can_ingli","bol_ingli"};
                String argIngli[] ={licid};
                Cursor obtIngslic = dbr.query("IngredienteLista",ingli,"id_lic = ?",argIngli,null,null,null);

                if (obtIngslic != null && obtIngslic.moveToFirst()){
                    //existen registros

                    do{
                        int id = obtIngslic.getInt(obtIngslic.getColumnIndexOrThrow("id_ingli"));
                        String nombre = obtIngslic.getString(obtIngslic.getColumnIndexOrThrow("nom_ingli"));
                        int cantidad = Integer.parseInt(obtIngslic.getString(obtIngslic.getColumnIndexOrThrow("can_ingli")));
                        String check = obtIngslic.getString(obtIngslic.getColumnIndexOrThrow("bol_ingli"));

                        inglistcom.add(new IngListCom(id,nombre,cantidad,check.equals("1")));



                    }while (obtIngslic.moveToNext());



                }else{ //no existe registros de ingredintes
                    Toast.makeText(ctw, getString(R.string.msjToaNoExi), Toast.LENGTH_SHORT).show();
                }



            }else{
                Toast.makeText(ctw, getString(R.string.msjToaNoExi), Toast.LENGTH_SHORT).show();
            }



        }catch (Exception e){
            Log.e("ErrList",e.toString());
        }


        IngLisComAdapter ingLisComAdapter = new IngLisComAdapter(inglistcom);
        recyclerLisCom.setAdapter(ingLisComAdapter);
        ingLisComAdapter.submitList(inglistcom);


        btnAgregIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntentAct = new Intent(getActivity(),AgreList.class);
                startActivity(getIntentAct);
            }
        });



        btnAgreInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //obtencion de el id del grupo
                    FileInputStream fisGru = new FileInputStream(sessionGru);
                    byte[] bytesGru = new byte[(int) sessionGru.length()];
                    fisGru.read(bytesGru);
                    String content = new String(bytesGru, character);

                    //obtenemos el id del inventario a partir de el id de el grupo familiar
                    String idlic[] ={"id_lic"};
                    String argsellic[] = {content};
                    Cursor obtlic = dbr.query("ListaCompras",idlic,"id_gfa = ?",argsellic,null,null,null);

                    if(obtlic != null && obtlic.moveToFirst()){
                        String licid = String.valueOf(obtlic.getInt(obtlic.getColumnIndexOrThrow("id_lic")));

                        //obtenemos todos los ingredientes del inventario
                        String ingli[] = {"id_ingli","nom_ingli","can_ingli","bol_ingli"};
                        String argIngli[] ={licid};
                        Cursor obtIngslic = dbr.query("IngredienteLista",ingli,"id_lic = ?",argIngli,null,null,null);

                        if (obtIngslic != null && obtIngslic.moveToFirst()){

                            do{
                                String checkedadd = obtIngslic.getString(obtIngslic.getColumnIndexOrThrow("bol_ingli"));

                                if (checkedadd.equals("1")){
                                    String obtidinv[] = {"id_inv"};
                                    String argsBusinv[] = {content};
                                    Cursor obtinvid = dbr.query("Inventario",obtidinv,"id_gfa = ?",argsBusinv,null,null,null);

                                    if(obtinvid != null && obtinvid.moveToNext()){


                                        //comprobar si el elemento ya existe
                                        String argsbusIng[] = {obtIngslic.getString(obtIngslic.getColumnIndexOrThrow("nom_ingli")),obtinvid.getString(obtinvid.getColumnIndexOrThrow("id_inv"))};
                                        String colobt[] = {"id_ingin","can_ingin"};
                                        Cursor ingre = dbr.query("IngredienteInventario",colobt,"nom_ingin = ? AND id_inv = ?",argsbusIng,null,null,null);

                                        if (ingre != null && ingre.moveToFirst()){
                                            //actualizar cantidad
                                            ContentValues valupd = new ContentValues();
                                            double suma = Double.valueOf(ingre.getString(ingre.getColumnIndexOrThrow("can_ingin"))) + Double.valueOf(obtIngslic.getString(obtIngslic.getColumnIndexOrThrow("can_ingli"))) ;
                                            valupd.put("can_ingin",suma);
                                            String argsupd[] = {String.valueOf(ingre.getInt(ingre.getColumnIndexOrThrow("id_ingin")))};
                                            dbw.update("IngredienteInventario",valupd,"id_ingin = ?",argsupd);

                                        }else{
                                            //crear registro
                                            String idinv = String.valueOf(obtinvid.getInt(obtinvid.getColumnIndexOrThrow("id_inv")));
                                            ContentValues values = new ContentValues();
                                            values.put("nom_ingin",obtIngslic.getString(obtIngslic.getColumnIndexOrThrow("nom_ingli")));
                                            values.put("can_ingin",obtIngslic.getString(obtIngslic.getColumnIndexOrThrow("can_ingli")));
                                            values.put("id_inv",idinv);
                                            dbw.insert("IngredienteInventario",null,values);

                                            String selcolid[] ={"id_ingin"};
                                            String argsselid[] = {idinv,obtIngslic.getString(obtIngslic.getColumnIndexOrThrow("nom_ingli"))};
                                            Cursor curidingin = dbr.query("IngredienteInventario",selcolid,"id_inv = ? AND nom_ingin = ?",argsselid,null,null,null);
                                            curidingin.moveToFirst();

                                            ContentValues insLot = new ContentValues();
                                            insLot.put("fec_lotin",obtenerFechaActualMas7Dias());
                                            insLot.put("id_ingin",curidingin.getInt(curidingin.getColumnIndexOrThrow("id_ingin")));
                                            dbw.insert("LoteIngrediente",null,insLot);

                                        }


                                    }else {
                                        // crear inventario
                                        ContentValues insInv = new ContentValues();
                                        insInv.put("id_gfa",content);
                                        dbw.insert("Inventario",null,insInv);

                                        Cursor getidinv = dbr.query("Inventario",obtidinv,"id_gfa = ?",argsBusinv,null,null,null);
                                        getidinv.moveToFirst();

                                        String idinv = String.valueOf(getidinv.getInt(getidinv.getColumnIndexOrThrow("id_inv")));

                                        ContentValues values = new ContentValues();
                                        values.put("nom_ingin",obtIngslic.getString(obtIngslic.getColumnIndexOrThrow("nom_ingli")));
                                        values.put("can_ingin",obtIngslic.getString(obtIngslic.getColumnIndexOrThrow("can_ingli")));
                                        values.put("id_inv",idinv);
                                        dbw.insert("IngredienteInventario",null,values);

                                        String selcolid[] ={"id_ingin"};
                                        String argsselid[] = {idinv,obtIngslic.getString(obtIngslic.getColumnIndexOrThrow("nom_ingli"))};
                                        Cursor curidingin = dbr.query("IngredienteInventario",selcolid,"id_inv = ? AND nom_ingin = ?",argsselid,null,null,null);
                                        curidingin.moveToFirst();

                                        ContentValues insLot = new ContentValues();
                                        insLot.put("fec_lotin",obtenerFechaActualMas7Dias());
                                        insLot.put("id_ingin",curidingin.getInt(curidingin.getColumnIndexOrThrow("id_ingin")));
                                        dbw.insert("LoteIngrediente",null,insLot);





                                    }

                                }

                            }while (obtIngslic.moveToNext());

                        }

                    }


                }catch (Exception e){
                    Log.e("ErrList", e.toString());
                }
            }
        });




        //retornar view
        return view;
    }

    public static String obtenerFechaActualMas7Dias() {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        Date fechaActual = calendar.getTime();

        // Sumar 7 días a la fecha actual
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date fechaMas7Dias = calendar.getTime();

        // Formatear la fecha en el formato "yyyy-MM-dd"
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = formatoFecha.format(fechaMas7Dias);

        return fechaFormateada;
    }


}