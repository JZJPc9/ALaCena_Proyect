package com.example.alacena;

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

import com.example.alacena.clases.IngListCom;


public class IngLisComAdapter extends ListAdapter <IngListCom, IngLisComAdapter.IngLisComViewHolder>{

    //asignacion de la interface con el metodo de accion setOnclicListener
    private LOnItemClickListener objitemclick;

    public void setOnItemClickListener(LOnItemClickListener objitemclick){
        this.objitemclick = objitemclick;
    }
    //fin de la interface


    //configuracion de el dif para no repetido
    protected IngLisComAdapter(){
        super(DIFF_CALLBACK);
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
            cantitemlis.setText(ingListCom.getCantidad().toString());
            checkCom.setSelected(ingListCom.isCheck());
            //Esta sera una prueba de uso de el boton del recurso item
            elimItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Prueba"+ ingListCom.getNombre(),Toast.LENGTH_SHORT).show();
                }
            });
            /*
            itemView.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    objitemclick.onItemClick(ingListCom);
                }
            });

             */

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



