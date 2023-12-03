package com.example.alacena;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alacena.clases.IngListCom;

import java.util.ArrayList;

public class Lista extends Fragment {
    public Lista() {}
    View view;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ImageButton btnAgregIng;
        Button btnAgreInv;
        RecyclerView recyclerLisCom;
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_lista, container, false);

        //asociar
        btnAgreInv = view.findViewById(R.id.btnAgreInv);
        btnAgregIng = view.findViewById(R.id.btnAgregIng);
        recyclerLisCom = view.findViewById(R.id.recyclerLisCom);
        recyclerLisCom.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<IngListCom>inglistcom = new ArrayList<>();
        inglistcom.add(new IngListCom(1,"papa",2,true));
        inglistcom.add(new IngListCom(2,"jitomate",3,false));
        IngLisComAdapter ingLisComAdapter = new IngLisComAdapter();
        recyclerLisCom.setAdapter(ingLisComAdapter);
        ingLisComAdapter.submitList(inglistcom);


        btnAgregIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntentAct = new Intent(getActivity(),AgreList.class);
                startActivity(getIntentAct);
            }
        });




        //retornar view
        return view;
    }


}