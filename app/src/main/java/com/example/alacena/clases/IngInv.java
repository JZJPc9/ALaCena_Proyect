package com.example.alacena.clases;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IngInv {

    private int id;
    private String nombre;
    private String fecFin;
    private double cantidad;

    public IngInv(int id, String nombreIng, String fecFinIng, int canIng){
        this.id = id;
        this.nombre = nombreIng;
        this.fecFin = fecFinIng;
        this.cantidad = canIng;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getFecFin() {return fecFin;}
    public void setFecFin(String fecFin) {this.fecFin = fecFin;}

    public double getCantidad() {return cantidad;}
    public void setCantidad(double cantidad) {this.cantidad = cantidad;}


    public int getTimeTerm(){

        //generamos una fecha actual
        Calendar fec = Calendar.getInstance();
        String tiempo = fec.get(Calendar.YEAR) + "-" +
                (fec.get(Calendar.MONTH)+1)+ "-" +
                fec.get(Calendar.DAY_OF_MONTH);



        //formateamos los datos de entyrada de fechas
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            //convierte las cadenas de tiempo a Date
            Date dateIni = format.parse(tiempo);
            Date dateFin = format.parse(this.fecFin);
            //convierte los date a calendar
            Calendar calIni = Calendar.getInstance();
            calIni.setTime(dateIni);

            Calendar calFin = Calendar.getInstance();
            calFin.setTime(dateFin);

            //calcular diferencia en dias
            long difereciaMillis = calFin.getTimeInMillis() - calIni.getTimeInMillis();

            int diferenciaDias = (int)(difereciaMillis/(24 * 60 * 60 * 1000));
            //devolvemos el valor
            if (diferenciaDias >= 0) {
                return diferenciaDias;
            }else {
                return 0;
            }

        }catch (ParseException e){
            e.printStackTrace();
            return 0;
        }

    }



}
