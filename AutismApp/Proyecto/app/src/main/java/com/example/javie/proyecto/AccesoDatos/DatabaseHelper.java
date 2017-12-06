package com.example.javie.proyecto.AccesoDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.javie.proyecto.Entidades.Pictograma;

/**
 * Created by javie on 12/2/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Pictograma.db";
    public static final String TABLE_NAME = "pictograma_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOMBRE";
    public static final String COL_3 = "IMAGE";
    public static final String COL_4 = "CATEGORIA";
    public static final String COL_5 = "RESPUESTA";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 +" TEXT," + COL_3 + " BLOB," + COL_4 + " TEXT, " + COL_5 + " TEXT)");
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String nombre, byte[] image, String categoria, String respuesta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nombre);
        contentValues.put(COL_3, image);
        contentValues.put(COL_4, categoria);
        contentValues.put(COL_5, respuesta);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String id, String nombre,  byte[] image, String categoria, String respuesta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, nombre);
        contentValues.put(COL_3, image);
        contentValues.put(COL_4, categoria);
        contentValues.put(COL_5, respuesta);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Cursor getPictogramaPorNombre(String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns ={COL_1,COL_2,COL_3,COL_4, COL_5};
        Cursor res = db.query(TABLE_NAME, columns, "NOMBRE=?", new String[] { nombre }, null, null, null);
        // db.rawQuery("select * from " + TABLE_NAME + " where NOMBRE = " + nombre, null);
        return res;
    }
    public Cursor getPictogramaPorCategoria(String categoria) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns ={COL_1,COL_2,COL_3,COL_4, COL_5};
        Cursor res = db.query(TABLE_NAME, columns, "CATEGORIA=?", new String[] { categoria }, null, null, null);
        // db.rawQuery("select * from " + TABLE_NAME + " where NOMBRE = " + nombre, null);
        return res;
    }


//    public String getUriPictograma(String nombre) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String uri = "";
////        String selectQuery = "SELECT  URI FROM " + TABLE_NAME + " WHERE " + COL_2 + " = " + nombre;
////        Cursor c = db.rawQuery(selectQuery, null);
////        if (c != null)
////            c.moveToFirst();
////        uri = c.getString(c.getColumnIndex(COL_3));
////        return uri;
//        String query = "SELECT * FROM " + TABLE_NAME + " WHERE NOMBRE='" + nombre;
//
//        Cursor  cursor = db.rawQuery(query,null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//            uri = cursor.getString(cursor.getColumnIndex(COL_3));
//        }
//        return uri;
//    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }




}