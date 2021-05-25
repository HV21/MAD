package com.example.momstime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.momstime.Models.NotesModel;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AddNewScheduleActivity extends AppCompatActivity {
    EditText tEt,dEt,dateEt;
    FloatingActionButton saveScheduleBtn;
    DatabaseHelper databaseHelper;
    WorkManager workManager;
    DatePickerDialog.OnDateSetListener mDatesetListener;
    public static final String TAG_MY_WORK = "mywork";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_schedule);

        dateEt=findViewById(R.id.dateEt);
        dEt=findViewById(R.id.dEt);
        tEt=findViewById(R.id.tEt);
        saveScheduleBtn=findViewById(R.id.saveScheduleBtn);
        databaseHelper=new DatabaseHelper(getApplicationContext());
        workManager=WorkManager.getInstance(this);


        dateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogue();
            }
        });

        mDatesetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                i1++;
                dateEt.setText(i2 + "/" + i1 + "/" + i);

            }
        };



        saveScheduleBtn.setOnClickListener(new View.OnClickListener() {
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
                if (dateEt.getText().toString().length() < 1) {

                    dateEt.setError("Please select date");
                    return;
                }


                saveSchedule();
            }
        });
    }





    private void saveSchedule() {

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;


       NotesModel notesModel=new NotesModel();
        notesModel.setTitle(tEt.getText().toString());
        notesModel.setDesc(dEt.getText().toString());
        notesModel.setDate(dateEt.getText().toString());
        notesModel.setStatus("pending");
        notesModel.setId(String.valueOf(m));
        databaseHelper.insertSchedulesData(notesModel);

        tEt.setText("");
        dEt.setText("");
        dateEt.setText("");

        Toast.makeText(this, "Schedule saved successfully", Toast.LENGTH_SHORT).show();
        scheduleWork("work");

    }









    private void showDatePickerDialogue() {

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mnth = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewScheduleActivity.this, android.R.style.Theme_DeviceDefault_Dialog, mDatesetListener, year, mnth, day);

        //datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 86400000);
     //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }



    public void scheduleWork(String tag) {



      PeriodicWorkRequest request=new PeriodicWorkRequest.Builder(Notify.class,18,TimeUnit.MINUTES).build();
      WorkManager.getInstance(AddNewScheduleActivity.this).enqueueUniquePeriodicWork(tag,ExistingPeriodicWorkPolicy.REPLACE,request);
    }


}