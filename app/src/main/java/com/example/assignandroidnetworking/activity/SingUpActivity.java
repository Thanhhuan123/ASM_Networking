package com.example.assignandroidnetworking.activity;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignandroidnetworking.R;
import com.example.assignandroidnetworking.api.Api;

import org.json.JSONException;
import org.json.JSONObject;

public class SingUpActivity extends AppCompatActivity {
    EditText edt_Email,edt_Pass_Word;
    TextView tv_Sing_Up;
    String email,passWord;
    public static final String API_URL = Api.api+"/singup";
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        img_back=findViewById(R.id.img_back);
        edt_Email=findViewById(R.id.edt_email);
        edt_Pass_Word=findViewById(R.id.edt_pass_word);
        tv_Sing_Up=findViewById(R.id.tv_sing_up);
        tv_Sing_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                if(passWord.length()==0||email.length()==0){
                    Toast.makeText(SingUpActivity.this,"Vui Lòng Nhập Đầy Đủ Thông Tin",Toast.LENGTH_LONG).show();
                    return;
                }
                JSONObject user = new JSONObject();
                try {


                    user.put("email",email );
                    user.put("password", passWord);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.POST,
                            API_URL,
                            user,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Xử lý response
                                    Toast.makeText(SingUpActivity.this,"Đăng Ký Tài Khoản Thành Công",Toast.LENGTH_LONG).show();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Clear các trường nhập liệu
                                            edt_Email.setText("");
                                            edt_Pass_Word.setText("");
                                        }
                                    });
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Xử lý lỗi
                                    Toast.makeText(SingUpActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();

                                }
                            }
                    );
                    queue.add(request);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }





            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private  void getData(){
        email=edt_Email.getText().toString().trim();
        passWord=edt_Pass_Word.getText().toString().trim();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}