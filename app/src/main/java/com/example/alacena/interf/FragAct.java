package com.example.alacena.interf;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.alacena.R;

public class FragAct {

    public void remplaceFragment(Fragment fragment) {
        FragmentActivity frag = new FragmentActivity();
        FragmentManager fragmentManager = frag.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framenav,fragment);
        fragmentTransaction.commit();
    }
}
