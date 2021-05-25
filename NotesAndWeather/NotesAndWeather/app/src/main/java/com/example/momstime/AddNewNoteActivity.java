package com.example.momstime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.momstime.Models.NotesModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNewNoteActivity extends AppCompatActivity {
EditText tEt,dEt;
FloatingActionButton saveNoteBtn;
DatabaseHelper databaseHelper;
NotesModel notesModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        tEt=findViewById(R.id.tEt);
        dEt=findViewById(R.id.dEt);
        saveNoteBtn=findViewById(R.id.saveNoteBtn);
        databaseHelper=new DatabaseHelper(getApplicationContext());

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            tEt.setInputType(0);
            dEt.setInputType(0);
            saveNoteBtn.setVisibility(View.GONE);

            tEt.setText(bundle.getString("t"));
            dEt.setText(bundle.getString("desc"));
        }

        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tEt.getText().toString().length() < 1) {

                    tEt.setError("Please enter title");
                    return;
                }
                if (dEt.getText().toString().length() < 1) {

                    dEt.setError("Please enter description");
                    return;
                }

                saveNote();
            }
        });


    }

    private void saveNote() {



        Date dateObj= Calendar.getInstance().getTime();
        SimpleDateFormat postFormater = new SimpleDateFormat("d/M/yyyy");
        String newDateStr = postFormater.format(dateObj);

        notesModel=new NotesModel();
         notesModel.setTitle(tEt.getText().toString());
         notesModel.setDesc(dEt.getText().toString());
         notesModel.setDate(newDateStr);
         databaseHelper.insertNotesData(notesModel);

         tEt.setText("");
         dEt.setText("");

        Toast.makeText(this, "note saved successfully", Toast.LENGTH_SHORT).show();

    }
}