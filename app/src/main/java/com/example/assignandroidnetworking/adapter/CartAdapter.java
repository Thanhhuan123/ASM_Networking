package com.example.assignandroidnetworking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignandroidnetworking.R;
import com.example.assignandroidnetworking.modal.Cart;
import com.example.assignandroidnetworking.modal.Food;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    List<Cart>list;

    public IClick clickItem;
    int amount;
    public CartAdapter(IClick clickItem) {
        this.clickItem = clickItem;
    }

    public  interface IClick{
        void delete(Cart cart,String id);
    }
    public  void  setData(List<Cart>getList){
        this.list=getList;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cart,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart=list.get(position);
        if(cart==null){
            return;
        }
        holder.tv_Quantity.setText(cart.getQuantity()+"");
        holder.tv_Name.setText(cart.getFood().getName());
        Picasso.get().load(cart.getFood().getImage()).into(holder.img_Food);
        holder.tv_Price.setText(cart.getFood().getPrice()+"");


        holder.tv_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem.delete(cart, cart.getId());

            }
        });



    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return  list.size();
        }
        return 0;
    }

    public  class  CartViewHolder extends RecyclerView.ViewHolder{
        TextView tv_Name,tv_Price,tv_Quantity,tv_Delete;
        ImageView img_Food;
        ImageView img_Plus,img_Minus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Price=itemView.findViewById(R.id.tv_price_food);
            tv_Name=itemView.findViewById(R.id.tv_name_food);
            tv_Quantity=itemView.findViewById(R.id.tv_quantity);
            img_Food=itemView.findViewById(R.id.img_food_detail);
            tv_Delete=itemView.findViewById(R.id.tv_delete);
            img_Minus=itemView.findViewById(R.id.img_minus);
            img_Plus=itemView.findViewById(R.id.img_plus);
        }
    }
}
