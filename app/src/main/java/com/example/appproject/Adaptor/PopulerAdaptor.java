package com.example.appproject.Adaptor;


import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appproject.Activity.ShowDetailActivity;
import com.example.appproject.Domain.FoodDomain;
import com.example.appproject.R;

import java.util.ArrayList;



public class PopulerAdaptor extends RecyclerView.Adapter<PopulerAdaptor.ViewHolder> {

    ArrayList<FoodDomain> PopulerFood;

    public PopulerAdaptor(ArrayList<FoodDomain> PopulerFood) {
        this.PopulerFood = PopulerFood;
    }

    @Override
    public PopulerAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_populer,parent,false);
        return new PopulerAdaptor.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PopulerAdaptor.ViewHolder holder, int position) {
        holder.title.setText(PopulerFood.get(position).getTitle());
        holder.fee.setText(String.valueOf(PopulerFood.get(position).getFee()));


        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(PopulerFood.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());


        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object",PopulerFood.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return PopulerFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,fee ;
        ImageView pic ;
        TextView addBtn ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            fee = itemView.findViewById(R.id.fee) ;
            pic = itemView.findViewById(R.id.pic) ;
            addBtn = itemView.findViewById(R.id.addBtn) ;
        }
    }
}

