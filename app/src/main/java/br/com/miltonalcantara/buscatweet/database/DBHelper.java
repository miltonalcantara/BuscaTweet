package br.com.miltonalcantara.buscatweet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Criado por Milton Alcântara em 28/09/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private final static String BANCODEDADOS = "mydb";
    private final static String TABELA = "pesquisas";
    private final static String PALAVRA = "palavra";

    public DBHelper(Context context) {
        super(context, BANCODEDADOS, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABELA + "(id integer primary key, " + PALAVRA + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void apagarLista() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA, null, null);
    }

    public boolean inserirPalavraPesquisada(String palavra) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(PALAVRA, palavra); //inserir aqui outras colunas
        db.insert(TABELA, null, content);
        return true;
    }

    public ArrayList<String> PegarTodasPesquisas() {
        ArrayList<String> myArray = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from " + TABELA, null);
        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            String c;
            c = (cur.getString(cur.getColumnIndex(PALAVRA)));
            myArray.add(c);
            cur.moveToNext();
        }
        return myArray;
    }

}
