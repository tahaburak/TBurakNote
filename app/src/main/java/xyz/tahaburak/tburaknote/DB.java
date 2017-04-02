package xyz.tahaburak.tburaknote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by tahaburaks on 02/04/2017.
 */

public class DB extends SQLiteOpenHelper {
    SQLiteDatabase db;

    public DB(Context context) {
        // super(context, name, factory, version);
        super(context, "mainDB", null, 1);

        db = getWritableDatabase();
        db.close();
    }

    //Runs only once since no need to drop db
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table notes (id integer primary key, note_title text, note_text text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exist notes");
        this.onCreate(db);
    }

    // add note into DB with base 64
    public void addNote(String title, String text) {
        db = getWritableDatabase();

        String values[] = {BS64.encode(title), BS64.encode(text)};

        db.execSQL("insert into notes (note_title, note_text) values (?, ?)", values);
        db.close();
    }

    public void deleteNote(int id) {
        db = getWritableDatabase();

        db.execSQL("delete from notes where id = " + id);

        db.close();

    }

    public void updateNote(Integer id, String title, String text) {
        db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("note_title", BS64.encode(title));
        cv.put("note_text", BS64.encode(text));

        db.update("notes", cv, "id=" + id, null);
    }

    // get notes with base64 decoding
    public ArrayList<Note> getNotes() {
        ArrayList<Note> notesList = new ArrayList<>();
        db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from notes", null);
        while (c.moveToNext()) {

            Note nt = new Note(c.getInt(0), BS64.decode(c.getString(c.getColumnIndex("note_title"))),
                    BS64.decode(c.getString(c.getColumnIndex("note_text"))));
            notesList.add(nt);
        }

        c.close();
        db.close();
        return notesList;
    }

    // Used in UpdateNoteActivity
    public Note noteFromID(Integer note_id) {
        db = getWritableDatabase();
        Cursor c = db.rawQuery("select note_title, note_text from notes where id = " + note_id, null);
        c.moveToFirst();

        Note nt = new Note(note_id, BS64.decode(c.getString(c.getColumnIndex("note_title"))),
                BS64.decode(c.getString(c.getColumnIndex("note_text"))));


        db.close();
        return nt;


    }
}
