package com.example.maturitnyprojektfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText memail,mpassword;
    Button mloginBtn;
    TextView mcreateBtn,forgotTextLink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        mloginBtn = findViewById(R.id.loginBtn);
        mcreateBtn = findViewById(R.id.createBtn);
        forgotTextLink = findViewById(R.id.forgotPassword);

        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    memail.setError("Zadaj email");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mpassword.setError("Zadaj heslo");
                    return;
                }
                if (password.length()< 6 ){
                    mpassword.setError("Heslo musi mat aspon 6 znakov");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //authentifikacia usera

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Si prihlaseny", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),drawerActivity.class));
                        }else{
                            Toast.makeText(Login.this, "Chyba" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mcreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Obnova hesla");
                passwordResetDialog.setMessage("Zadajte vas email na obnovenie hesla");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Poziadat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //vytiahnutie emailu a odoslanie reset linku
                        String mail =  resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Odkaz na obnovenie hesla odoslany", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Erorik,link na obnovenie neodoslany" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("Zrusit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //zatvorenie dialogu,navrat do login.java
                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }
}
