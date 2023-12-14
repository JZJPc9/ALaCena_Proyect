package com.example.alacena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alacena.DB.DBHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    Button btnLog;
    Button btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLog = findViewById(R.id.btnLog);
        btnReg = findViewById(R.id.btnReg);



/*****************************************validador de sesion******************************************/
        //obtencion de sesion (archivo donde se guarda la sesion)
        File ruta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File sessionBol = new File(ruta,"sessionBol.dat");
        File sessionCor = new File(ruta,"sessionCor.dat");
        File sessionGru = new File(ruta, "sessionGru.dat");

        try {
            if (sessionBol.exists() && sessionCor.exists() && sessionGru.exists()){

                Charset character = StandardCharsets.UTF_8;

                //archivp, id de grupo familiar
                FileInputStream fisGru = new FileInputStream(sessionGru);
                byte[] bytesGru = new byte[(int) sessionGru.length()];
                fisGru.read(bytesGru);
                String contentg = new String(bytesGru,character);

               //archivo session valida (True, False)
                FileInputStream fisBol = new FileInputStream(sessionBol);
                byte[] bytesBol = new byte[(int) sessionBol.length()];
                fisBol.read(bytesBol);
                String contents = new String(bytesBol,character);

                //archivo, correo de session
                FileInputStream fisCor = new FileInputStream(sessionCor);
                byte[] bytesCor = new byte[(int) sessionCor.length()];
                fisCor.read(bytesCor);
                String contentc = new String(bytesBol,character);


                if(contentg.equals("1") && !contentc.isEmpty() && !contents.isEmpty()){
                    if(contents.equals("1")){
                        Log.i("SESSION","session validad");
                        Intent intentmen = new Intent(getApplicationContext(), Menuprin.class);
                        startActivity(intentmen);
                        finish();
                    }else{
                        Log.i("SESSION", "sesion invalida");
                    }
                }else if(contents.isEmpty()){
                    Log.i("SESSION", "Algun archivo esta vacio");
                    Intent intentele = new Intent(getApplicationContext(), ElecRol.class);
                    startActivity(intentele);
                    finish();
                }else{
                    Log.i("SESSION", "algun otro archivo esta vacio");
                }


            }else if(sessionBol.exists() && sessionCor.exists() && !sessionGru.exists()){
                Log.e("SESSION","Archivo de grupo no encontrado");
                Intent intentele = new Intent(getApplicationContext(), ElecRol.class);
                startActivity(intentele);
            }else{
                Log.i("SESSION","Archivos no encontrados");
            }

        }catch (IOException e){
            Log.e("File","Error en consulta: " + String.valueOf(e));
        }

/***********************************Find del validador de session**************************************/



        //listener y evento de el boton
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Crear la logica de el boton
                Intent irreg = new Intent(getApplicationContext(), Registro.class);
                startActivity(irreg);
                finish();

            }
        });
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Crear la logica de el boton
                Intent irlog = new Intent(getApplicationContext(),Login.class);
                startActivity(irlog);
                finish();
            }
        });

    }
}