package com.example.app_pelis;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Adapter extends FirestoreRecyclerAdapter<Peli, Adapter.NoteViewHolder> {

    Context context;

    public Adapter(@NonNull FirestoreRecyclerOptions<Peli> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Peli note) {
        holder.name.setText(note.name);
        holder.descrip.setText(note.descrip);
        holder.category.setText(note.category);
        holder.idioma.setText(note.idioma);
        holder.Subtitulos.setText(note.Subtitulos);


        holder.Editar.setOnClickListener((v) -> {
            Intent intent = new Intent(context, AgregarPelicula.class);
            intent.putExtra("nombre", note.name);
            intent.putExtra("descripcion", note.descrip);
            intent.putExtra("categoria", note.descrip);
            intent.putExtra("idioma", note.descrip);
            intent.putExtra("subtitulos", note.descrip);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("uid", docId);
            context.startActivity(intent);
        });

        holder.Borrar.setOnClickListener((v) -> {
            String code = this.getSnapshots().getSnapshot(position).getId();
            Utils.getCollectionReferenceForNotes().document(code).delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Utils.showToast(context, "Nota Borrada Correctamente");
                    notifyDataSetChanged();
                } else {
                    Utils.showToast(context, "Error, No se pudo Eliminar la Nota");
                }
            });

        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peli, parent, false);
        return new NoteViewHolder(view);
    }


    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView name, descrip, category, idioma, Subtitulos;
        ImageButton Borrar, Editar;



        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.note_title_text_view);
            descrip = itemView.findViewById(R.id.note_content_text_view);
            category = itemView.findViewById(R.id.note_timestamp_text_view);
            idioma = itemView.findViewById(R.id.note_timestamp_text_view);
            Subtitulos = itemView.findViewById(R.id.note_timestamp_text_view);

            Borrar = itemView.findViewById(R.id.Borrar);
            Editar = itemView.findViewById(R.id.Editar);


        }
    }
}
