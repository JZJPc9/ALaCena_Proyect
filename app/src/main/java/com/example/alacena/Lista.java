package com.example.alacena;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alacena.clases.IngListCom;

import java.util.ArrayList;

public class Lista extends Fragment {
    public Lista() {}
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView recyclerLisCom;
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.fragment_lista, container, false);

        //asociar
        recyclerLisCom = view.findViewById(R.id.recyclerLisCom);

        recyclerLisCom.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<IngListCom>inglistcom = new ArrayList<>();
        inglistcom.add(new IngListCom("papa",2.0,false));
        inglistcom.add(new IngListCom("jitomate",3.5,false));

        IngLisComAdapter ingLisComAdapter = new IngLisComAdapter();

        ingLisComAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(IngListCom ingListCom) {
                Toast.makeText(getContext(),"cantidad" + String.valueOf(ingListCom.getCantidad()),Toast.LENGTH_SHORT).show();
            }
        });

        recyclerLisCom.setAdapter(ingLisComAdapter);
        ingLisComAdapter.submitList(inglistcom);





        return view;
    }
}