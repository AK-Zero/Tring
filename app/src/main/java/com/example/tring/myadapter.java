package com.example.tring;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {

    List<Integer> itemnos;
    List<String> splits;
    List<String> laps;

    public myadapter(List<Integer> i , List<String> s , List<String> l) {

        itemnos = i;
        splits = s;
        laps = l;

    }
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.laplist_item , parent , false);
        return new myviewholder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.tv1.setText(itemnos.get(position).toString() + ':');
        holder.tv2.setText("S : " + splits.get(position));
        holder.tv3.setText("L : " + laps.get(position));

    }

    @Override
    public int getItemCount() {
        return itemnos.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        TextView tv1,tv2,tv3;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.itemno);
            tv2 = itemView.findViewById(R.id.split);
            tv3 = itemView.findViewById(R.id.lap);
        }
    }
}
