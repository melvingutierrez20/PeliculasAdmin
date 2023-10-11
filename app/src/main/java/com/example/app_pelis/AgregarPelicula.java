package com.example.app_pelis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AgregarPelicula extends AppCompatActivity {

    //    TextView id_Usuario, Correo_Usuario, Fecha_hora, Fecha, Estado;
//
//    EditText Titulo, Descripcion;
//    Button Btn_Calendario;
//
//    int dia, mes, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pelicula);

        //        InicializarVariables();
//        ObtenerDatos();
//        ObtenerFHA();
//
//        Btn_Calendario.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Calendar calendario = Calendar.getInstance();
//
//                dia = calendario.get(Calendar.DAY_OF_MONTH);
//                mes = calendario.get(Calendar.MONTH);
//                year = calendario.get(Calendar.YEAR);
//                DatePickerDialog datePickerDialog = new DatePickerDialog(AgregarPelicula.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int Año, int Mes, int Dia) {
//                        String diaF, mesF;
//
//                        if (Dia < 10){
//                            diaF = "0" + String.valueOf(Dia);
//                        }else{
//                            diaF = String.valueOf(Dia);
//                        }
//                        int mes = Mes + 1;
//
//                        if (mes < 10){
//                            mesF = "0" + String.valueOf(mes);
//                        }else{
//                            mesF = String.valueOf(mes);
//                        }
//                        Fecha.setText(diaF + "/" + mesF + "/" + Año);
//                    }
//                }
//                ,year,mes,dia);
//                datePickerDialog.show();
//            }
//        });
    }
    //
//    private void ObtenerFHA() {
//
//    }
//
//    private void ObtenerDatos() {
//    }
//
//    private void InicializarVariables() {
//    }
}