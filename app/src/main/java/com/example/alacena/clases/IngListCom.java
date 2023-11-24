package com.example.alacena.clases;

import java.util.Objects;

public class IngListCom {


    private int id;
    private String nombre;
    private double cantidad;
    private boolean check;

    public IngListCom(int id, String nombreIng, Integer cantidadIng, boolean checkIng){
        this.id = id;
        this.nombre = nombreIng;
        this.cantidad = cantidadIng;
        this.check = checkIng;
    }

    //g s de id
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    //g s de nombre
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}


    //g s de cantidad
    public double getCantidad() {return cantidad;}
    public void setCantidad(double cantidad) {this.cantidad = cantidad;}


    //g s de check
    public boolean isCheck() {return check;}
    public void setCheck(boolean check) {this.check = check;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngListCom)) return false;
        IngListCom that = (IngListCom) o;
        return Objects.equals(getNombre(), that.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre());
    }
}
