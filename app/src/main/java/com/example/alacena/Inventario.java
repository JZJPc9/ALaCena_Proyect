package com.example.alacena;

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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alacena.DB.DBHelper;
import com.example.alacena.clases.IngInv;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class Inventario extends Fragment {
    public Inventario() {}

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        RecyclerView recyclerInv;
        ImageButton btnAgreInv;
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_inventario, container, false);

        //asociar
        btnAgreInv = view.findViewById(R.id.btnAgregInv);
        recyclerInv = view.findViewById(R.id.recyclerInv);
        recyclerInv.setLayoutManager(new LinearLayoutManager(getContext()));



/**************************************************************************************************/
        //objeto Ingrediente de inventario
        ArrayList<IngInv>ingInv = new ArrayList<>();

        //obtencion de el archivo de session de grupo
        Charset character = StandardCharsets.UTF_8;
        ContextWrapper ctw = new ContextWrapper(getContext());
        File ruta = ctw.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File sessionGru = new File(ruta,"sessionGru.dat");
        String contentg;

        //creacion de la coneccion de la DB
        DBHelper db = new DBHelper(getContext());
        SQLiteDatabase dbr = db.getReadableDatabase();

        try {
            //obtencion de el id del grupo
            FileInputStream fisGru = new FileInputStream(sessionGru);
            byte[] bytesGru = new byte[(int) sessionGru.length()];
            fisGru.read(bytesGru);
            contentg = new String(bytesGru,character);




            //obtenemos el id del inventario a partir de el id de el grupo familiar
            String idinv[] ={"id_inv"};
            String argselinv[] = {contentg};
            Cursor obtInv = dbr.query("Inventario",idinv,"id_gfa = ?",argselinv,null,null,null);



            if(obtInv != null && obtInv.moveToFirst()){
                //existe el inventario

                String invid = String.valueOf(obtInv.getInt(obtInv.getColumnIndexOrThrow("id_inv")));

                //obtenemos todos los ingredientes del inventario
                String ingIn[] = {"id_ingin","nom_ingin","can_ingin"};
                String argIngIn[] ={invid};
                Cursor obtIngsInv = dbr.query("IngredienteInventario",ingIn,"id_inv = ?",argIngIn,null,null,null);



                if (obtIngsInv != null && obtIngsInv.moveToFirst()){
                    //existe registros de ingrediente
                    do{
                        //obtenemos el id del ingredinte para obtener su fecha de expiracion
                        String idingin = String.valueOf(obtIngsInv.getInt(obtIngsInv.getColumnIndexOrThrow("id_ingin")));
                        String fechcol[] ={"fec_lotin"};
                        String argfechsel[] = {idingin};
                        Cursor obtLotIng = dbr.query("LoteIngrediente",fechcol,"id_ingin = ?",argfechsel,null,null,null);
                        obtLotIng.moveToFirst();
                        int id = obtIngsInv.getInt(obtIngsInv.getColumnIndexOrThrow("id_ingin"));
                        String nombre = obtIngsInv.getString(obtIngsInv.getColumnIndexOrThrow("nom_ingin"));
                        String fecha = obtLotIng.getString(obtLotIng.getColumnIndexOrThrow("fec_lotin"));
                        int cantidad = Integer.parseInt(obtIngsInv.getString(obtIngsInv.getColumnIndexOrThrow("can_ingin")));

                        ingInv.add(new IngInv(id,nombre,fecha,cantidad));


                    }while (obtIngsInv.moveToNext());

                }else{
                    //no existe registros de ingredintes
                    Toast.makeText(ctw, getString(R.string.msjToaNoExi), Toast.LENGTH_SHORT).show();
                }

                obtIngsInv.close();




            }else{
                //no existe el inventario
                Toast.makeText(ctw, getString(R.string.msjToaNoExi), Toast.LENGTH_SHORT).show();
            }

            obtInv.close();



        }catch (Exception e){
            Log.e("Error_obt_inv",e.toString());
        }


/**************************************************************************************************/
        IngInvAdapter ingInvAdapter = new IngInvAdapter();



        recyclerInv.setAdapter(ingInvAdapter);
        ingInvAdapter.submitList(ingInv);







        btnAgreInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getIntentAct = new Intent(getActivity(), AgreInv.class);
                startActivity(getIntentAct);

            }
        });
        return view;
    }
}