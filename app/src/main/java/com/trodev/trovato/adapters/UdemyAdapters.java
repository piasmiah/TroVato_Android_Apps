package com.trodev.trovato.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.trodev.trovato.R;
import com.trodev.trovato.activity.UdemyDetailsActivity;
import com.trodev.trovato.models.UdemyModels;

import java.util.ArrayList;

public class UdemyAdapters extends RecyclerView.Adapter<UdemyAdapters.MyViewHolder> {

    ArrayList<UdemyModels> list;
    Context context;

    public UdemyAdapters(ArrayList<UdemyModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setFilteredList(ArrayList<UdemyModels> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UdemyAdapters.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.udemy_list_item, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull UdemyAdapters.MyViewHolder holder, int position) {

        UdemyModels model = list.get(position);

        // Image get on firebase database.....
        Picasso.get().load(model.getImage()).placeholder(R.drawable.course_image).into(holder.imageIv);

        holder.name.setText(model.getCname());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
        holder.rating.setText(model.getCrating() + " ⭐");
        holder.lang.setText(model.getClanguage());


        /*send data on udemy activity*/
        holder.enroll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UdemyDetailsActivity.class);

                intent.putExtra("cname", model.getCname());
                intent.putExtra("crating", model.getCrating());
                intent.putExtra("clanguage", model.getClanguage());
                intent.putExtra("cdate", model.getDate());
                intent.putExtra("ctime", model.getTime());
                intent.putExtra("cdes", model.getCdescriptio());
                intent.putExtra("clink", model.getClink());

                /*image pass details activity*/
                intent.putExtra("cimage", model.getImage());

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageIv;
        TextView name, lang, rating, date, time;
        MaterialButton enroll_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.courseName_TV);
            lang = itemView.findViewById(R.id.language_TV);
            rating = itemView.findViewById(R.id.rating_TV);
            date = itemView.findViewById(R.id.date_TV);
            time = itemView.findViewById(R.id.time_TV);
            enroll_btn = itemView.findViewById(R.id.enroll_btn);

            imageIv = itemView.findViewById(R.id.imageIv);

        }
    }
}
