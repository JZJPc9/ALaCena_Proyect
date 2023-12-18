package com.example.alacena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.alacena.DB.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditIngre extends AppCompatActivity {


    TextView nombre;
    EditText cantidad,dia,mes,ano;
    Button guardar;
    ImageButton btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingre);
        nombre = findViewById(R.id.nomIngEdit);
        cantidad = findViewById(R.id.IngreCan);
        dia = findViewById(R.id.diaexp);
        mes = findViewById(R.id.mesexp);
        ano = findViewById(R.id.yeaexp);
        btnback = findViewById(R.id.btnBackInv);
        guardar = findViewById(R.id.guardar);

        Intent datos = getIntent();
        String idin = datos.getStringExtra("idingin");
        String nombrein= datos.getStringExtra("nominginv");
        String cantidadin = datos.getStringExtra("caningin");
        String fechain = datos.getStringExtra("fechaingin");

        nombre.setText(nombrein);
        cantidad.setText(cantidadin);
        try {
            String fechaform[] = descomponerFecha(fechain);
            dia.setText(fechaform[2]);
            mes.setText(fechaform[1]);
            ano.setText(fechaform[0]);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(getApplicationContext());
                SQLiteDatabase dbw = db.getWritableDatabase();
                String newcantidad = cantidad.getText().toString();
                String newfecha = ano.getText().toString() + "-" + mes.getText().toString() + "-" + dia.getText().toString() ;
                ContentValues can = new ContentValues();
                can.put("can_ingin",newcantidad);
                String args[] = {idin};
                dbw.update("IngredienteInventario",can,"id_ingin = ?",args);
                ContentValues fec = new ContentValues();
                fec.put("fec_lotin",newfecha);
                dbw.update("LoteIngrediente",fec,"id_ingin = ?",args);
                dbw.close();

                Intent backmenu = new Intent(getApplicationContext(), Menuprin.class);
                startActivity(backmenu);
                finish();

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

    public String[] descomponerFecha(String fecha) throws ParseException {
        // Formato de la fecha de entrada
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        // Parsear la cadena de fecha a un objeto Date
        Date fechaDate = formatoEntrada.parse(fecha);

        // Formato para obtener el año, mes y día por separado
        SimpleDateFormat formatoAno = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatoMes = new SimpleDateFormat("MM");
        SimpleDateFormat formatoDia = new SimpleDateFormat("dd");

        // Obtener los componentes de la fecha como cadenas
        String fecharet[] = {formatoAno.format(fechaDate),formatoMes.format(fechaDate),formatoDia.format(fechaDate)};

        return fecharet;

    }



}