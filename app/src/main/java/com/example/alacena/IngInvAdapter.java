package com.example.alacena;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alacena.DB.DBHelper;
import com.example.alacena.clases.IngInv;
import com.example.alacena.interf.FragAct;
import com.example.alacena.interf.IOnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class IngInvAdapter extends ListAdapter <IngInv, IngInvAdapter.IngInvViewHolder> {

    //asignacion
    private ArrayList<IngInv>  milista;

    private IOnItemClickListener objitemclick;

    public void setOnItemClickListener(IOnItemClickListener objitemclick){
        this.objitemclick = objitemclick;
    }

    //configuracion de el dif para no repetirlo
    protected IngInvAdapter(ArrayList<IngInv> milista){
        super(DIFF_CALLBACK);
        this.milista = milista;
    }

    //
    public IngInvAdapter.IngInvViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.res_inv_item,parent,false);
        return new IngInvViewHolder(view);
    }

    public void onBindViewHolder(@NonNull IngInvAdapter.IngInvViewHolder holder, int pos){
        IngInv ingInv = getItem(pos);
        holder.bind(ingInv);
    }

    //class viewholder
    class IngInvViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNomIng,txtTieIng,txtCanIng;
        private ImageButton btnMas,btnMen,btnOpt;

        public IngInvViewHolder(@NonNull View itemview){
            super(itemview);
            txtNomIng = itemview.findViewById(R.id.txtNomIng);
            txtTieIng = itemview.findViewById(R.id.txtTieIng);
            txtCanIng = itemview.findViewById(R.id.txtCanIng);
            btnMas = itemview.findViewById(R.id.btnMas);
            btnMen = itemview.findViewById(R.id.btnMen);
            btnOpt = itemview.findViewById(R.id.btnOpt);


        }
        public void bind(IngInv ingInv){
            txtNomIng.setText(ingInv.getNombre());
            txtTieIng.setText(String.valueOf(ingInv.getTimeTerm()) +" "+ txtTieIng.getText());
            txtCanIng.setText(String.valueOf(ingInv.getCantidad()));

            //logica de boton
            btnMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    double cantidad = Double.valueOf(txtCanIng.getText().toString());
                    double suma = cantidad + 1;
                    if ((int)suma >= 0) {
                        txtCanIng.setText(String.valueOf(suma));
                        DBHelper db = new DBHelper(v.getContext());
                        SQLiteDatabase dbw = db.getWritableDatabase();
                        ContentValues updIngInv = new ContentValues();
                        updIngInv.put("can_ingin", suma);
                        String argsUpdIngIn[] = {String.valueOf(ingInv.getId())};
                        dbw.update("IngredienteInventario", updIngInv, "id_ingin = ?", argsUpdIngIn);
                        dbw.close();
                    }else{
                        DBHelper db = new DBHelper(v.getContext());
                        SQLiteDatabase dbw = db.getWritableDatabase();
                        String argDelIng[] = {String.valueOf(ingInv.getId())};
                        dbw.delete("LoteIngrediente","id_ingin = ?",argDelIng);
                        dbw.delete("IngredienteInventario","id_ingin = ?",argDelIng);


                        milista.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                }
            });



            btnMen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    double cantidad = Double.valueOf(txtCanIng.getText().toString());
                    double resta = cantidad - 1;
                    if ((int)resta >= 0) {
                        txtCanIng.setText(String.valueOf(resta));
                        DBHelper db = new DBHelper(v.getContext());
                        SQLiteDatabase dbw = db.getWritableDatabase();
                        ContentValues updIngInv = new ContentValues();
                        updIngInv.put("can_ingin", resta);
                        String argsUpdIngIn[] = {String.valueOf(ingInv.getId())};
                        dbw.update("IngredienteInventario", updIngInv, "id_ingin = ?", argsUpdIngIn);
                        dbw.close();
                    }else{
                        DBHelper db = new DBHelper(v.getContext());
                        SQLiteDatabase dbw = db.getWritableDatabase();
                        String argDelIng[] = {String.valueOf(ingInv.getId())};
                        dbw.delete("LoteIngrediente","id_ingin = ?",argDelIng);
                        dbw.delete("IngredienteInventario","id_ingin = ?",argDelIng);


                        milista.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                }
            });



            btnOpt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarMenu(v,ingInv);
                }
            });
        }

        //logica para las opciones y creacion de el menu "mas opciones"
        private void mostrarMenu(View v, IngInv ingInv){
            //creando el menu desplegable
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            //se le vincula con los recursos de opciones
            popupMenu.inflate(R.menu.popup_mav_menu);

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.toString().equals("Editar") || item.toString().equals("Edit")){
                        Intent edit = new Intent(v.getContext(),EditIngre.class);
                        edit.putExtra("idingin",String.valueOf(ingInv.getId()));
                        edit.putExtra("nominginv",ingInv.getNombre());
                        edit.putExtra("caningin",String.valueOf(ingInv.getCantidad()));
                        edit.putExtra("fechaingin",ingInv.getFecFin());
                        v.getContext().startActivity(edit);
                        return true;
                    }else{

                        //Eliminar registro de la base de datos
                        DBHelper db = new DBHelper(v.getContext());
                        SQLiteDatabase dbw = db.getWritableDatabase();
                        String argDelIng[] = {String.valueOf(ingInv.getId())};
                        dbw.delete("LoteIngrediente","id_ingin = ?",argDelIng);
                        dbw.delete("IngredienteInventario","id_ingin = ?",argDelIng);


                        milista.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyDataSetChanged();


                        return true;
                    }
                }
            });
            popupMenu.show();
        }
        /*final del pop up mexico*/
    }

    public static final DiffUtil.ItemCallback<IngInv> DIFF_CALLBACK = new DiffUtil.ItemCallback<IngInv>() {
        @Override
        public boolean areItemsTheSame(@NonNull IngInv oldItem, @NonNull IngInv newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull IngInv oldItem, @NonNull IngInv newItem) {
            return false;
        }
    };


}
