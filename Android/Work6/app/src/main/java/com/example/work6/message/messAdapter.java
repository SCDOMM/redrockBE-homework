package com.example.work6.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.work6.R;

import java.util.ArrayList;

public class messAdapter extends RecyclerView.Adapter<messAdapter.ViewHolder> {
    private ArrayList<messData> dataList;
    public interface OnItemClickListener{
        void onItemClick(messData data);
    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public messAdapter (ArrayList<messData> list){
        this.dataList=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mess_model,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull messAdapter.ViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTd;
        TextView mTt;
        ImageView mTi;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            mTd=itemView.findViewById(R.id.mess_title);
            mTi=itemView.findViewById(R.id.mess_image);
            mTt=itemView.findViewById(R.id.mess_data);
            itemView.setOnClickListener(view -> {
                int pos=getAdapterPosition();
                messData data=dataList.get(pos);
                listener.onItemClick(data);
            });
        }
        public void bind(messData data){
            mTt.setText(data.getTitle());
            mTd.setText(data.getData());
            mTi.setImageResource(data.getImage());
        }
    }

}
