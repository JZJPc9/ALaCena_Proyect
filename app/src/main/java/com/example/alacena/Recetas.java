package com.example.alacena;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//imports de android recicler view
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Recetas extends Fragment {

    public Recetas() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView recyclerRec;
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_recetas, container, false);

        recyclerRec = view.findViewById(R.id.recyclerRec);

        //recyclerRec.setLayoutManager(new GridLayoutManager());









        return view;
    }
}