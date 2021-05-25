package com.example.momstime.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.momstime.Adapters.NotesAdapter;
import com.example.momstime.AddNewScheduleActivity;
import com.example.momstime.DatabaseHelper;
import com.example.momstime.Models.NotesModel;
import com.example.momstime.R;

import java.util.ArrayList;

public class ScheduleFrag extends Fragment {
    FloatingActionButton newScheduleBtn;
    DatabaseHelper databaseHelper;
    RecyclerView schedulesRv;
    NotesAdapter adapter;
    LinearLayout emptyLl;
    public ScheduleFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_schedule, container, false);
        emptyLl=view.findViewById(R.id.emptyLl);
        newScheduleBtn=view.findViewById(R.id.newScheduleBtn);
        schedulesRv=view.findViewById(R.id.schedulesRv);
        schedulesRv.setHasFixedSize(true);
        schedulesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseHelper=new DatabaseHelper(getContext());


        newScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), AddNewScheduleActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void showSchedulesList() {
        ArrayList<NotesModel> notes=databaseHelper.fetchSchedulesData();

        if (notes.size()<1){
            emptyLl.setVisibility(View.VISIBLE);
        }
        else {
            emptyLl.setVisibility(View.GONE);
        }

        adapter=new NotesAdapter(notes,getContext());
        schedulesRv.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        showSchedulesList();
    }
}