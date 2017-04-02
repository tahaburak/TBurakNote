package xyz.tahaburak.tburaknote;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Note> noteArrayList = new ArrayList<>();
    DB db;
    BaseAdapter ba;
    ListView lv;
    LayoutInflater li;

    /*Refresh data onResume*/
    @Override
    protected void onResume() {
        super.onResume();
        refreshList();

    }

    public void refreshList() {
        noteArrayList = db.getNotes();
        ba.notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         /*Get notes as arrayList*/
        db = new DB(this);
        noteArrayList = db.getNotes();

        /*Custom Listview object*/
        lv = (ListView) findViewById(R.id.lv);
        li = LayoutInflater.from(this);
        /*Build layout*/
        ba = new BaseAdapter() {
            @Override
            public int getCount() {
                return noteArrayList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = li.inflate(R.layout.item_note, null);

                }
                TextView tvNoteTitle = (TextView) convertView.findViewById(R.id.tvNoteTitle);
                TextView tvNoteText = (TextView) convertView.findViewById(R.id.tvNoteText);
                Note nt = noteArrayList.get(position);

                tvNoteText.setText(nt.getText());
                tvNoteTitle.setText(nt.getTitle());

                return convertView;

            }


        };

        lv.setAdapter(ba);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, UpdateNoteActivity.class);
                i.putExtra("note_id", noteArrayList.get(position).getId());
                startActivity(i);
            }
        });

        /*Create alerdialog onitemlongclick*/
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("User Operation");
                adb.setMessage("Do you want to delete selected note?");
                adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete note and refresh Listview
                        db.deleteNote(noteArrayList.get(position).getId());
                        dialog.dismiss();
                        refreshList();
                    }
                });
                adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                adb.show();


                return false;
            }
        });
    }
    // next 2 methods are for creating menu item button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.specialmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnActionbar:
                Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
