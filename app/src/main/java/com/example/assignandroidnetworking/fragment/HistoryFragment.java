package com.example.assignandroidnetworking.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignandroidnetworking.R;
import com.example.assignandroidnetworking.adapter.CartAdapter;
import com.example.assignandroidnetworking.adapter.HistoryAdapter;
import com.example.assignandroidnetworking.api.Api;
import com.example.assignandroidnetworking.modal.Cart;
import com.example.assignandroidnetworking.modal.History;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    RecyclerView rcv_History;
    HistoryAdapter historyAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String  _id="";
    ImageView img_Back;
    TextView tv_Empty;
    Boolean isLogin;
    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.history_fragment,container,false);
        rcv_History=view.findViewById(R.id.rcv_history);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        rcv_History.setLayoutManager(linearLayoutManager);
        historyAdapter=new HistoryAdapter();
        img_Back=view.findViewById(R.id.img_back);
        tv_Empty=view.findViewById(R.id.tv_empty);
        return view;
    }
    private void fetchData() {
        if (_id.isEmpty()) {
            return;
        }
        String API_URL = Api.api+"/history/:" + _id;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Type cartListType = new TypeToken<ArrayList<History>>(){}.getType();
                        ArrayList<History> historyList = gson.fromJson(response.toString(), cartListType);
                        rcv_History.setLayoutManager(linearLayoutManager);
                        historyAdapter.setData(historyList);
                        rcv_History.setAdapter(historyAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.getMessage(), error);
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonArrayRequest);
    }
    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences=getActivity().getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        isLogin = sharedPreferences.getBoolean("isLogin",false);
        _id = sharedPreferences.getString("_id","");
        if(isLogin){
            fetchData();

        }
    }


}
