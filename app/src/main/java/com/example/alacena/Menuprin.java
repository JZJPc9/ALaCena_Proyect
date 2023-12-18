package com.example.alacena;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alacena.DB.DBHelper;
import com.example.alacena.clases.Cifrado;
import com.google.android.material.navigation.NavigationBarView;

import androidx.drawerlayout.widget.DrawerLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Menuprin extends AppCompatActivity {

    //instancias de los fragmenst
    Inventario inv = new Inventario();
    Lista lis = new Lista();
    Recetas rec = new Recetas();
    //barra de navegacion principal
    NavigationBarView navMenu;

    //dubujable y menu botton principal de el menu desplegable
    DrawerLayout drawerLayout;
    ImageButton navhorbutton;

    //text de opciones
    TextView textCuenta,textGrupo,textClose,textNombre,textCorreo,codigo;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprin);
        Intent datback = getIntent();


        //menu desplegable
        drawerLayout = findViewById(R.id.dibujable);
        navhorbutton = findViewById(R.id.navhorbutton);
        //barra de navegacion
        navMenu = findViewById(R.id.barNav);
        //opciones de texto
        textCuenta = findViewById(R.id.textCuenta);
        textGrupo = findViewById(R.id.textGrupo);
        textClose = findViewById(R.id.textClose);

        textNombre = findViewById(R.id.txtnom);
        textCorreo = findViewById(R.id.txtcor);
        codigo = findViewById(R.id.codAcc);

        //saber donde iniciar
        if(datback.getStringExtra("selrec") == null){
            navMenu.setSelectedItemId(R.id.inventario);
            remplaceFragment(inv);
        }else if(datback.getStringExtra("selrec").equals("List")){
            navMenu.setSelectedItemId(R.id.lista);
            remplaceFragment(lis);
        }else {
            navMenu.setSelectedItemId(R.id.recetas);
            remplaceFragment(rec);
        }

        //damos la informacion al front
        Charset character = StandardCharsets.UTF_8;
        File ruta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File sessionCor = new File(ruta,"sessionCor.dat");
        File sessionGru = new File(ruta,"sessionGru.dat");
        String contentc;
        String contentg;

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase dbr = dbHelper.getReadableDatabase();
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        try {
            FileInputStream fisCor = new FileInputStream(sessionCor);
            byte[] bytesCor = new byte[(int) sessionCor.length()];
            fisCor.read(bytesCor);
            contentc = new String(bytesCor,character);
            String inp = Cifrado.descifrar(contentc);

            FileInputStream fisGru = new FileInputStream(sessionGru);
            byte[] bytesGru = new byte[(int) sessionGru.length()];
            fisGru.read(bytesGru);
            contentg = new String(bytesGru,character);


            String colsel[] = {"id_dat"};
            String argsbus[] = {inp};
            Cursor valid = dbr.query("DatosUsuario",colsel,"corr_dat = ?",argsbus,null,null,null);
            valid.moveToFirst();

            int iddat = valid.getInt(valid.getColumnIndexOrThrow("id_dat"));

            Log.i("datos",String.valueOf(iddat));



            String colselU[] = {"nom_usu"};
            String argsselU[] = {String.valueOf(iddat)};
            Cursor datnom = dbr.query("Usuario",colselU,"id_usu = ?",argsselU,null,null,null);
            datnom.moveToFirst();

            String colselG[] = {"cod_gfa"};
            String argsselG[] = {contentg};
            Cursor codec = dbr.query("GrupoFamiliar",colselG,"id_gfa = ?",argsselG,null,null,null);
            codec.moveToFirst();




            codigo.setText(codec.getString(codec.getColumnIndexOrThrow("cod_gfa")));
            textNombre.setText(datnom.getString(datnom.getColumnIndexOrThrow("nom_usu")));
            textCorreo.setText(inp);

            datnom.close();
            codec.close();
            fisGru.close();
            fisCor.close();
            valid.close();
            datnom.close();

        }catch (Exception e){
            Log.e("SESSION",e.toString());
        }



        //escucha de barra de navegacion
        navMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.toString().charAt(0)){
                    case 'L':
                        remplaceFragment(lis);
                        return true;
                    case 'I':
                        remplaceFragment(inv);
                        return true;
                    case 'R':
                        remplaceFragment(rec);
                        return true;
                }
                return false;
            }
        });


        //escucha par EL BOTON que realiza el dibujado de el menu de opciones
        navhorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        //escucha para el click de los textview

        textCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intCuenta = new Intent(getApplicationContext(),ConfigCuenta.class);
                startActivity(intCuenta);
                finish();
            }
        });


        textGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intGrupo = new Intent(getApplicationContext(),ConfigGrupo.class);
                startActivity(intGrupo);
            }
        });


        textClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File ruta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                File sessionBol = new File(ruta, "sessionBol.dat");
                try{
                    FileOutputStream fosbol = new FileOutputStream(sessionBol);
                    fosbol.write("0".getBytes());
                    fosbol.close();
                }catch (Exception e){
                    Log.e("SESSION","error al reasignar");
                }
                Intent mainAct = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainAct);
                finish();
            }
        });




    }


    //metodo para remplazar fragments
    private void remplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framenav,fragment);
        fragmentTransaction.commit();
    }

}