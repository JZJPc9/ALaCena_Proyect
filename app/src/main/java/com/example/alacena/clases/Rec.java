package com.example.alacena.clases;

import java.util.ArrayList;

public class Rec {

    private int id;
    private String nombre;
    private ArrayList <IngRec> ingredients;
    private String preparacion;

    public Rec(int id, String nombre, ArrayList <IngRec> ingredients, String preparacion) {
        this.id = id;
        this.nombre = nombre;
        this.ingredients = ingredients;
        this.preparacion = preparacion;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public ArrayList<IngRec> getIngredients() {return ingredients;}
    public void setIngredients(ArrayList<IngRec> ingredients) {this.ingredients = ingredients;}

    public String getPreparacion() {return preparacion;}
    public void setPreparacion(String preparacion) {this.preparacion = preparacion;}


    public String ingformatter(){

        String form = null;

        for(IngRec ingRec : ingredients){
            form += ingRec.getNombre() + "   " +String.valueOf(ingRec.getCantidad() + " pz " + "\n" );
        }

        return form;

    }


}
