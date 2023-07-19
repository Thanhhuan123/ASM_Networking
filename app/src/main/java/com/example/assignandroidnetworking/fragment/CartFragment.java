package com.example.assignandroidnetworking.fragment;

import static com.example.assignandroidnetworking.activity.SingUpActivity.API_URL;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignandroidnetworking.R;
import com.example.assignandroidnetworking.activity.MuaNgayActivity;
import com.example.assignandroidnetworking.activity.SingUpActivity;
import com.example.assignandroidnetworking.adapter.CartAdapter;
import com.example.assignandroidnetworking.api.Api;
import com.example.assignandroidnetworking.modal.Cart;
import com.example.assignandroidnetworking.modal.Food;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CartFragment extends Fragment {
    CartAdapter cartAdapter;
    RecyclerView rcv_Cart;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView tv_Total_All,tv_Order,tv_Empty_Cart;
    private BottomSheetBehavior bottomSheetBehavior;
    RelativeLayout layout_Bottom_Sheet;
    TextView tv_Name,tv_Quantity,tv_Total_All_Sheet;
    TextView tv_Order_Sheet,tv_Cancel_Sheet;
    EditText edt_Name,edt_Phone,edt_Adress,edt_Payment_Method;
    String paymentMethod,name ,phone,adress;
    ImageView img_Back;
    String  _id="";
    Boolean isLogin;
    int total = 0;
    ArrayList<Cart> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.cart_fragment,container,false);
        unitUi(view);
        tv_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    String title="";
                    String quantity="";
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getFood().getName().length()>25){
                            title+=list.get(i).getFood().getName()+"("+list.get(i).getFood().getPrice()+")"+"\n";
                            quantity+="- Số Lượng:"+" "+list.get(i).getQuantity()+"\n"+"\n";
                        }else {
                            title+=list.get(i).getFood().getName()+"("+list.get(i).getFood().getPrice()+")"+"\n";
                            quantity+="- Số Lượng:"+" "+list.get(i).getQuantity()+"\n";
                        }
                    }
                    tv_Name.setText(title);
                    tv_Quantity.setText(quantity);
                    tv_Total_All.setText(total+"vnd");
                    tv_Order_Sheet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getData();
                            if(name.length()==0 || phone.length() ==0 ||paymentMethod.length()==0){
                                Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            for(int i = 0 ; i <list.size();i++){
                                String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                                String API_URL = Api.api+"/buyNow/:"+_id;
                                JSONObject infoCart = new JSONObject();
                                UUID uuid = UUID.randomUUID();
                                String randomUUID = uuid.toString();
                                try {
                                    infoCart.put("price",list.get(i).getFood().getPrice());
                                    infoCart.put("name",list.get(i).getFood().getName());
                                    infoCart.put("quantity",list.get(i).getQuantity());
                                    infoCart.put("fullName",name);
                                    infoCart.put("address",adress);
                                    infoCart.put("paymentMethod",paymentMethod);
                                    infoCart.put("phone",phone);
                                    infoCart.put("time",timeStamp);
                                    infoCart.put("id",randomUUID);
                                    infoCart.put("_id",_id );
                                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                                    JsonObjectRequest request = new JsonObjectRequest(
                                            Request.Method.POST,
                                            API_URL,
                                            infoCart,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {


                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // Xử lý lỗi
                                                    Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
                                                    return;

                                                }
                                            }
                                    );
                                    queue.add(request);

                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }

                            }

                            Toast.makeText(getActivity(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();


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
        });

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return view;
    }
    private  void unitUi(View view){
        cartAdapter=new CartAdapter(new CartAdapter.IClick() {
           @Override
           public void delete(Cart cart,String id) {
               AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

               builder.setMessage("Bạn Có Muốn Chắc Chắn Xóa Sản Phẩm "+" "+cart.getFood().getName()+"Này Không").setTitle("Xóa")
                       .setPositiveButton("Không", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                           }
                       })
                       .setNegativeButton("Có", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               RequestQueue queue = Volley.newRequestQueue(getActivity());
                               String API_URL_DELETE = Api.api+"/delete/carts/:"+_id+"/:"+id;

                               JsonObjectRequest request = new JsonObjectRequest(
                                       Request.Method.POST,
                                       API_URL_DELETE,
                                       null,
                                       new Response.Listener<JSONObject>() {
                                           @Override
                                           public void onResponse(JSONObject response) {
                                               // Xử lý response
                                               Toast.makeText(getActivity(),"Xóa giỏ hàng thành công",Toast.LENGTH_LONG).show();
                                               fetchData();
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

                           }
                       });
               builder.show();
           }
       });
        rcv_Cart=view.findViewById(R.id.rcv_cart);
        tv_Order=view.findViewById(R.id.tv_order);
        layout_Bottom_Sheet=view.findViewById(R.id.layout_bottom_sheet);
        bottomSheetBehavior=BottomSheetBehavior.from(layout_Bottom_Sheet);
        rcv_Cart.setAdapter(cartAdapter);
        tv_Total_All = view.findViewById(R.id.tv_total_all);
        tv_Name=view.findViewById(R.id.tv_name);
        tv_Quantity=view.findViewById(R.id.tv_quantity);
        tv_Total_All_Sheet=view.findViewById(R.id.tv_total_all_cart);
        tv_Cancel_Sheet=view.findViewById(R.id.tv_cancel_sheet);
        tv_Order_Sheet=view.findViewById(R.id.tv_order_sheet);
        edt_Name=view.findViewById(R.id.edt_name);
        edt_Adress=view.findViewById(R.id.edt_adress);
        edt_Phone=view.findViewById(R.id.edt_phone);
        edt_Payment_Method=view.findViewById(R.id.edt_payment_method);
        tv_Empty_Cart=view.findViewById(R.id.tv_empty_cart);
        img_Back=view.findViewById(R.id.img_back);

    }
    private  void getData(){
        paymentMethod=edt_Payment_Method.getText().toString();
        name=edt_Name.getText().toString().trim();
        phone=edt_Phone.getText().toString().trim();
        adress=edt_Adress.getText().toString().trim();
    }

    private void fetchData() {
        if (_id.isEmpty()) {
            return;
        }
        String API_URL = Api.api+"/carts/:" + _id;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Type cartListType = new TypeToken<ArrayList<Cart>>(){}.getType();
                        ArrayList<Cart> cartList = gson.fromJson(response.toString(), cartListType);
                        rcv_Cart.setLayoutManager(linearLayoutManager);
                        if(list.size()!=0){
                            list.clear();
                            total = 0;
                        }
                        list= cartList;
                        cartAdapter.setData(cartList);
                        rcv_Cart.setAdapter(cartAdapter);
                        for (int i = 0 ; i < cartList.size();i++){
                            total+= cartList.get(i).getFood().getPrice()*cartList.get(i).getQuantity();
                        }
                        tv_Total_All_Sheet.setText(total+"VND");
                        tv_Total_All.setText(total+"VND");



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
    public void onStop() {
        super.onStop();
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
