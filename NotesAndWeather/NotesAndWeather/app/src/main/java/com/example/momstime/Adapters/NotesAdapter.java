package com.example.momstime.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momstime.AddNewNoteActivity;
import com.example.momstime.Models.NotesModel;
import com.example.momstime.R;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesVH> {

    ArrayList<NotesModel> list;
    Context context;

    public NotesAdapter(ArrayList<NotesModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NotesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_layout,null);
        return new NotesVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesVH holder, int position) {

        holder.tTv.setText(list.get(position).getTitle());
        holder.dateTv.setText(list.get(position).getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AddNewNoteActivity.class);
                intent.putExtra("t",list.get(position).getTitle());
                intent.putExtra("desc",list.get(position).getDesc());
                intent.putExtra("date",list.get(position).getDate());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NotesVH extends RecyclerView.ViewHolder{
           TextView tTv,dateTv;

        public NotesVH(@NonNull View itemView) {
            super(itemView);
            tTv=itemView.findViewById(R.id.tTv);
            dateTv=itemView.findViewById(R.id.dateTv);
        }
    }
}
