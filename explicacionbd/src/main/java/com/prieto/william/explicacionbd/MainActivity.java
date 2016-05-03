package com.prieto.william.explicacionbd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button bInsertar, bActualizar, bEliminar, bConsultar;
    private EditText eCodigo, eNombre, eEdad;
    private TextView Resultado;

    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bInsertar = (Button) findViewById(R.id.bInsertar);
        bActualizar = (Button) findViewById(R.id.bActualizar);
        bEliminar = (Button) findViewById(R.id.bEliminar);
        bConsultar = (Button) findViewById(R.id.bConsultar);
        eCodigo = (EditText) findViewById(R.id.eCod);
        eNombre = (EditText) findViewById(R.id.eNom);
        eEdad = (EditText) findViewById(R.id.eEdad);
        Resultado = (TextView) findViewById(R.id.txtResultado);

        //Abrimos la base de datos 'UsuariosBD' en modo escritura
        UsuariosSQLiteHelper usuario = new UsuariosSQLiteHelper(this);
        //UsuariosSQLiteHelper usuario = new UsuariosSQLiteHelper(this, "UsuariosBD", null, 1);

        db = usuario.getWritableDatabase();
       // SQLiteDatabase db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {

            for (int i = 1; i <= 3; i++) {
                String nombre = "usuario"+i;
                int edad = 20 + i;

                db.execSQL("INSERT INTO Usuarios(nombre,edad)"
                        + "VALUES('" + nombre + "','" + edad + "')");
            }
        }

        Ver_Tabla();

        bInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre=eNombre.getText().toString();
                String  edad= eEdad.getText().toString();

                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("nombre", nombre);
                nuevoRegistro.put("edad", edad);
                db.insert("Usuarios", null, nuevoRegistro);
                Ver_Tabla();
            }
        });

        bActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codigo=eCodigo.getText().toString();
                String nombre=eNombre.getText().toString();
                String edad=eEdad.getText().toString();

                ContentValues nuevoValor = new ContentValues();
                nuevoValor.put("nombre",nombre);
                nuevoValor.put("edad",edad);
                db.update("Usuarios", nuevoValor, "codigo=" + codigo, null);
                Ver_Tabla();
            }
        });

        bEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codigo=eCodigo.getText().toString();

                db.delete("Usuarios", "codigo="+codigo, null);
                Ver_Tabla();
            }
        });

        bConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = eCodigo.getText().toString();
                String[] campos = new String[]{"codigo", "nombre", "edad"};
                String[] args = new String[]{id};

                Cursor c = db.query("Usuarios", campos, "codigo=?", args, null, null, null);



                //Nos aseguramos de que existe al menos un registro
                if (c.moveToFirst()) {
                    Resultado.setText("");
                    //Recorremos el cursor hasta que no encontrar mas registros
                    do {
                        String codigo = c.getString(0);
                        String nombre = c.getString(1);
                        int edad = c.getInt(2);

                       Resultado.append(" " + codigo + " - " + nombre + " - " + edad + "\n");
                    } while (c.moveToNext());
                }
            }
        });
    }



    protected void Ver_Tabla() {
    //PAra mostrar todos los campos de la tabla
      Cursor c = db.rawQuery("SELECT codigo,nombre,edad FROM Usuarios", null);

      Resultado.setText("");
        if (c.moveToFirst())
          do {
             String codigo = c.getString(0);
             String nombre = c.getString(1);
             int    edad = c.getInt(2);

             Resultado.append(" " + codigo + " -- " + nombre + " -- " + edad + "\n");

            }while (c.moveToNext());
    }

}




