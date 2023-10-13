package com.example.app_pelis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MunuPrincipal extends AppCompatActivity {

    Button CerrarC, AgregarPeli, Lista;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    TextView NombreP, CorreoP;
    ProgressBar ProgresBar;
    DatabaseReference Usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_munu_principal);

        NombreP = findViewById(R.id.NombreP);
        AgregarPeli = findViewById(R.id.AgregarPeli);
        Lista = findViewById(R.id.Lista);
        CorreoP = findViewById(R.id.CorreoP);
        ProgresBar = findViewById(R.id.ProgresBar);
        CerrarC = findViewById(R.id.CerrarC);
        Usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        CerrarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalirApp();
            }
        });

        AgregarPeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MunuPrincipal.this, AgregarPelicula.class));
            }
        });
        Lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MunuPrincipal.this, vista_peli.class));
            }
        });
    }
    @Override
    protected void onStart() {
        ComprobarInicioSesion();
        super.onStart();
    }

    private void ComprobarInicioSesion(){
        if (user!=null){
            //Se ejecutara si el usuario a iniciado sesion
            CargarDatos();
        }else{
            //Lo dirigira al main activity
            startActivity(new Intent(MunuPrincipal.this, MainActivity.class));
            finish();
        }
    }

    private void CargarDatos(){
        Usuarios.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Si el usuario existe
                if (snapshot.exists()){
                    //El progresBar se oculta
                    ProgresBar.setVisibility(View.GONE);
                    //Los Texview se muestran
                    NombreP.setVisibility(View.VISIBLE);
                    CorreoP.setVisibility(View.VISIBLE);

                    String nombre = ""+snapshot.child("nombres").getValue();
                    String correo = ""+snapshot.child("correo").getValue();

                    NombreP.setText(nombre);
                    CorreoP.setText(correo);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SalirApp() {
        firebaseAuth.signOut();
        startActivity(new Intent(MunuPrincipal.this, MainActivity.class));
        Toast.makeText(this, "Sesion Cerrada Correctamente", Toast.LENGTH_SHORT).show();
    }
}