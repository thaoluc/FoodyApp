package com.example.tpasus.foodyapp.Model;

import android.graphics.Bitmap;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.tpasus.foodyapp.Controller.Interfaces.AddressInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanAnModel {

    boolean giaohang;
    String giodongcua, giomocua, tenquanan, videogioithieu, maquanan;
    List<String> tienich;
    List<String> hinhanhquanan;
    List<CommentModel> commentModelList;
    //List<Bitmap> bitmapList;
    //List<BranchDinerModel> branchDinerModelList;
    long luotthich;
    DatabaseReference nodeRoot;
    //DatabaseReference dataQuanAn;

    public QuanAnModel() {
      //  dataQuanAn = FirebaseDatabase.getInstance().getReference().child("quanans");
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    public List<CommentModel> getCommentModelList() {
        return commentModelList;
    }

    public void setCommentModelList(List<CommentModel> commentModelList) {
        this.commentModelList = commentModelList;
    }

//    public List<Bitmap> getBitmapList() {
//        return bitmapList;
//    }
//
//    public void setBitmapList(List<Bitmap> bitmapList) {
//        this.bitmapList = bitmapList;
//    }

//    public List<BranchDinerModel> getBranchDinerModelList() {
//        return branchDinerModelList;
//    }
//
//    public void setBranchDinerModelList(List<BranchDinerModel> branchDinerModelList) {
//        this.branchDinerModelList = branchDinerModelList;
//    }

    //DataSnapshot dataRoot;
    public void getDanhSachQuanAn(final AddressInterface addressInterface/*, final Location currentlocation, final int itemdaload, final int itemtieptheo*/) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {      // dataSnapshot: root
                DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");    //lấy ra các maquanan
                //lấy danh sách quán ăn
                for (DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren())   //lấy ra các value của từng maquanan (thuộc tính + giá trị)
                {

                    //lấy ra các giá trị ép về kiểu object QuanAnModel
                    QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
                    quanAnModel.setMaquanan(valueQuanAn.getKey());
                    //Log.d("kiemtra",quanAnModel.getTenquanan());

                    //lấy danh sách hình ảnh của quán ăn theo mã
                    DataSnapshot dataSnapshot1DinerPic = dataSnapshot.child("hinhanhquanans").child(valueQuanAn.getKey());
                     //Log.d("kiemtraMa",valueQuanAn.getKey());
                    List<String> pictureList = new ArrayList<>();
                    for (DataSnapshot valueDinerPic : dataSnapshot1DinerPic.getChildren()) {
                      //  Log.d("kiemtra",valueDinerPic.getValue() + "");
                        pictureList.add(valueDinerPic.getValue(String.class));
                    }
                    quanAnModel.setHinhanhquanan(pictureList);

                    //lấy danh sách bình luận của quán ăn
                    DataSnapshot dataSnapshotComment = dataSnapshot.child("binhluans").child(valueQuanAn.getKey()); //bình luận theo mã quán ăn
                    List<CommentModel> commentModels = new ArrayList<>();
                    for (DataSnapshot valueComment : dataSnapshotComment.getChildren())    //mã bình luận
                    {
                        CommentModel commentModel = valueComment.getValue(CommentModel.class);
                        commentModel.setMabinhluan(valueComment.getKey());

                        MemberModel memberModel = dataSnapshot.child("thanhviens").child(commentModel.getMauser()).getValue(MemberModel.class);
                        commentModel.setMemberModel(memberModel);

                        //lấy danh sách hình ảnh trong comment của 1 quán ăn
                        List<String> photocmtsList = new ArrayList<>();
                        DataSnapshot dataSnapshotPhotoCmts = dataSnapshot.child("hinhanhbinhluans").child(commentModel.getMabinhluan());
                        for (DataSnapshot valuePhotoComments : dataSnapshotPhotoCmts.getChildren()) {
                            photocmtsList.add(valuePhotoComments.getValue(String.class));
                        }
                        commentModel.setPhotoCmtsList(photocmtsList);
                        commentModels.add(commentModel);
                    }
                    quanAnModel.setCommentModelList(commentModels);

//            //lấy chi nhánh quán ăn
//            DataSnapshot dataSnapshotBranch = dataSnapshot.child("chinhanhquanans").child(valueQuanAn.getKey()); //bình luận theo mã quán ăn
//            List<BranchDinerModel> branchDinerModels = new ArrayList<>();
//
//            for(DataSnapshot valueBranch:dataSnapshotBranch.getChildren()){
//                BranchDinerModel branchDinerModel = valueBranch.getValue(BranchDinerModel.class);
//                //vị trí quán ăn
//                Location dinerlocation = new Location("");
//                dinerlocation.setLatitude(branchDinerModel.getLatitude());
//                dinerlocation.setLongitude(branchDinerModel.getLongitude());
//
//                double distance = currentlocation.distanceTo(dinerlocation)/1000;
//                branchDinerModel.setKhoangcach(distance);
//
//                branchDinerModels.add(branchDinerModel);
//            }
//            quanAnModel.setBranchDinerModelList(branchDinerModels);
                    addressInterface.getDanhSachQuanAn(quanAnModel);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
//        if(dataRoot != null){
//            LayDanhSachQuanAn(dataRoot, addressInterface, /*currentlocation,*/ itemdaload, itemtieptheo);
//        }else
//        {
//            nodeRoot.addListenerForSingleValueEvent(valueEventListener);    //gọi code download trên server
//        }
        };
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }

//   private void LayDanhSachQuanAn(DataSnapshot dataSnapshot, AddressInterface addressInterface, //Location currentlocation,
//                                   int itemdaload, int itemtieptheo )
//    {
//        DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");    //lấy ra các maquanan
//        //lấy danh sách quán ăn
//        int i = 0;  //đếm bao nhiêu sp đã đc load, vòng lặp for đã đc duyệt bn lần
//        for(DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren())   //lấy ra các value của từng maquanan (thuộc tính + giá trị)
//        {
//            if(i == itemtieptheo)   //load số item mặc định
//            {break;}
//
//            if(i < itemdaload)
//            {
//                i++;
//                continue;
//            }
//            i++;
//            //lấy ra các giá trị ép về kiểu object QuanAnModel
//            QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
//            quanAnModel.setMaquanan(maquanan);
//            //Log.d("kiemtra",quanAnModel.getTenquanan());
//                    /*add vào list
//                    quanAnModelList.add(quanAnModel);*/
//
//
//            //lấy danh sách hình ảnh của quán ăn theo mã
//            DataSnapshot dataSnapshot1DinerPic = dataSnapshot.child("hinhanhquanans").child(valueQuanAn.getKey());
//            // Log.d("kiemtraMa",valueQuanAn.getKey());
//            List<String> pictureList = new ArrayList<>();
//            for(DataSnapshot valueDinerPic : dataSnapshot1DinerPic.getChildren()){
//                // Log.d("kiemtra",valueDinerPic.getValue() + "");
//                pictureList.add(valueDinerPic.getValue(String.class));
//            }
//            quanAnModel.setHinhanhquanan(pictureList);
//
//            //lấy danh sách bình luận của quán ăn
//            DataSnapshot dataSnapshotComment = dataSnapshot.child("binhluans").child(valueQuanAn.getKey()); //bình luận theo mã quán ăn
//            List<CommentModel> commentModels = new ArrayList<>();
//            for(DataSnapshot valueComment:dataSnapshotComment.getChildren())    //mã bình luận
//            {
//                CommentModel commentModel= valueComment.getValue(CommentModel.class);
//                commentModel.setMabinhluan(valueComment.getKey());
//
//                MemberModel memberModel = dataSnapshot.child("thanhviens").child(commentModel.getMauser()).getValue(MemberModel.class);
//                commentModel.setMemberModel(memberModel);
//
//                //lấy danh sách hình ảnh trong comment của 1 quán ăn
//                List<String> photocmtsList = new ArrayList<>();
//                DataSnapshot dataSnapshotPhotoCmts = dataSnapshot.child("hinhanhbinhluans").child(commentModel.getMabinhluan());
//                for (DataSnapshot valuePhotoComments : dataSnapshotPhotoCmts.getChildren()){
//                    photocmtsList.add(valuePhotoComments.getValue(String.class));
//                }
//                commentModel.setPhotoCmtsList(photocmtsList);
//                commentModels.add(commentModel);
//            }
//            quanAnModel.setCommentModelList(commentModels);
//
//         /*   //lấy chi nhánh quán ăn
//            DataSnapshot dataSnapshotBranch = dataSnapshot.child("chinhanhquanans").child(valueQuanAn.getKey()); //bình luận theo mã quán ăn
//            List<BranchDinerModel> branchDinerModels = new ArrayList<>();
//
//            for(DataSnapshot valueBranch:dataSnapshotBranch.getChildren()){
//                BranchDinerModel branchDinerModel = valueBranch.getValue(BranchDinerModel.class);
//                //vị trí quán ăn
//                Location dinerlocation = new Location("");
//                dinerlocation.setLatitude(branchDinerModel.getLatitude());
//                dinerlocation.setLongitude(branchDinerModel.getLongitude());
//
//                double distance = currentlocation.distanceTo(dinerlocation)/1000;
//                branchDinerModel.setKhoangcach(distance);
//
//                branchDinerModels.add(branchDinerModel);
//            }
//            quanAnModel.setBranchDinerModelList(branchDinerModels);*/
//
//            addressInterface.getDanhSachQuanAn(quanAnModel);
//        }
    }


    // Đa tiến trình
//    List<QuanAnModel> quanAnModelList; //danh sách quán ăn
//    public List<QuanAnModel> getDanhSachQuanAn(){
//        quanAnModelList = new ArrayList<>();
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //dataSnapshot: có value bao gồm các maquanan
//                //duyệt các value trong từng maquanan, value có cấu trúc dạng QuanAnModel
//                for(DataSnapshot dataValue:dataSnapshot.getChildren())
//                {
//                    QuanAnModel quanAnModel = dataValue.getValue(QuanAnModel.class);  //lấy gtri của value ép về QuanAnModel
//                    Log.d("kiemtra",quanAnModel.getTenquanan());
//                    quanAnModelList.add(quanAnModel); //add quán ăn vào list
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//        dataQuanAn.addListenerForSingleValueEvent(valueEventListener);
//        return quanAnModelList;
//    }

