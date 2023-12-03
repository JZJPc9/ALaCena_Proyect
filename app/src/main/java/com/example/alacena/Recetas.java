package com.example.alacena;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

//imports de android recicler view
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alacena.R;
import com.example.alacena.RecAdapter;
import com.example.alacena.clases.IngRec;
import com.example.alacena.clases.Rec;

import java.util.ArrayList;


public class Recetas extends Fragment {

    public Recetas() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView recyclerRec;
        ImageButton btnAgregRec;
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_recetas, container, false);




        btnAgregRec = view.findViewById(R.id.btnAgregRec);
        recyclerRec = view.findViewById(R.id.recyclerRec);
        recyclerRec.setLayoutManager(new GridLayoutManager(getContext(),2));

        ArrayList <IngRec>ingRec = new ArrayList<>();
        ingRec.add(new IngRec(1,"Nombre",2));
        ingRec.add(new IngRec(2,"nombre2",4));
        ArrayList <Rec> recs = new ArrayList<>();
        recs.add(new Rec(1,"Nose",ingRec,"Preparando las cosas con no se muchas cosas"));
        RecAdapter recAdapter = new RecAdapter();
        recyclerRec.setAdapter(recAdapter);
        recAdapter.submitList(recs);


        btnAgregRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntentAct = new Intent(getActivity(), AgreRec.class);
                startActivity(getIntentAct);
            }
        });









        return view;
    }
}