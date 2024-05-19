package com.trodev.trovato.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trodev.trovato.models.BillModels;
import com.trodev.trovato.R;

import java.util.ArrayList;

public class BillHistoryAdapter extends RecyclerView.Adapter<BillHistoryAdapter.MyViewHolder> {

    ArrayList<BillModels> list;
    Context context;

    public BillHistoryAdapter(ArrayList<BillModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BillHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.billhistory_list_item, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BillHistoryAdapter.MyViewHolder holder, int position) {

        BillModels model = list.get(position);

        holder.invoice.setText(model.getPayment_bill_no());
        holder.transaction_id.setText(model.getTransactionId());
        holder.date.setText(model.getPayment_date());
        holder.product_code.setText(model.getProduct_code());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView invoice, transaction_id, product_name, product_code, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            invoice = itemView.findViewById(R.id.invoice_TV);
            transaction_id = itemView.findViewById(R.id.transaction_TV);
            product_name = itemView.findViewById(R.id.product_name_TV);
            product_code = itemView.findViewById(R.id.productCode_TV);
            date = itemView.findViewById(R.id.dateTv);

        }
    }
}
