package com.example.assignandroidnetworking.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignandroidnetworking.R;
import com.example.assignandroidnetworking.activity.AccountActivity;
import com.example.assignandroidnetworking.activity.DetailActicity;
import com.example.assignandroidnetworking.activity.NotifyActivity;
import com.example.assignandroidnetworking.adapter.FoodAdapter;
import com.example.assignandroidnetworking.api.Api;
import com.example.assignandroidnetworking.modal.Food;
import com.example.assignandroidnetworking.modal.History;
import com.example.assignandroidnetworking.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment  {
    FoodAdapter foodAdapter ;
    RecyclerView rcv_Home;
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
    ImageView img_NotifyCation,img_Account;
    ImageView imgSearch;
    EditText edt_Search;
    TextView tv_Tat_Ca,tv_Tra_Sua,tv_Banh_Mi,tv_Pizza,tv_Empty;
    String search = "";
    String _id ="";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static TextView tv_notification_count_;
    public static int notifyCount = 0 ;
    Boolean isLogin ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home_fragment,container,false);
        super.onCreate(savedInstanceState);
        unitUi(view);
        tv_Tat_Ca.setFocusable(true);
        if(tv_Tat_Ca.isFocusable()){
            tv_Tat_Ca.setBackground(getResources().getDrawable(R.drawable.cs_category_checked));
        }
        tv_Tat_Ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_Tra_Sua.setFocusable(false);
                tv_Banh_Mi.setFocusable(false);
                tv_Pizza.setFocusable(false);
                if(!tv_Tra_Sua.isFocusable()||tv_Pizza.isFocusable()||tv_Banh_Mi.isFocusable()){
                    tv_Tra_Sua.setBackground(getResources().getDrawable(R.drawable.cs_category_no_checked));
                    tv_Banh_Mi.setBackground(getResources().getDrawable(R.drawable.cs_category_no_checked));
                    tv_Pizza.setBackground(getResources().getDrawable(R.drawable.cs_category_no_checked));
                }
                tv_Tat_Ca.setFocusable(true);
                if(tv_Tat_Ca.isFocusable()){
                    tv_Tat_Ca.setBackground(getResources().getDrawable(R.drawable.cs_category_checked));
                }

                    fetchData("All",false);


            }
        });
        tv_Tra_Sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_Tat_Ca.setFocusable(false);
                tv_Banh_Mi.setFocusable(false);
                tv_Pizza.setFocusable(false);
                if(!tv_Tat_Ca.isFocusable()||tv_Pizza.isFocusable()||tv_Banh_Mi.isFocusable()){
                    tv_Tat_Ca.setBackground(getResources().getDrawable(R.drawable.cs_category_no_checked));
                    tv_Banh_Mi.setBackground(getResources().getDrawable(R.drawable.cs_category_no_checked));
                    tv_Pizza.setBackground(getResources().getDrawable(R.drawable.cs_category_no_checked));
                }
                tv_Tra_Sua.setFocusable(true);
                if(tv_Tra_Sua.isFocusable()){
                    tv_Tra_Sua.setBackground(getResources().getDrawable(R.drawable.cs_category_checked));
                }

                    fetchData("tea_milk",false);


            }
        });
        tv_Banh_Mi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_Tat_Ca.setFocusable(false);
                tv_Tra_Sua.setFocusable(false);
                tv_Pizza.setFocusable(false);
                if(!tv_Tra_Sua.isFocusable()||tv_Pizza.isFocusable()||tv_Tat_Ca.isFocusable()){
                    tv_Tra_Sua.setBackground(getResources().getDrawable(R.drawable.cs_category_no_checked));
                    tv_Tat_Ca.setBackground(getResources().getDrawable(R.drawable.cs_category_no_checked));
                    tv_Pizza.setBackground(getResources().getDrawable(R.drawable.cs_category_no_checked));
                }

                tv_Banh_Mi.setFocusable(true);
                if(tv_Banh_Mi.isFocusable()){
                    tv_Banh_Mi.setBackground(getResources().getDrawable(R.drawable.cs_category_checked));
                }


                    fetchData("bread",false);


            }
        });
        tv_Pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_Tat_Ca.setFocusable(false);
                tv_Banh_Mi.setFocusable(false);
                tv_Tra_Sua.setFocusable(false);
                if(!tv_Tra_Sua.isFocusable()||tv_Tat_Ca.isFocusable()||tv_Banh_Mi.isFocusable()){
                    tv_Tra_Sua.setBackground(getResources().getDrawable(R.drawable.cs_category_no_checked));
                    tv_Banh_Mi.setBackground(getResources().getDrawable(R.drawable.cs_category_no_checked));
                    tv_Tat_Ca.setBackground(getResources().getDrawable(R.drawable.cs_category_no_checked));
                }

                tv_Pizza.setFocusable(true);
                if(tv_Pizza.isFocusable()){
                    tv_Pizza.setBackground(getResources().getDrawable(R.drawable.cs_category_checked));
                }

                    fetchData("pizza",false);


            }
        });
        img_NotifyCation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("notificationCount"+_id,0);
                editor.apply();
                startActivity(new Intent(getActivity(), NotifyActivity.class));

            }
        });
        img_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AccountActivity.class);
                startActivity(intent);

            }
        });

        edt_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }
            @Override
            public void afterTextChanged(Editable s) {
                search = s.toString();
                Utils.hideSoftKeyboard(getActivity());
               fetchData(search,true);
            }
        });

        foodAdapter =new FoodAdapter(new FoodAdapter.IclickDetail() {
            @Override
            public void detailFood(Food food) {
                Intent intent=new Intent(getActivity(), DetailActicity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("food",food);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        fetchData("All",false);


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences=getActivity().getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
         isLogin = sharedPreferences.getBoolean("isLogin",false);
        if(isLogin){
            _id = sharedPreferences.getString("_id","");
            notifyCount = sharedPreferences.getInt("notificationCount"+_id,0);
            tv_notification_count_.setText(notifyCount+"");

        }
        if(notifyCount == 0 ) {
            tv_notification_count_.setVisibility(View.GONE);
        }
    }

    private  void  fetchData(String key, Boolean isSearch){


        String API_URL = "";
        if(isSearch){
            API_URL = Api.api+"/search/:"+key;


        }else {
                API_URL = Api.api+"/:"+key;


        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Type cartListType = new TypeToken<ArrayList<Food>>(){}.getType();
                        ArrayList<Food> foodList = gson.fromJson(response.toString(), cartListType);
                        rcv_Home.setLayoutManager(gridLayoutManager);
                        foodAdapter.setData(foodList);
                        rcv_Home.setAdapter(foodAdapter);

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

    private  void  unitUi(View view){
        imgSearch = view.findViewById(R.id.img_search);
        edt_Search=view.findViewById(R.id.edt_search_name);
        rcv_Home=view.findViewById(R.id.rcv_home);
        tv_Banh_Mi=view.findViewById(R.id.tv_banh_mi);
        tv_Pizza=view.findViewById(R.id.tv_pizza);
        tv_Tat_Ca=view.findViewById(R.id.tv_tat_ca);
        tv_Tra_Sua=view.findViewById(R.id.tv_tra_sua);
        tv_Empty=view.findViewById(R.id.tv_empty);
        img_Account=view.findViewById(R.id.img_account);
        img_NotifyCation=view.findViewById(R.id.img_notifycation);
        tv_notification_count_ =view. findViewById(R.id.tv_notification_count);


    }






}
