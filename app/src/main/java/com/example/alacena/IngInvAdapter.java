package com.example.alacena;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alacena.clases.IngInv;


public class IngInvAdapter extends ListAdapter <IngInv, IngInvAdapter.IngInvViewHolder> {

    //asignacion

    private IOnItemClickListener objitemclick;

    public void setOnItemClickListener(IOnItemClickListener objitemclick){
        this.objitemclick = objitemclick;
    }

    //configuracion de el dif para no repetirlo
    protected IngInvAdapter(){super(DIFF_CALLBACK);}

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


        }

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