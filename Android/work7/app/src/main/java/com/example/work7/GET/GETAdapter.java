package com.example.work7.GET;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.work7.R;

import java.util.ArrayList;

public class GETAdapter extends RecyclerView.Adapter<GETAdapter.ViewHolder> {
    private ArrayList<GETData> dataList;
    public GETAdapter (ArrayList<GETData> data){
        this.dataList=data;
    }
    @NonNull
    @Override
    public GETAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.get_model,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull GETAdapter.ViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvG1;
        TextView tvG2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvG1=itemView.findViewById(R.id.tv_get_1);
            tvG2=itemView.findViewById(R.id.tv_get_2);
        }
        public void bind(GETData data){
            tvG1.setText(data.getTitle());
            tvG2.setText(data.getContent());
        }
    }

}
