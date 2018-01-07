package com.semester.seecs.autoresponse;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private List<Model> modelList = new ArrayList<>();

    private ItemClickListener itemClickListener;


    public Adapter(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Model model = modelList.get(position);

        String date =  model.getStartTime().getReadableTime()
                + " To " + model.getEndTime().getReadableTime();

        holder.time.setText(date);

        holder.message.setText(model.getMessage());

        holder.status.setChecked(model.isActive());
        holder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                model.setActive(b);
                itemClickListener.onStatusChange(model);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               itemClickListener.onItemClick(model);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemRemove(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return  modelList.size();
    }

    public void setData(Set<Model> models){
        modelList = new ArrayList<>(models);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time, message;
        public Switch status;
        public ImageView delete;


        public MyViewHolder(View view) {
            super(view);
            time =  view.findViewById(R.id.time_duration);
            message =  view.findViewById(R.id.message);
            status =  view.findViewById(R.id.status);
            delete = view.findViewById(R.id.delete);

        }
    }


    public static interface ItemClickListener{
        public void onItemClick(Model model);
        public void onItemRemove(Model model);
        public void onStatusChange(Model model);
    }
}
