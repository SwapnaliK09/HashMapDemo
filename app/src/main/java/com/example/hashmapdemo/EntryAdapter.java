package com.example.hashmapdemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    private ArrayList<HashMap<String, String>> dataArrayList;
    private Activity activity;


    public EntryAdapter(ArrayList<HashMap<String, String>> dataArrayList, Activity activity) {
        this.dataArrayList = dataArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public EntryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lists_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryAdapter.ViewHolder holder, int position) {
        HashMap<String, String> map = dataArrayList.get(position);
        holder.txt_api.setText(map.get("api"));
        holder.txt_auth.setText(map.get("auth"));
        holder.txt_desc.setText(map.get("desc"));

    }

    @Override
    public int getItemCount() {
//        return 0;
        return dataArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_api, txt_desc, txt_auth;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_api = itemView.findViewById(R.id.api_txt);
            txt_desc = itemView.findViewById(R.id.des_txt);
            txt_auth = itemView.findViewById(R.id.auth_txt);
        }

    }
}
