package com.example.eva25;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Spinner spSpinner;
    String[] arma = {"Gran Espada", "Espada Larga", "Espada y Escudo", "Espadas Dobles", "Martillo",
            "Cuerno de Caza", "Lanza", "Lanza Pistola", "Hacha Espada", "Glaive Insecto",
            "Hacha Cargada", "Ballesta Ligera", "Ballesta Pesada", "Arco"};
    EditText edtRut, edtNom, edtFel;
    ListView lista;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        edtRut = (EditText) findViewById(R.id.edtRut);
        edtNom = (EditText) findViewById(R.id.edtNom);
        edtFel = (EditText) findViewById(R.id.edtFel);
        spSpinner = (Spinner) findViewById(R.id.spSpinner);
        lista = (ListView) findViewById(R.id.lstLista);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arma);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpinner.setAdapter(spinnerAdapter);
        CargarLista();
    }
    public void onClickMulti(View view){
        Intent intent= new Intent(this, MultimediaActivity.class);
        startActivity(intent);
    }

    public void CargarLista() {
        DataHelper dh = new DataHelper(this, "gremio.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        Cursor c = bd.rawQuery("SELECT rut, nombre, felyne, arma FROM gremio", null);
        String[] arr = new String[c.getCount()];
        if (c.moveToFirst() == true) {
            int i = 0;
            do {
                String linea = "||" + c.getInt(0) + "||" + c.getString(1)
                        + "||" + c.getString(2) + "||" + c.getString(3) + "||";
                arr[i] = linea;
                i++;
            } while (c.moveToNext() == true);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, arr);
            lista.setAdapter(adapter);
            bd.close();
        }
    }

    public void onClickAgregar(View view) {
        DataHelper dh = new DataHelper(this, "gremio.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        ContentValues reg = new ContentValues();
        reg.put("rut", edtRut.getText().toString());
        reg.put("nombre", edtNom.getText().toString());
        reg.put("felyne", edtFel.getText().toString());
        reg.put("arma", spSpinner.getSelectedItem().toString());
        long resp = bd.insert("gremio", null, reg);
        bd.close();
        if (resp == -1) {
            Toast.makeText(this, "No se ´puede ingresar el alumno", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Registro Agregado", Toast.LENGTH_LONG).show();
        }
        CargarLista();
        limpiar();
    }

    public void limpiar() {
        edtRut.setText("");
        edtNom.setText("");
        edtFel.setText("");
    }

    public void onClickModificar(View view) {
        DataHelper dh = new DataHelper(this, "gremio.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        ContentValues reg = new ContentValues();

        reg.put("rut", edtRut.getText().toString());
        reg.put("nombre", edtNom.getText().toString());
        reg.put("felyne", edtFel.getText().toString());
        reg.put("arma", spSpinner.getSelectedItem().toString());

        long respuesta = bd.update("gremio", reg, "rut=?", new String[]{edtRut.getText().toString()});
        bd.close();

        if (respuesta == -1) {
            Toast.makeText(this, "Dato No Modificado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Dato Modificado", Toast.LENGTH_LONG).show();
        }
        limpiar();
        CargarLista();
    }

    public void onClickEliminar(View view) {
        DataHelper dh = new DataHelper(this, "gremio.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        String rutEliminar = edtRut.getText().toString();
        long respuesta = bd.delete("gremio", "rut=" + rutEliminar, null);
        bd.close();
        if(respuesta == -1){
            Toast.makeText(this, "Registro No Eliminado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Registro Eliminado", Toast.LENGTH_LONG).show();
        }
        CargarLista();
        limpiar();
    }
}