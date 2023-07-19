package com.example.assignandroidnetworking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignandroidnetworking.R;
import com.example.assignandroidnetworking.modal.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHoler> {
    List<History>list;
    public void setData(List<History>historyList){
        this.list=historyList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public HistoryViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history,parent,false);

        return new HistoryViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHoler holder, int position) {
        History history=list.get(position);
        if(history==null){
            return;
        }
        holder.tv_Id.setText(history.getId());
        holder.tv_FullName.setText(history.getFullName());
        holder.tv_Phone.setText(history.getPhone());
        holder.tv_Address.setText(history.getAddress());
        holder.tv_Price.setText(history.getCart().getFood().getPrice()+"");
        holder.tv_Name_Food.setText(history.getCart().getFood().getName());
        holder.tv_Quantity.setText(history.getCart().getQuantity()+"");
        holder.tv_Time.setText(history.getTime());
        holder.tv_Payment_Method.setText(history.getPaymentMethod());
        holder.tv_Total.setText(history.getCart().getQuantity() * history.getCart().getFood().getPrice()+" VND");

    }
    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    public  class HistoryViewHoler extends RecyclerView.ViewHolder{
        TextView tv_Id,tv_FullName,tv_Phone,tv_Address,tv_Time,
        tv_Total,tv_Payment_Method,tv_Quantity,
        tv_Price,tv_Name_Food;
        public HistoryViewHoler(@NonNull View itemView) {
            super(itemView);
            tv_Address=itemView.findViewById(R.id.tv_address);
            tv_Phone=itemView.findViewById(R.id.tv_phone);
            tv_Time=itemView.findViewById(R.id.tv_time);
            tv_Id=itemView.findViewById(R.id.tv_id);
            tv_Total=itemView.findViewById(R.id.tv_total);
            tv_Payment_Method=itemView.findViewById(R.id.tv_payment_method);
            tv_FullName=itemView.findViewById(R.id.tv_full_name);
            tv_Quantity=itemView.findViewById(R.id.tv_quantity);
            tv_Price=itemView.findViewById(R.id.tv_price_food);
            tv_Name_Food=itemView.findViewById(R.id.tv_name_food);

        }
    }

}
