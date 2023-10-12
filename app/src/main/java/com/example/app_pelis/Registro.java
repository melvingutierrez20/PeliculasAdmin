package com.example.app_pelis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_pelis.Menu.InicioActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {

    EditText NombreEt, CorreoEt, Contra, ConfirmarContra;
    Button RegistrarUsu;
    TextView TengoCuenta;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    String nombre = " ",  correo = " ", password = "", confirmarpassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        NombreEt = findViewById(R.id.NombreEt);
        CorreoEt = findViewById(R.id.CorreoEt);
        Contra = findViewById(R.id.Contra);
        ConfirmarContra = findViewById(R.id.ConfirmarContra);
        RegistrarUsu = findViewById(R.id.RegistrarUsu);
        TengoCuenta = findViewById(R.id.TengoCuenta);
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Registro.this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Registrar");
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);


        RegistrarUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });

        TengoCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registro.this, Login.class));
            }
        });
    }

    private void validarCampos(){
        nombre = NombreEt.getText().toString();
        correo = CorreoEt.getText().toString();
        password = Contra.getText().toString();
        confirmarpassword = ConfirmarContra.getText().toString();

        if (TextUtils.isEmpty(nombre)){
            Toast.makeText(this, "Ingrese el Nombre", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Ingrese el Correo", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese la Contraseña", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(confirmarpassword)) {
            Toast.makeText(this, "Confirme la Contraseña", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmarpassword)) {
            Toast.makeText(this, "Lass contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        } else{
            CrearCuenta();
        }
    }

    private void CrearCuenta() {
        progressDialog.setMessage("Creando su Cuenta");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(correo, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                //
                GuardarInfo();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Registro.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void GuardarInfo() {
        progressDialog.setMessage("Guardando su informacion");
        progressDialog.dismiss();

        String uid = firebaseAuth.getUid();

        HashMap<String, String> Datos = new HashMap<>();
        Datos.put("uid", uid);
        Datos.put("correo", correo);
        Datos.put("nombres", nombre);
        Datos.put("password", password);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.child(uid).setValue(Datos).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(Registro.this, "Cuenta creada con Exito", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Registro.this, InicioActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Registro.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}