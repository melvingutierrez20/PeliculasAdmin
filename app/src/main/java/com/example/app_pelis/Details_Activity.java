package com.example.app_pelis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class Details_Activity extends AppCompatActivity {

    EditText campo1,campo2,campo3,campo4,campo5;
    ImageButton saveNoteBtn;
    TextView pageTitleTextView;
    String name;
    String descrip;
    String category;
    String idioma;
    String Subtitulos;
    String uid;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        campo1 = findViewById(R.id.campo1);
        campo2 = findViewById(R.id.campo2);
        campo3 = findViewById(R.id.campo3);
        campo4 = findViewById(R.id.campo4);
        campo5 = findViewById(R.id.campo5);
        saveNoteBtn = findViewById(R.id.save_note_btn);
        pageTitleTextView = findViewById(R.id.page_title);


        name = getIntent().getStringExtra("nombre");
        descrip= getIntent().getStringExtra("descripcion");
        category= getIntent().getStringExtra("categoria");
        idioma= getIntent().getStringExtra("idioma");
        Subtitulos = getIntent().getStringExtra("subtitulos");
        uid = getIntent().getStringExtra("uid");

        if(uid!=null && !uid.isEmpty()){
            isEditMode = true;
        }

        campo1.setText(name);
        campo2.setText(descrip);
        campo3.setText(category);
        campo4.setText(idioma);
        campo5.setText(Subtitulos);
        if(isEditMode){
            pageTitleTextView.setText("Editar Nota");
        }

        saveNoteBtn.setOnClickListener( (v)-> saveNote());
    }
    void saveNote() {
        String nombre = campo1.getText().toString();
        String descrip = campo2.getText().toString();
        String categori = campo3.getText().toString();
        String idima = campo4.getText().toString();
        String subti = campo5.getText().toString();
        if(nombre==null || nombre.isEmpty() ){
            campo1.setError("El nombre es requerido");
            return;
        }
        Peli peli = new Peli();
        peli.setName(nombre);
        peli.setDescrip(descrip);
        peli.setCategory(categori);
        peli.setIdioma(idima);
        peli.setSubtitulos(subti);

        saveNoteToFirebase(peli);
    }

    void saveNoteToFirebase(Peli note) {
        DocumentReference documentReference;
        if (isEditMode) {
            documentReference = Utils.getCollectionReferenceForNotes().document(uid);
        } else {
            documentReference = Utils.getCollectionReferenceForNotes().document();
        }


        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utils.showToast(Details_Activity.this, "Nota agregada correctamente");
                    finish();
                } else {
                    Utils.showToast(Details_Activity.this, "Error, no se pudo agregar la nota");
                }
            }
        });
    }

}