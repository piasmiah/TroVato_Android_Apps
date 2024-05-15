package com.trodev.trovato.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trodev.trovato.EnvatoDetailsActivity;
import com.trodev.trovato.R;
import com.trodev.trovato.models.EnvatoModels;

import java.util.ArrayList;

public class EnvatoAdapter extends RecyclerView.Adapter<EnvatoAdapter.MyViewHolder> {

    ArrayList<EnvatoModels> list;
    Context context;

    public EnvatoAdapter(ArrayList<EnvatoModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public EnvatoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.envato_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnvatoAdapter.MyViewHolder holder, int position) {

        EnvatoModels model = list.get(position);

        // Image get on firebase database.....
         Picasso.get().load(model.getImage()).placeholder(R.drawable.envato_elements).into(holder.imageIv);

        holder.detailsTv.setText(model.getPname());
        holder.productCode_TV.setText(model.getPcode());
        holder.dateTv.setText(model.getDate());
        holder.productPrice_Tv.setText(model.getPprice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EnvatoDetailsActivity.class);

                intent.putExtra("pname", model.getPname());
                intent.putExtra("pcode", model.getPcode());
                intent.putExtra("pdate", model.getDate());
                intent.putExtra("plicense", model.getPlicense());
                intent.putExtra("pdes", model.getPdes());
                intent.putExtra("pfilesize", model.getFileSize_ET());
                intent.putExtra("pappsup", model.getAppSup_ET());
                intent.putExtra("ptime", model.getTime());
                intent.putExtra("pvideo", model.getPvideo());
                intent.putExtra("pprice", model.getPprice());

                intent.putExtra("pimage", model.getImage());

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

        private TextView detailsTv, productPrice_Tv, dateTv, productCode_TV;
        private CardView cardView;
        private ImageView imageIv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            detailsTv = itemView.findViewById(R.id.prductName_TV);
            productPrice_Tv = itemView.findViewById(R.id.productPrice_Tv);
            dateTv = itemView.findViewById(R.id.dateTv);
            productCode_TV = itemView.findViewById(R.id.productCode_TV);
            imageIv = itemView.findViewById(R.id.imageIv);

        }
    }
}
