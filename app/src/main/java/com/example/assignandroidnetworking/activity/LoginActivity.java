package com.example.assignandroidnetworking.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignandroidnetworking.MainActivity;
import com.example.assignandroidnetworking.R;
import com.example.assignandroidnetworking.api.Api;
import com.example.assignandroidnetworking.modal.Food;
import com.example.assignandroidnetworking.modal.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity  {
    private static final String API_URL = Api.api+"/login";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText edt_Email,edt_PassWord;
    String email,passWord;
    TextView tv_Login;
    ImageView img_Back;
    ArrayList<User>listUser = new ArrayList<>();
    Boolean check= true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unitUi();
        fetchData();
        tv_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                for(int i = 0 ; i< listUser.size();i++){
                    User user = listUser.get(i);
                    if(user.getEmail().equals(email)&& user.getPassword().equals(passWord)){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        editor.putString("email",email);
                        editor.putString("password",passWord);
                        editor.putBoolean("isLogin",true);
                        editor.putString("_id",user.get_id());
                        int notify =  sharedPreferences.getInt("notificationCount"+user.get_id(),0);
                        editor.putInt("notificationCount"+user.get_id(),notify);
                        editor.apply();
                        check = false;
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        break;
                    }
                }
                if(check){
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }

            }
        });
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private  void unitUi(){
        edt_Email=findViewById(R.id.edt_email);
        edt_PassWord=findViewById(R.id.edt_pass_word);
        tv_Login=findViewById(R.id.tv_login);
        img_Back=findViewById(R.id.img_back);
    }

    private  void getData(){
        email=edt_Email.getText().toString().trim();
        passWord=edt_PassWord.getText().toString().trim();
    }

    private void fetchData(){
        // Khởi tạo RequestQueue
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        // Tạo StringRequest để gọi API
        StringRequest request = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Chuyển đổi chuỗi JSON thành danh sách các đối tượng Java
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<User>>(){}.getType();
                        List<User> users = gson.fromJson(response, type);

                        // Hiển thị danh sách các đối tượng Java
                        for (User user : users) {
                            listUser.add(0,user);

                        }


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
        sharedPreferences=getSharedPreferences("infoUser",MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

}