package xyz.tahaburak.tburaknote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateNoteActivity extends AppCompatActivity {
    // Initialize note_id as 1
    Integer note_id = 1;
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        final EditText txtNoteUpdateTitle = (EditText) findViewById(R.id.txtNoteUpdateTitle);
        final EditText txtNoteUpdateText = (EditText) findViewById(R.id.txtNoteUpdateText);
        Button btnNoteUpdate = (Button) findViewById(R.id.btnNoteUpdate);

        // get note id from previous activity
        note_id = getIntent().getIntExtra("note_id", 1);

        //get note and fill text fields
        db = new DB(this);
        Note nt = db.noteFromID(note_id);

        txtNoteUpdateText.setText(nt.getText());
        txtNoteUpdateTitle.setText(nt.getTitle());


        final DB db = new DB(this);
        btnNoteUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNoteUpdateText.getText().toString().isEmpty() || txtNoteUpdateTitle.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), "Please fill the text fields.", Toast.LENGTH_SHORT).show();
                } else {

                    db.updateNote(note_id, txtNoteUpdateTitle.getText().toString(), txtNoteUpdateText.getText().toString());
                    Toast.makeText(getBaseContext(), "Success.", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(UpdateNoteActivity.this, MainActivity.class);
                    startActivity(i);
                }

            }
        });

    }
}
