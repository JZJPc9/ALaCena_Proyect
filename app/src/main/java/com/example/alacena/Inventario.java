package com.example.alacena;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_inventario, container, false);



        //asociar
        recyclerInv = view.findViewById(R.id.recyclerInv);

        recyclerInv.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<IngInv>ingInv = new ArrayList<>();



        ingInv.add(new IngInv("Cebolla","2023-11-23",1));


        IngInvAdapter ingInvAdapter = new IngInvAdapter();

        recyclerInv.setAdapter(ingInvAdapter);
        ingInvAdapter.submitList(ingInv);


        return view;
    }
}