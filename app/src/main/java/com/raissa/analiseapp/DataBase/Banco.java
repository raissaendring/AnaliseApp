package com.raissa.analiseapp.DataBase;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.raissa.analiseapp.ItemCadastro;

import java.util.ArrayList;

/**
 * Created by primelan on 9/23/15.
 */
public class Banco {
    SQLiteDatabase db;

    public Banco(Context context){
        db = new BDHelper(context).getWritableDatabase();
    }

    public void inserir(ItemCadastro cadastro){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id",cadastro.getId());
        contentValues.put("nome",cadastro.getNome());
        contentValues.put("matricula",cadastro.getMatricula());
        contentValues.put("foto",cadastro.getFoto());
        contentValues.put("txt",cadastro.getTxt());
        contentValues.put("tabela",cadastro.getTabela());
        db.insert("cadastros",null,contentValues);
    }

    public void deletar(String id){
        db.delete("cadastros", "_id = " + id, null);
    }

    public ArrayList<ItemCadastro> todosCadastros(){
        ArrayList<ItemCadastro> cadastros = new ArrayList<>();
        String colunas[] = {"_id","nome","matricula","foto","txt","tabela"};
        Cursor c = db.query("cadastros",colunas,null,null,null,null,null);
        if(c.getCount()>0){
            c.moveToFirst();
            do{
                ItemCadastro cadastro = new ItemCadastro(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5));
                cadastros.add(cadastro);
            }while (c.moveToNext());
            c.close();
        }
        return cadastros;
    }

    public void deletarTodos(ArrayList<ItemCadastro> cadastros){
        for(ItemCadastro cadastro:cadastros){
            deletar(cadastro.getId());
        }
    }

    public int countCadastros(){
        int nCadastros;

        Cursor c = db.rawQuery("select count(*) from cadastros",null);
        c.moveToFirst();
        nCadastros = c.getInt(0);
        c.close();
        return nCadastros;
    }
}
