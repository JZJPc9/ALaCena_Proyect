package com.example.alacena;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Recetas extends Fragment {


    View view;
    /*public Recetas() {
        // Required empty public constructor
    }

    public static Recetas newInstance(String param1, String param2){
        Recetas fragment = new Recetas();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recetas, container, false);
        return view;
    }

    /*
    public void onDestroyView() {
        super.onDestroyView();
    }*/


}