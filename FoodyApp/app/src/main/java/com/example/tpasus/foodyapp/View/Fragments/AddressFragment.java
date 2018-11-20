package com.example.tpasus.foodyapp.View.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.tpasus.foodyapp.Controller.AddressController;
import com.example.tpasus.foodyapp.Model.QuanAnModel;
import com.example.tpasus.foodyapp.R;

import java.util.List;

public class AddressFragment extends Fragment {
    AddressController addressController;
    RecyclerView recyclerAddress;
    ProgressBar progressBarAddress;
    //SharedPreferences sharedPreferences;
   // NestedScrollView nestedScrollView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.layout_fragment_address, container,false);
        recyclerAddress= view.findViewById(R.id.recyclerAddress);
       // addressController=new AddressController(getContext());
       progressBarAddress=view.findViewById(R.id.progressBarAddress);
        //nestedScrollView = view.findViewById(R.id.nestedScrollView_Address);

     //   addressController.getDinerListController(nestedScrollView, recyclerAddress, progressBarAddress);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//       sharedPreferences = getContext().getSharedPreferences("save_location", Context.MODE_PRIVATE);
//        Location currentlocation = new Location("");
//        currentlocation.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
//        currentlocation.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
        // Log.d("kiemtratoado",sharedPreferences.getString("latitude","0") + "");
        //khi fragmentAddress chạy lên khởi tạo controllAddress get danh sách quán ăn từ model truyền vào recycler (đã gán adapter vào)
         addressController=new AddressController(getContext());
         addressController.getDinerListController(/*nestedScrollView, */recyclerAddress,progressBarAddress/*currentlocation*/);
    }
}
