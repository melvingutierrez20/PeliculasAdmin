package com.example.app_pelis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AgregarPelicula extends AppCompatActivity {

    EditText campo1, campo2, campo3, campo4, campo5;
    Button btnAgre;
    String nombre= " ", descripcion = " ", categoria = " ", idioma = " ", subtitulos = " ";
    FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pelicula);

        campo1 = findViewById(R.id.campo1);
        campo2 = findViewById(R.id.campo2);
        campo3 = findViewById(R.id.campo3);
        campo4 = findViewById(R.id.campo4);
        campo5 = findViewById(R.id.campo5);

        btnAgre = findViewById(R.id.btnAgre);
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(AgregarPelicula.this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        this.setTitle("Crear reservacion");
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnAgre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });
    }

    private void validarCampos(){
        nombre = campo1.getText().toString();
        descripcion = campo2.getText().toString();
        categoria = campo3.getText().toString();
        idioma = campo4.getText().toString();
        subtitulos = campo5.getText().toString();



        if (TextUtils.isEmpty(nombre)){
            Toast.makeText(this, "Ingrese el Nombre", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(descripcion) ){
            Toast.makeText(this, "Ingrese el Descripcion", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(categoria)) {
            Toast.makeText(this, "Ingrese la Categoria", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(idioma)) {
            Toast.makeText(this, "Ingrese la Idioma", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(subtitulos)) {
            Toast.makeText(this, "Ingrese la Subtitulos", Toast.LENGTH_SHORT).show();
        }else{
            CrearCuenta();
        }
    }
    private void CrearCuenta() {
        progressDialog.setMessage("Ingresando Registro");
        progressDialog.show();
        GuardarInfo();
    }

    private void GuardarInfo() {
        progressDialog.setMessage("Guardando");
        progressDialog.dismiss();

        String uid = firebaseAuth.getUid();

        HashMap<String, String> Datos = new HashMap<>();
        Datos.put("uid", uid);
        Datos.put("nombre", nombre);
        Datos.put("descripcion", descripcion);
        Datos.put("categoria", categoria);
        Datos.put("idioma", idioma);
        Datos.put("subtitulos", subtitulos);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Peliculas");
        databaseReference.child(uid).setValue(Datos).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(AgregarPelicula.this, "Pelicula ingresada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AgregarPelicula.this, MunuPrincipal.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AgregarPelicula.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}