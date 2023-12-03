package com.example.alacena;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alacena.clases.Rec;
import com.example.alacena.interf.ROnItemClickListener;

public class RecAdapter extends ListAdapter <Rec, RecAdapter.RecViewHolder>{

    //asignacion de el click
    private ROnItemClickListener objitemclick;

    public void setOnItemClickListener(ROnItemClickListener objitemclick){
        this.objitemclick = objitemclick;
    }

    //configuracion de el dif para no repetirlo
    protected RecAdapter(){super(DIFF_CALLBACK);}

    //creacion y pocicion de e holder

    public RecAdapter.RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.res_rec_item,parent,false);
        return new RecViewHolder(view);
    }

    public void onBindViewHolder(@NonNull RecAdapter.RecViewHolder holder,int pos){
        Rec rec = getItem(pos);
        holder.bind(rec);
    }



    //view holder
    class RecViewHolder extends RecyclerView.ViewHolder{
        private TextView identify,txtNomRec,txtIngRec,txtPreRec;

        public RecViewHolder(@NonNull View itemview){
            super(itemview);
            identify = itemview.findViewById(R.id.identify);
            txtNomRec = itemview.findViewById(R.id.txtNomRec);
            txtIngRec = itemview.findViewById(R.id.txtIngRec);
            txtPreRec = itemview.findViewById(R.id.txtPreRec);
        }
        public void bind(Rec rec){
            identify.setText(String.valueOf(rec.getId()));
            txtNomRec.setText(rec.getNombre());
            txtIngRec.setText(rec.ingformatter());
            txtPreRec.setText(rec.getPreparacion());

        }
    }


    public static final DiffUtil.ItemCallback<Rec> DIFF_CALLBACK = new DiffUtil.ItemCallback<Rec>() {
        @Override
        public boolean areItemsTheSame(@NonNull Rec oldItem, @NonNull Rec newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Rec oldItem, @NonNull Rec newItem) {
            return false;
        }
    };


}
