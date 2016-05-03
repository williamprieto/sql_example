package com.prieto.william.explicacionbd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by willo on 02/05/2016.
 */
public class UsuariosSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME="UsuariosBD";
    private static final int DATA_VERSION=1;

    //Sentencia SQL Para crear una tabla
    String sqlCreate = "CREATE TABLE Usuarios(codigo INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,edad INTEGER)";

    public UsuariosSQLiteHelper(Context contexto) {
        super(contexto, DATA_BASE_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }
}
