package com.example.assignandroidnetworking.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignandroidnetworking.R;
import com.example.assignandroidnetworking.api.Api;
import com.example.assignandroidnetworking.fragment.HomeFragment;
import com.example.assignandroidnetworking.modal.Cart;
import com.example.assignandroidnetworking.modal.Food;
import com.example.assignandroidnetworking.modal.History;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class MuaNgayActivity extends AppCompatActivity {
    Food food;
    ImageView imgFood;
    TextView tvName,tvQuantity,tvTotalPrice;
    TextView tvCancel,tvOrder;
    String paymentMethod,name,phone,adress;
    EditText edtName,edtPhone,edtAdress,edtPaymentMethod;
    ImageView imgPlus,imgMinus;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String  _id="";
    Boolean isLogin;

    int quantity=1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_ngay);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        food=(Food)bundle.getSerializable("food");
        tvName=findViewById(R.id.tv_name);
        tvQuantity = findViewById(R.id.tv_quantity);
        edtName=findViewById(R.id.edt_name);
        edtAdress=findViewById(R.id.edt_adress);
        edtPhone=findViewById(R.id.edt_phone);
        imgPlus=findViewById(R.id.img_plus);
        imgMinus=findViewById(R.id.img_minus);
        edtPaymentMethod=findViewById(R.id.edt_payment_method);
        tvTotalPrice=findViewById(R.id.tv_total_all);
        imgFood=findViewById(R.id.img_food);
        tvOrder=findViewById(R.id.tv_order);
        tvCancel=findViewById(R.id.tv_cancel);
        tvName.setText(food.getName());
        tvTotalPrice.setText(food.getPrice()+"");
        tvQuantity.setText(quantity+"");

        Picasso.get().load(food.getImage()).into(imgFood);
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                tvQuantity.setText(quantity+"");
            }
        });
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity==1){
                    quantity=1;
                    tvQuantity.setText(quantity+"");
                }else {
                    quantity--;
                    tvQuantity.setText(quantity+"");

                }
            }
        });
        tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInformation();

                finish();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    private  void getInformation(){
        getData();
        if(name.length()==0||phone.length()==0||adress.length()==0){
            Toast.makeText(this, "Bạn Cần Phải Điền Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
            return;
        }
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        UUID uuid = UUID.randomUUID();
        String randomUUIDCart = uuid.toString();
        Cart cart = new Cart(food,quantity,randomUUIDCart);
        String API_URL = Api.api+"/buyNow/:"+_id;
        JSONObject infoHistory = new JSONObject();
        String randomUUIDHistory = uuid.toString();
        try {
            infoHistory.put("price",cart.getFood().getPrice());
            infoHistory.put("name",cart.getFood().getName());
            infoHistory.put("quantity",cart.getQuantity());
            infoHistory.put("fullName",name);
            infoHistory.put("address",adress);
            infoHistory.put("paymentMethod",paymentMethod);
            infoHistory.put("phone",phone);
            infoHistory.put("time",timeStamp);
            infoHistory.put("id",randomUUIDHistory);
            infoHistory.put("_id",_id );
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    API_URL,
                    infoHistory,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Xử lý response
                            Toast.makeText(MuaNgayActivity.this," Mua Hàng Thành Công",Toast.LENGTH_LONG).show();

                            HomeFragment.tv_notification_count_.setVisibility(View.VISIBLE);
                            HomeFragment.tv_notification_count_.setText(++ HomeFragment.notifyCount +"");
                            editor.putInt("notificationCount"+_id,HomeFragment.notifyCount);
                            editor.apply();


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Xử lý lỗi
                            Toast.makeText(MuaNgayActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
            );
            queue.add(request);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }
    private  void getData(){
        paymentMethod=edtPaymentMethod.getText().toString();
        name=edtName.getText().toString().trim();
        phone=edtPhone.getText().toString().trim();
        adress=edtAdress.getText().toString().trim();
    }
    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences=getSharedPreferences("infoUser",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        isLogin = sharedPreferences.getBoolean("isLogin",false);
        _id = sharedPreferences.getString("_id","");

        if(!isLogin){
            HomeFragment.tv_notification_count_.setVisibility(View.GONE);
        }
    }
}