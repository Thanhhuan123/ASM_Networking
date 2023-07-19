package com.example.assignandroidnetworking.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignandroidnetworking.R;
import com.example.assignandroidnetworking.adapter.ContactLogoAdapter;
import com.example.assignandroidnetworking.modal.ContactLogo;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
    TextView tv_Slogan;
    List<ContactLogo>list;
    GridLayoutManager gridLayoutManager;
    RecyclerView rcv_Contact;
    ContactLogoAdapter contactLogoAdapter;
    ImageView img_Back;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.contact_fragment,container,false);
        list=new ArrayList<>();
        contactLogoAdapter=new ContactLogoAdapter();
        rcv_Contact=view.findViewById(R.id.rcv_contact);
        tv_Slogan=view.findViewById(R.id.tv_slogan);
        tv_Slogan.setText("Assignment AndroidNetWorking");
        gridLayoutManager=new GridLayoutManager(getActivity(),3);
        rcv_Contact.setLayoutManager(gridLayoutManager);
        rcv_Contact.setAdapter(contactLogoAdapter);
        img_Back=view.findViewById(R.id.img_back);
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        setData();
        return view;
    }
    private  void setData(){
        if(list.size()>0){
            list.clear();
        }
        list.add(new ContactLogo(R.drawable.face,"Facebook","https://www.facebook.com/profile.php?id=100035202136142"));
        list.add(new ContactLogo(R.drawable.call_phone,"Call Phone","tel:0327596141"));
        list.add(new ContactLogo(R.drawable.gmail,"Gmail","mailto:dath33603@gmail.com"));
        list.add(new ContactLogo(R.drawable.ytb,"Youtube","https://www.youtube.com/channel/UCTnmDPuAMb8UR6YNSVWb3mw"));
        list.add(new ContactLogo(R.drawable.zalo,"Zalo","https://chat.zalo.me/"));
        contactLogoAdapter.setData(list);

    }

}
