package com.example.maturitnyprojektfinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        Button odosli = (Button) view.findViewById(R.id.posli);
        final EditText editText = view.findViewById(R.id.postrehy);
        odosli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editText.setText("");
                Toast.makeText(getActivity(), "Odoslane", Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }
    public void posliPostrehy(View view){




    }
}
