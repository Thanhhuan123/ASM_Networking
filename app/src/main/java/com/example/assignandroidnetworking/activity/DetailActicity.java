package com.example.assignandroidnetworking.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignandroidnetworking.R;
import com.example.assignandroidnetworking.api.Api;
import com.example.assignandroidnetworking.modal.Cart;
import com.example.assignandroidnetworking.modal.Food;
import com.example.assignandroidnetworking.modal.User;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class DetailActicity extends AppCompatActivity  {
    ImageView img_Food;
    TextView tv_Name,tv_Price,tv_Description;
    TextView tv_Buy_Now,tv_Add_To_Cart,tv_Cancel_Sheet,tv_Add_To_Cart_Sheet;
    Food food;
    private BottomSheetBehavior bottomSheetBehavior;
    RelativeLayout layout_Bottom_Sheet;
    TextView tv_Name_Food,tv_Price_Food,tv_Quantity;
    ImageView img_Food_Detail,img_Plus,img_Minus;
    int quantity=1;
    String  _id="";



    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView img_Back;
    Boolean isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        untitUi();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        food=(Food)bundle.getSerializable("food");
        bottomSheetBehavior=BottomSheetBehavior.from(layout_Bottom_Sheet);
        setData();
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        tv_Add_To_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isLogin){
                    Toast.makeText(DetailActicity.this, "Bạn cần phải đăng nhập trước", Toast.LENGTH_SHORT).show();
                    return;
                }
                showInforDetailFood();

            }
        });
        tv_Buy_Now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isLogin){
                    Toast.makeText(DetailActicity.this, "Bạn cần phải đăng nhập trước", Toast.LENGTH_SHORT).show();
                    return;
                }
                    Intent muaNgay=new Intent(DetailActicity.this,MuaNgayActivity.class);
                    Bundle bundle_MuaNgay=new Bundle();
                    bundle_MuaNgay.putSerializable("food",food);
                    muaNgay.putExtras(bundle_MuaNgay);
                    startActivity(muaNgay);
            }
        });


    }
    private  void untitUi(){
        layout_Bottom_Sheet=findViewById(R.id.layout_bottom_sheet);
        img_Food=findViewById(R.id.img_food);
        tv_Name=findViewById(R.id.tv_name);
        tv_Price=findViewById(R.id.tv_price);
        tv_Description=findViewById(R.id.tv_description);
        tv_Buy_Now=findViewById(R.id.tv_buy_now);
        tv_Add_To_Cart=findViewById(R.id.tv_add_to_cart);
        tv_Name_Food=findViewById(R.id.tv_name_food);
        tv_Price_Food=findViewById(R.id.tv_price_food);
        img_Food_Detail=findViewById(R.id.img_food_detail);
        tv_Cancel_Sheet=findViewById(R.id.tv_cancel_sheet);
        tv_Add_To_Cart_Sheet=findViewById(R.id.tv_add_to_cart_sheet);
        img_Minus=findViewById(R.id.img_minus);
        img_Plus=findViewById(R.id.img_plus);
        tv_Quantity=findViewById(R.id.tv_quantity);
        img_Back=findViewById(R.id.img_back);
    }
    private  void setData(){
        Picasso.get().load(food.getImage()).into(img_Food);
        tv_Description.setText(food.getDescription());
        tv_Name.setText(food.getName());
        tv_Price.setText(food.getPrice()+"");

    }


    private  void showInforDetailFood(){
        if(bottomSheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            tv_Name_Food.setText(food.getName());
            tv_Price_Food.setText(food.getPrice()+"");
            Picasso.get().load(food.getImage()).into(img_Food_Detail);
            img_Minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(quantity==1){
                        quantity=1;
                    }else {
                        quantity--;

                    }
                    tv_Quantity.setText(quantity+"");

                }
            });
            img_Plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quantity++;
                    tv_Quantity.setText(quantity+"");

                }
            });



            tv_Add_To_Cart_Sheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     String API_URL = Api.api+"/addCart/:"+_id;
                    UUID uuid = UUID.randomUUID();
                    String randomUUIDCart = uuid.toString();

                    Cart cart = new Cart(food,quantity,randomUUIDCart);
                    JSONObject infoCart = new JSONObject();
                    try {
                        infoCart.put("id",cart.getId());

                        infoCart.put("image",cart.getFood().getImage());
                        infoCart.put("price",cart.getFood().getPrice());
                        infoCart.put("name",cart.getFood().getName());
                        infoCart.put("quantity",cart.getQuantity());

                        infoCart.put("_id",_id );
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        JsonObjectRequest request = new JsonObjectRequest(
                                Request.Method.POST,
                                API_URL,
                                infoCart,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Xử lý response
                                        Toast.makeText(DetailActicity.this," Đặt Hàng Thành Công",Toast.LENGTH_LONG).show();

                                    }
                                },
                        new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // Xử lý lỗi
                                        Toast.makeText(DetailActicity.this,error.getMessage(),Toast.LENGTH_LONG).show();

                                    }
                                }
                        );
                        queue.add(request);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }


                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


                }
            });
            tv_Cancel_Sheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            });


        }else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences=getSharedPreferences("infoUser",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        isLogin = sharedPreferences.getBoolean("isLogin",false);
        _id = sharedPreferences.getString("_id","");


    }
}