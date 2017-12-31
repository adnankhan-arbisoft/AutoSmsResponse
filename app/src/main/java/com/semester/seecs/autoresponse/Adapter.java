package com.semester.seecs.autoresponse;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private List<Model> modelList = new ArrayList<>();

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Model model = modelList.get(position);

        String date =  model.getStartTime().getReadableTime()
                + "\n To \n" + model.getEndTime().getReadableTime();

        holder.time.setText(date);

        holder.message.setText(model.getMessage());

        holder.status.setChecked(model.isActive());

    }

    @Override
    public int getItemCount() {
        return  modelList.size();
    }

    public void setData(List<Model> modelList){
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time, message;
        public Switch status;

        public MyViewHolder(View view) {
            super(view);
            time =  view.findViewById(R.id.time_duration);
            message =  view.findViewById(R.id.message);
            status =  view.findViewById(R.id.status);
        }
    }
}
