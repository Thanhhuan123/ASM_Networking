package com.example.assignandroidnetworking.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignandroidnetworking.R;
import com.example.assignandroidnetworking.modal.Food;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    List<Food>list;
    View view;

    public IclickDetail clickItem;
    public   interface  IclickDetail{
        void detailFood(Food food);
    }
    public FoodAdapter(IclickDetail clickItem) {
        this.clickItem = clickItem;
    }
    public  void setData(List<Food>foodList){
        this.list=foodList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food,parent,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        final Food food=list.get(position);
        if(food==null){
            return;

        }
        holder.tv_Name.setText(food.getName());
        holder.tv_Price.setText(food.getPrice()+" VND");
        Picasso.get().load(food.getImage()).into(holder.img_Food);

        holder.img_Food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem.detailFood(food);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(list!=null)
            return list.size();
        return 0;
    }
    public  class FoodViewHolder extends RecyclerView.ViewHolder{
        TextView tv_Name,tv_Price;
        ImageView img_Food;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Name=itemView.findViewById(R.id.tv_name);
            tv_Price=itemView.findViewById(R.id.tv_price);
            img_Food=itemView.findViewById(R.id.img_food);
        }
    }
}
