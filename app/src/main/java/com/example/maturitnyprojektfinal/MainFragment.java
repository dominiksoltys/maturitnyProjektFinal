package com.example.maturitnyprojektfinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        userId = fAuth.getCurrentUser().getUid();

        View view = inflater.inflate(R.layout.fragment_main,container,false);
        Button odosli = view.findViewById(R.id.posli);
        final EditText editText = view.findViewById(R.id.postrehy);
        odosli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> postrehy = new HashMap<>();
                postrehy.put("Id", userId);
                postrehy.put("Text", editText.getText().toString().trim());
                fStore.collection("postrehy").add(postrehy);
                editText.setText("");
                Toast.makeText(getActivity(), "Odoslane", Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }
    public void posliPostrehy(View view){




    }
}
