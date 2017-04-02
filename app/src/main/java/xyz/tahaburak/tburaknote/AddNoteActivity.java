package xyz.tahaburak.tburaknote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        final EditText txtNoteTitle = (EditText) findViewById(R.id.txtNoteTitle);
        final EditText txtNoteText = (EditText) findViewById(R.id.txtNoteText);

        Button btnNoteSave = (Button) findViewById(R.id.btnNoteSave);

        final DB db = new DB(this);

        btnNoteSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNoteText.getText().toString().isEmpty() || txtNoteTitle.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), "Please fill the text fields.", Toast.LENGTH_SHORT).show();
                } else {

                    db.addNote(txtNoteTitle.getText().toString(), txtNoteText.getText().toString());
                    Toast.makeText(getBaseContext(), "Success.", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(AddNoteActivity.this, MainActivity.class);
                    startActivity(i);
                }

            }
        });
    }
}
