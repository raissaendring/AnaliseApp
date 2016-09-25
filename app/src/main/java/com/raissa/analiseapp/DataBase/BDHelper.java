package com.raissa.analiseapp.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.raissa.analiseapp.R;

/**
 * Created by primelan on 9/23/15.
 */
public class BDHelper extends SQLiteOpenHelper {
    public BDHelper(Context context) {
        super(context, "bd_caca_entulho_app", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cadastros(_id text primary key, " +
                "nome text not null," +
                "matricula text not null," +
                "foto text not null, " +
                "latitude double not null," +
                "longitude double not null," +
                "valor int not null," +
                "referencia text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("drop table cadastros;");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
