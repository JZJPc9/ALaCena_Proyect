package com.example.alacena;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.alacena.clases.IngInv;

import java.util.ArrayList;
import java.util.Calendar;


public class Inventario extends Fragment {
    public Inventario() {}

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        RecyclerView recyclerInv;
        ImageButton btnAgreInv;
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_inventario, container, false);



        //asociar

        btnAgreInv = view.findViewById(R.id.btnAgregInv);
        recyclerInv = view.findViewById(R.id.recyclerInv);
        recyclerInv.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<IngInv>ingInv = new ArrayList<>();
        ingInv.add(new IngInv(1,"Cebolla","2023-11-29",1));
        IngInvAdapter ingInvAdapter = new IngInvAdapter();
        recyclerInv.setAdapter(ingInvAdapter);
        ingInvAdapter.submitList(ingInv);


        btnAgreInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getIntentAct = new Intent(getActivity(), AgreInv.class);
                startActivity(getIntentAct);

            }
        });



        return view;
    }
}