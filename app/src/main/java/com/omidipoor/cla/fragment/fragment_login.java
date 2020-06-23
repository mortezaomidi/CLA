package com.omidipoor.cla.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.omidipoor.cla.R;
import com.omidipoor.cla.activity.MapActivity;

public class fragment_login extends Fragment {


    public fragment_login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button button = view.findViewById(R.id.btn_login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MapActivity.class);
                startActivity(i);
                // ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });
        return view;
    }
}



