package com.example.alacena.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "Alacena.db";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dbAlacena) {
        dbAlacena.execSQL("CREATE TABLE DatosUsuario (" +
                "id_dat INTEGER NOT NULL ," +
                "corr_dat TEXT NOT NULL," +
                "pass_dat TEXT," +
                "PRIMARY KEY(" +
                "id_dat " +
                "AUTOINCREMENT)" +
                ")");
        dbAlacena.execSQL("CREATE TABLE GrupoFamiliar  (" +
                "id_gfa INTEGER NOT NULL," +
                "cod_gfa TEXT," +
                "nom_gfa TEXT," +
                "PRIMARY KEY(id_gfa " +
                "AUTOINCREMENT)" +
                ")");
        dbAlacena.execSQL("CREATE TABLE Rol (" +
                "id_rol INTEGER NOT NULL," +
                "nom_rol TEXT," +
                "PRIMARY KEY(id_rol " +
                "AUTOINCREMENT)" +
                ")");
        dbAlacena.execSQL("CREATE TABLE Permisos (" +
                "id_perm INTEGER NOT NULL," +
                "nom_per TEXT," +
                "sta_per INTEGER," +
                "PRIMARY KEY(id_perm " +
                "AUTOINCREMENT)" +
                ")");
        dbAlacena.execSQL("CREATE TABLE IF NOT EXISTS Usuario (" +
                "id_usu INTEGER NOT NULL," +
                "fcre_usu TEXT," +
                "nom_usu TEXT," +
                "app_usu TEXT," +
                "apm_usu TEXT," +
                "id_rol INTEGER," +
                "id_gfa INTEGER," +
                "id_dat INTEGER," +
                "id_perm INTEGER," +
                "PRIMARY KEY(id_usu " +
                "AUTOINCREMENT)," +
                "FOREIGN KEY(id_perm) REFERENCES Permisos(id_perm)," +
                "FOREIGN KEY(id_rol) REFERENCES Rol(id_rol)," +
                "FOREIGN KEY(id_gfa) REFERENCES GrupoFamiliar(id_gfa)," +
                "FOREIGN KEY(id_dat) REFERENCES DatosUsuario(id_dat)" +
                ")");

        dbAlacena.execSQL("CREATE TABLE IF NOT EXISTS Recetario (" +
                "id_rece INTEGER NOT NULL," +
                "id_gfa INTEGER," +
                "FOREIGN KEY(id_gfa) REFERENCES GrupoFamiliar(id_gfa)," +
                "PRIMARY KEY(id_rece " +
                "AUTOINCREMENT)" +
                ")");

        dbAlacena.execSQL("CREATE TABLE IF NOT EXISTS Receta (" +
                "id_rec INTEGER NOT NULL," +
                "nom_rec TEXT," +
                "ins_rec TEXT," +
                "id_rece INTEGER," +
                "FOREIGN KEY(id_rece) REFERENCES Recetario(id_rece)," +
                "PRIMARY KEY(id_rec " +
                "AUTOINCREMENT)" +
                ")");

        dbAlacena.execSQL("CREATE TABLE IF NOT EXISTS Inventario (" +
                "id_inv INTEGER NOT NULL," +
                "id_gfa INTEGER," +
                "FOREIGN KEY(id_gfa) REFERENCES GrupoFamiliar(id_gfa)," +
                "PRIMARY KEY(id_inv " +
                "AUTOINCREMENT)" +
                ")");

        dbAlacena.execSQL("CREATE TABLE IF NOT EXISTS ListaCompras (" +
                "id_lic INTEGER NOT NULL," +
                "id_gfa INTEGER," +
                "FOREIGN KEY(id_gfa) REFERENCES GrupoFamiliar(id_gfa)," +
                "PRIMARY KEY(id_lic " +
                "AUTOINCREMENT)" +
                ")");
        dbAlacena.execSQL("CREATE TABLE IF NOT EXISTS IngredienteInventario  (" +
                "id_ingin INTEGER NOT NULL," +
                "nom__ingin TEXT," +
                "can_ingin NUMERIC," +
                "id_inv INTEGER," +
                "FOREIGN KEY(id_inv) REFERENCES Inventario(id_inv)," +
                "PRIMARY KEY(id_ingin " +
                "AUTOINCREMENT)" +
                ")");

        dbAlacena.execSQL("CREATE TABLE IF NOT EXISTS IngredienteLista (" +
                "id_ingli INTEGER NOT NULL," +
                "nom_ingli TEXT," +
                "can_ingli TEXT," +
                "id_lic INTEGER," +
                "FOREIGN KEY(id_lic) REFERENCES Lista(id_lic)," +
                "PRIMARY KEY(id_ingli " +
                "AUTOINCREMENT)" +
                ")");

        dbAlacena.execSQL("CREATE TABLE IF NOT EXISTS IngredienteReceta (" +
                "id_ingre INTEGER NOT NULL," +
                "nom_ingre TEXT," +
                "can_ingre TEXT," +
                "apo_ingre TEXT," +
                "id_rec INTEGER," +
                "FOREIGN KEY(id_rec) REFERENCES Receta(id_rec)," +
                "PRIMARY KEY(id_ingre " +
                "AUTOINCREMENT)" +
                ")");

        dbAlacena.execSQL("CREATE TABLE IF NOT EXISTS LoteIngrediente (" +
                "id_lotin INTEGER NOT NULL," +
                "fec_lotin TEXT," +
                "id_ingin INTEGER," +
                "FOREIGN KEY(id_ingin) REFERENCES IngredienteInventario(id_ingin)," +
                "PRIMARY KEY(id_lotin " +
                "AUTOINCREMENT)" +
                ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
