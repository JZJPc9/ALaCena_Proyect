package com.example.alacena;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView textCuenta,textGrupo,textClose,textNombre,textCorreo;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprin);
        remplaceFragment(inv);
        //menu desplegable
        drawerLayout = findViewById(R.id.dibujable);
        navhorbutton = findViewById(R.id.navhorbutton);
        //barra de navegacion
        navMenu = findViewById(R.id.barNav);
        //opciones de texto
        textCuenta = findViewById(R.id.textCuenta);
        textGrupo = findViewById(R.id.textGrupo);
        textClose = findViewById(R.id.textClose);
        textNombre = findViewById(R.id.textNombre);
        textCorreo = findViewById(R.id.textCorreo);

        //damos la informacion al front
        Charset character = StandardCharsets.UTF_8;
        File ruta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File sessionCor = new File(ruta,"sessionCor.dat");
        String contentc;

        try {
            FileInputStream fisCor = new FileInputStream(sessionCor);
            byte[] bytesCor = new byte[(int) sessionCor.length()];
            fisCor.read(bytesCor);
            contentc = new String(bytesCor,character);

            String inp = Cifrado.descifrar(contentc);

            Toast.makeText(getApplicationContext(),inp,Toast.LENGTH_SHORT).show();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textCorreo.setText(inp);
                }
            });


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