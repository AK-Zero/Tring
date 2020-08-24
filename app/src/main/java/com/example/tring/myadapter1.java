package com.example.tring;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class myadapter1 extends RecyclerView.Adapter<myadapter1.myviewholder1> {

    reset MA;
    int flag=0;
    Context mcontext;
    public myadapter1(Context context) {
    mcontext = context;
    }
    @NonNull
    @Override
    public myviewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item2 , parent , false);
        return new myviewholder1(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final myviewholder1 holder, final int position) {
        if(position%3==0){
            holder.tv1.setVisibility(View.VISIBLE);
            holder.tv2.setVisibility(View.INVISIBLE);
            holder.tv3.setVisibility(View.INVISIBLE);
            holder.tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tv1.setVisibility(View.INVISIBLE);
                    flag++;
                    if (flag == 27) {
                        MA.resetpage();
                    }
                }
            });
        }
        else if(position%3==1){
            holder.tv1.setVisibility(View.INVISIBLE);
            holder.tv2.setVisibility(View.VISIBLE);
            holder.tv3.setVisibility(View.INVISIBLE);
            holder.tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tv2.setVisibility(View.INVISIBLE);
                    flag++;
                    if (flag == 27) {
                        MA.resetpage();
                    }
                }
            });
        }
        else{
            holder.tv1.setVisibility(View.INVISIBLE);
            holder.tv2.setVisibility(View.INVISIBLE);
            holder.tv3.setVisibility(View.VISIBLE);
            holder.tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tv3.setVisibility(View.INVISIBLE);
                    flag++;
                    if (flag == 27) {
                        MA.resetpage();
                    }
                }
            });}
    }

    @Override
    public int getItemCount() {
        return 27;
    }

    public class myviewholder1 extends RecyclerView.ViewHolder{

        TextView tv1,tv2,tv3;
        public myviewholder1(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.box1);
            tv2 = itemView.findViewById(R.id.box2);
            tv3 = itemView.findViewById(R.id.box3);
        }
    }

    public interface reset{
        void resetpage();
    }
    public void setreset(reset x){
        MA = x;
    }
}

