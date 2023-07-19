package com.example.assignandroidnetworking.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignandroidnetworking.R;
import com.example.assignandroidnetworking.adapter.NotifyAdapter;
import com.example.assignandroidnetworking.api.Api;
import com.example.assignandroidnetworking.modal.Food;
import com.example.assignandroidnetworking.modal.Notify;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotifyActivity extends AppCompatActivity {
    RecyclerView rcv_Notify;
    NotifyAdapter notifyAdapter;
    LinearLayoutManager linearLayoutManager;
    List<Notify>listNotify=new ArrayList<>();
    TextView tv_Empty;
    public long diff, diffSeconds ,diffMinutes,diffHours;
    public String timeEnd;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView img_Back;
    String _id ="";
    Boolean isLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        rcv_Notify=findViewById(R.id.rcv_notify);
        linearLayoutManager=new LinearLayoutManager(this);
        rcv_Notify.setLayoutManager(linearLayoutManager);
        notifyAdapter=new NotifyAdapter(this);
        tv_Empty=findViewById(R.id.tv_empty);
        rcv_Notify.setAdapter(notifyAdapter);
        img_Back=findViewById(R.id.img_back);
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    private  void  fetchData(){
        if(_id.equals("")){
            return;
        }

        // Khởi tạo RequestQueue
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String API_URL = Api.api+"/notify/:"+_id;

        // Tạo StringRequest để gọi API
        StringRequest request = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(listNotify.size()!=0){
                            listNotify.clear();
                        }
                        // Chuyển đổi chuỗi JSON thành danh sách các đối tượng Java
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Notify>>(){}.getType();
                        List<Notify> list = gson.fromJson(response, type);

                        // Hiển thị danh sách các đối tượng Java
                        for (Notify notify : list) {
                            listNotify.add(0,notify);

                        }
                        rcv_Notify.setLayoutManager(linearLayoutManager);

                        notifyAdapter.setData(listNotify);
                        rcv_Notify.setAdapter(notifyAdapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        // Thêm yêu cầu vào hàng đợi và thực hiện nó
        queue.add(request);

    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences=getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        isLogin = sharedPreferences.getBoolean("isLogin",false);
        _id = sharedPreferences.getString("_id","");
        if(isLogin) {
            fetchData();
        }



    }


    public void handlerTimes(String timeStart){
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        timeEnd=  format.format(date);
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(timeStart);
            d2 = format.parse(timeEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(d2!=null && d1!=null){
            diff = d2.getTime() - d1.getTime();

        }
        // Get msec from each, and subtract.
         diffSeconds = diff / 1000 % 60;
         diffMinutes = diff / (60 * 1000) % 60;
         diffHours = diff / (60 * 60 * 1000);
    }
}




