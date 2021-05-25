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
import com.example.momstime.AddNewNoteActivity;
import com.example.momstime.DatabaseHelper;
import com.example.momstime.Models.NotesModel;
import com.example.momstime.R;

import java.util.ArrayList;

public class NotesFrag extends Fragment {

FloatingActionButton newNoteBtn;
DatabaseHelper databaseHelper;
RecyclerView notesRv;
NotesAdapter adapter;
LinearLayout emptyLl;
    public NotesFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.notes_fragment, container, false);
        emptyLl=view.findViewById(R.id.emptyLl);
        newNoteBtn=view.findViewById(R.id.newNoteBtn);
        notesRv=view.findViewById(R.id.notesRv);
        notesRv.setHasFixedSize(true);
        notesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseHelper=new DatabaseHelper(getContext());



        newNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), AddNewNoteActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void shownotesList() {

        ArrayList<NotesModel> notes=databaseHelper.fetchNotesData();
        if (notes.size()<1){
            emptyLl.setVisibility(View.VISIBLE);
        }
        else {
            emptyLl.setVisibility(View.GONE);
        }
        adapter=new NotesAdapter(notes,getContext());
        notesRv.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        shownotesList();
    }
}