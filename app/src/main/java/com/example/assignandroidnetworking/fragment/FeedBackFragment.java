package com.example.assignandroidnetworking.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignandroidnetworking.R;
import com.example.assignandroidnetworking.activity.SingUpActivity;
import com.example.assignandroidnetworking.api.Api;

import org.json.JSONException;
import org.json.JSONObject;


public class FeedBackFragment extends Fragment {
    TextView tv_Gui_Phan_Hoi;
    EditText edt_Binh_Luan;
    String binhLuan;
    ImageView img_Back;
    public static final String API_URL = Api.api+"/feedback";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.feedback_fragment,container,false);
        edt_Binh_Luan=view.findViewById(R.id.edt_binh_luan);
        tv_Gui_Phan_Hoi=view.findViewById(R.id.tv_gui_phan_hoi);
        img_Back=view.findViewById(R.id.img_back);
        tv_Gui_Phan_Hoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binhLuan=edt_Binh_Luan.getText().toString().trim();
                if(binhLuan.length()==0){
                    Toast.makeText(getActivity(), "Bạn Phải Điền Đẩy Đủ Thông Tin Cần Thiết", Toast.LENGTH_SHORT).show();
                }else {
                    JSONObject feedback = new JSONObject();
                    try {
                        feedback.put("binhLuan",binhLuan);
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        JsonObjectRequest request = new JsonObjectRequest(
                                Request.Method.POST,
                                API_URL,
                                feedback,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Xử lý response
                                        Toast.makeText(getActivity(), "Cảm Ơn Những Đóng Góp Của Bạn", Toast.LENGTH_SHORT).show();

                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                edt_Binh_Luan.setText("");
                                            }
                                        });
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // Xử lý lỗi
                                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();

                                    }
                                }
                        );
                        queue.add(request);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                }

            }
        });
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return view;
    }



}
