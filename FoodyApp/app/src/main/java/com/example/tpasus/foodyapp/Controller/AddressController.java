package com.example.tpasus.foodyapp.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.tpasus.foodyapp.Adapters.AdapterRecyclerAddress;
import com.example.tpasus.foodyapp.Controller.Interfaces.AddressInterface;
import com.example.tpasus.foodyapp.Model.QuanAnModel;
import com.example.tpasus.foodyapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddressController {

    Context context;
    QuanAnModel quanAnModel;
    AdapterRecyclerAddress adapterRecyclerAddress;
   // int itemdaco = 3;

    public AddressController(Context context){
        this.context=context;
        quanAnModel=new QuanAnModel();
    }

    //@SuppressLint("NewApi")
    public void getDinerListController(/*NestedScrollView nestedScrollView, */RecyclerView recyclerAddress, final ProgressBar progressBar){ //final Location currentlocation){

        // new list
        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        //tạo 1 layoutManager quản lý recyclerAddress
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerAddress.setLayoutManager(layoutManager);
        // truyền
        adapterRecyclerAddress = new AdapterRecyclerAddress(quanAnModelList, R.layout.custom_layout_recyclerview_address);
        recyclerAddress.setAdapter(adapterRecyclerAddress);

        progressBar.setVisibility(View.VISIBLE);

//        final AddressInterface addressInterface= new AddressInterface() {
//            @Override
//            public void getDanhSachQuanAn(final QuanAnModel quanAnModel) {
//                final List<Bitmap> bitmaps = new ArrayList<>();
//                for(String linkpic : quanAnModel.getHinhanhquanan()){
//                    String imgUrl = "hinhanh/"+linkpic;
//                    //Log.d("hinhanh",quanAnModel.getHinhanhquanan().get(0));
//                    //FirebaseStorage storage = FirebaseStorage.getInstance();
//                    StorageReference storagePicture = FirebaseStorage.getInstance().getReference().child(imgUrl);
//                    long ONE_MEGABYTE = 1024 * 1024;
//                    storagePicture.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                        @Override
//                        public void onSuccess(byte[] bytes) {
////                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                            InputStream input=new ByteArrayInputStream(bytes);
//                            Bitmap bitmap = BitmapFactory.decodeStream(input);
//                            bitmaps.add(bitmap);
//                            quanAnModel.setBitmapList(bitmaps);
//
//                            if(quanAnModel.getBitmapList().size() == quanAnModel.getHinhanhquanan().size()){
//                                quanAnModelList.add(quanAnModel);
//                                adapterRecyclerAddress.notifyDataSetChanged();
//                                progressBar.setVisibility(View.GONE);   //ẩn đi khi interface kích hoạt
//                            }
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            //e.printStackTrace();
//                            Log.e("storagePicture","fail");
//                        }
//                    });
//                }
//
//
//            }
//        };

//        //load more
//        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if(v.getChildAt(v.getChildCount()-1) != null)   // ktra NestedScrollView có giá trị hay k
//                {
//                    if(scrollY >= (v.getChildAt(v.getChildCount()-1)).getMeasuredHeight()-v.getMeasuredHeight())    //getMeasuredHeight: lấy chiều cao
//                    {
//                        itemdaco += 3;
//                        quanAnModel.getDanhSachQuanAn(addressInterface, /*currentlocation,*/itemdaco,itemdaco-3);
//                    }
//                }
//            }
//        });
        //lấy dữ liệu từ model thông qua interface
        AddressInterface addressInterface = new AddressInterface() {
            @Override
            public void getDanhSachQuanAn(QuanAnModel quanAnModel) {
                quanAnModelList.add(quanAnModel);
                adapterRecyclerAddress.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);   //ẩn đi khi interface kích hoạt
            }
        };
        quanAnModel.getDanhSachQuanAn(addressInterface);
    }
}
