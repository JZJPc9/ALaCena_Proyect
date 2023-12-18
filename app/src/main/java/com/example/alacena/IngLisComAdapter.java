package com.example.alacena;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alacena.DB.DBHelper;
import com.example.alacena.clases.IngListCom;
import com.example.alacena.interf.LOnItemClickListener;

import java.util.ArrayList;


public class IngLisComAdapter extends ListAdapter <IngListCom, IngLisComAdapter.IngLisComViewHolder>{

    //asignacion de la interface con el metodo de accion setOnclicListener

    private ArrayList<IngListCom> milista;
    private LOnItemClickListener objitemclick;

    public void setOnItemClickListener(LOnItemClickListener objitemclick){
        this.objitemclick = objitemclick;
    }
    //fin de la interface


    //configuracion de el dif para no repetido
    protected IngLisComAdapter(ArrayList<IngListCom> milista){
        super(DIFF_CALLBACK);
        this.milista = milista;
    }

    @NonNull
    @Override
    public IngLisComAdapter.IngLisComViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.res_lis_item,parent,false);
        return new IngLisComViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull IngLisComAdapter.IngLisComViewHolder holder, int pos){
        IngListCom ingListCom = getItem(pos);
        holder.bind(ingListCom);
    }




    //clase viewholder
    class IngLisComViewHolder extends RecyclerView.ViewHolder{

        //nombre y cantidad
        private TextView nomitemLis,cantitemlis;
        private CheckBox checkCom;
        private ImageButton elimItem;

        public IngLisComViewHolder(@NonNull View itemView){
            super(itemView);
            nomitemLis = itemView.findViewById(R.id.nomitemLis);
            cantitemlis = itemView.findViewById(R.id.cantitemlis);
            checkCom = itemView.findViewById(R.id.checkCom);
            elimItem = itemView.findViewById(R.id.elimItem);

        }

        public void bind (IngListCom ingListCom){
            nomitemLis.setText(ingListCom.getNombre());
            cantitemlis.setText(String.valueOf(ingListCom.getCantidad()));
            checkCom.setChecked(ingListCom.isCheck());
            //Esta sera una prueba de uso de el boton del recurso item
            elimItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHelper db = new DBHelper(v.getContext());
                    SQLiteDatabase dbw = db.getWritableDatabase();
                    String delargs[] = {String.valueOf(ingListCom.getId())};
                    dbw.delete("IngredienteLista","id_ingli = ?",delargs);

                    milista.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyDataSetChanged();


                }
            });

            checkCom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHelper db = new DBHelper(v.getContext());
                    SQLiteDatabase dbw = db.getWritableDatabase();

                    String bolargs[] = {String.valueOf(ingListCom.getId())};
                    ContentValues valbol = new ContentValues();

                    if(checkCom.isChecked()){
                        Log.i("checked","1");
                        valbol.put("bol_ingli","1");
                        dbw.update("IngredienteLista",valbol,"id_ingli = ?",bolargs);
                        dbw.close();
                    }else{
                        Log.i("checked","0");
                        valbol.put("bol_ingli","0");
                        dbw.update("IngredienteLista",valbol,"id_ingli = ?",bolargs);
                        dbw.close();
                    }




                }
            });


        }

    }
    //fin de la clase viewholder


    public static final DiffUtil.ItemCallback<IngListCom> DIFF_CALLBACK = new DiffUtil.ItemCallback<IngListCom>() {
        @Override
        public boolean areItemsTheSame(@NonNull IngListCom oldItem, @NonNull IngListCom newItem) {
            return oldItem.getNombre().equals(newItem.getNombre());
        }

        @Override
        public boolean areContentsTheSame(@NonNull IngListCom oldItem, @NonNull IngListCom newItem) {
            return oldItem.equals(newItem);
        }
    };




}



