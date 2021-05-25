package com.example.momstime.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.example.momstime.Adapters.NotesAdapter;
import com.example.momstime.DatabaseHelper;
import com.example.momstime.Models.NotesModel;
import com.example.momstime.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RemindersFrag extends Fragment {

    DatePickerDialog.OnDateSetListener mDatesetListener;
    DatabaseHelper databaseHelper;
    RecyclerView remindersRv;
    NotesAdapter adapter;
    LinearLayout emptyLl;
    Button dateBtn;
    public RemindersFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_reminders, container, false);

        emptyLl=view.findViewById(R.id.emptyLl);
        dateBtn=view.findViewById(R.id.dateBtn);

        remindersRv=view.findViewById(R.id.remindersRv);
        remindersRv.setHasFixedSize(true);
        remindersRv.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseHelper=new DatabaseHelper(getContext());



        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogue();
            }
        });


        mDatesetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                i1++;
               // dateEt.setText(i2 + "/" + i1 + "/" + i);

                showRemList(i2 + "/" + i1 + "/" + i);

            }
        };

        return view;
    }


    private void showRemList(String date) {



       if (date==null){
           Date dateObj= Calendar.getInstance().getTime();
           SimpleDateFormat postFormater = new SimpleDateFormat("d/M/yyyy");
           String newDateStr = postFormater.format(dateObj);
           date=newDateStr;
       }

        ArrayList<NotesModel> todayReminders=new ArrayList<>();
        ArrayList<NotesModel> reminders=databaseHelper.fetchSchedulesData();

        for (int j=0;j<reminders.size();j++){

            if(reminders.get(j).getDate().equals(date)){
                todayReminders.add(reminders.get(j));
            }
        }


        if (todayReminders.size()<1){
            emptyLl.setVisibility(View.VISIBLE);
        }
        else {
            emptyLl.setVisibility(View.GONE);
        }


        adapter=new NotesAdapter(todayReminders,getContext());
        remindersRv.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        showRemList(null);
    }




    private void showDatePickerDialogue() {

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mnth = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog, mDatesetListener, year, mnth, day);

        //datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 86400000);
        //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
}