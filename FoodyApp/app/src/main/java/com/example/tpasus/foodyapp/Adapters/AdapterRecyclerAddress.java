package com.example.tpasus.foodyapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tpasus.foodyapp.Model.BranchDinerModel;
import com.example.tpasus.foodyapp.Model.CommentModel;
import com.example.tpasus.foodyapp.Model.QuanAnModel;
import com.example.tpasus.foodyapp.R;
import com.example.tpasus.foodyapp.View.DinerDetailsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecyclerAddress extends RecyclerView.Adapter<AdapterRecyclerAddress.ViewHolder> {

    List<QuanAnModel> quanAnModelList;
    int resource;
    //Context context;

    //(1)chạy vào phương thức khởi tạo adapter bao gồm list (quán ăn) và resource (gd của quán ăn)
    public AdapterRecyclerAddress(List<QuanAnModel> quanAnModelList, int resource) {
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
       // this.context = context;
    }

    //(3)mapping control từ xml sang viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtAddress_DinerName, txtTitle, txtTitle2, txtContent, txtContent2, txtMark, txtMark2,
                txtTotalComment, txtTotalPic, txtMediumScore, txtDinerAddress, txtDistance;
        Button btnAddress_Order;
        ImageView imgAddress_DinerPic;
        CircleImageView circleImageUser, circleImageUser2;
        LinearLayout containerComment, containerComment2;
        CardView cardviewDiner;

        public ViewHolder(View itemView) {
            super(itemView);
            txtAddress_DinerName = itemView.findViewById(R.id.txtAddress_DinerName);
            btnAddress_Order = itemView.findViewById(R.id.btnAddress_Order);
            imgAddress_DinerPic = itemView.findViewById(R.id.imgAddress_DinerPic);
            txtTitle=itemView.findViewById(R.id.txtTitle);
            txtTitle2=itemView.findViewById(R.id.txtTitle2);
            txtContent=itemView.findViewById(R.id.txtContent);
            txtContent2=itemView.findViewById(R.id.txtContent2);
            circleImageUser=itemView.findViewById(R.id.cicleImageUser);
            circleImageUser2=itemView.findViewById(R.id.cicleImageUser2);
            containerComment=itemView.findViewById(R.id.containerComment);
            containerComment2=itemView.findViewById(R.id.containerComment2);
            txtMark=itemView.findViewById(R.id.txtMark);
            txtMark2=itemView.findViewById(R.id.txtMark2);
            txtTotalComment=itemView.findViewById(R.id.txtTotalComment);
            txtTotalPic=itemView.findViewById(R.id.txtTotalPic);
            txtMediumScore=itemView.findViewById(R.id.txtMediumScore);
            txtDinerAddress=itemView.findViewById(R.id.txtDinerAddress);
            txtDistance=itemView.findViewById(R.id.txtDistance);
            cardviewDiner=itemView.findViewById(R.id.cardviewDiner);
        }
    }

    //(2)Khởi tạo giao diện (return viewHolder)
    @NonNull
    @Override
    public AdapterRecyclerAddress.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);  //truyền resource vào view
        //trả về 1 viewHolder truyền view vào
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //(4)Xử lý dữ liệu quán ăn bên model
    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerAddress.ViewHolder holder, int position) {
        //set giao diện
        final QuanAnModel quanAnModel = quanAnModelList.get(position);    //lấy item

        holder.txtAddress_DinerName.setText(quanAnModel.getTenquanan());    //gán tên quán ăn cho text bên file xml

        //hiển thị btnOrder nếu cửa hàng có giao hàng
        if (quanAnModel.isGiaohang()) {
            holder.btnAddress_Order.setVisibility(View.VISIBLE);
        }
        //lấy hình ảnh
        if (quanAnModel.getHinhanhquanan().size() > 0) {
            String imgUrl = "hinhanh/"+ quanAnModel.getHinhanhquanan().get(0);
            //Log.d("hinhanh",quanAnModel.getHinhanhquanan().get(0));

            StorageReference storagePicture = FirebaseStorage.getInstance().getReference().child("imgURL");
            long ONE_MEGABYTE = 1024 * 1024;
            storagePicture.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    InputStream input=new ByteArrayInputStream(bytes);
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    holder.imgAddress_DinerPic.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //e.printStackTrace();
                    Log.e("storagePicture","fail");
                }
            });
        }

        //lấy nội dung trong bình luận
        if(quanAnModel.getCommentModelList().size() > 0){   //kiểm tra quán ăn có bình luận
            CommentModel commentModel = quanAnModel.getCommentModelList().get(0);   //lấy cmt đầu
            holder.txtTitle.setText(commentModel.getTieude());                      //set tiêu đề
            holder.txtContent.setText(commentModel.getNoidung());                   //set nội dung
            holder.txtMark.setText(commentModel.getChamdiem() + "");
            setCommentPicture(holder.circleImageUser,commentModel.getMemberModel().getHinhanh());   //gán hình ảnh user
            //nếu có trên 2 bình luận
            if(quanAnModel.getCommentModelList().size()>2){
                CommentModel commentModel2 = quanAnModel.getCommentModelList().get(1);   //lấy cmt thứ2
                holder.txtTitle2.setText(commentModel2.getTieude());                      //set tiêu đề
                holder.txtContent2.setText(commentModel2.getNoidung());                   //set nội dung
                holder.txtMark2.setText(commentModel2.getChamdiem() + "");
                setCommentPicture(holder.circleImageUser2,commentModel2.getMemberModel().getHinhanh());   //gán hình ảnh user
            }
            //lấy tổng bình luận
            holder.txtTotalComment.setText(quanAnModel.getCommentModelList().size() + "");

            //lấy tổng hình ảnh, điểm trung bình
            int tongsohinhbinhluan = 0;
            double totalscore = 0;
            for(CommentModel commentModel1 : quanAnModel.getCommentModelList()){
                tongsohinhbinhluan += commentModel1.getPhotoCmtsList().size();
                totalscore += commentModel.getChamdiem();
            }

            double mediumscore = totalscore/quanAnModel.getCommentModelList().size();
            holder.txtMediumScore.setText(String.format("%.1f",mediumscore));
            if(tongsohinhbinhluan > 0){
                holder.txtTotalPic.setText(tongsohinhbinhluan + "");
            }

        }else{
            //nếu k có bình luận --> ẩn layout bình luận
            holder.containerComment.setVisibility(View.GONE);
            holder.containerComment2.setVisibility(View.GONE);
        }

        //lấy chi nhánh quán ăn, hiển thị địa chỉ và km
//        if(quanAnModel.getBranchDinerModelList().size()>0){
//            BranchDinerModel branchDinerModelTam = quanAnModel.getBranchDinerModelList().get(0);    //lấy khoảng cách của quán đầu tiên
//            for(BranchDinerModel branchDinerModel : quanAnModel.getBranchDinerModelList()){
//                if (branchDinerModelTam.getKhoangcach() > branchDinerModel.getKhoangcach()){
//                    branchDinerModelTam = branchDinerModel;
//                }
//            }
//            holder.txtDinerAddress.setText(branchDinerModelTam.getDiachi());
//            holder.txtDistance.setText(String.format("%.1f",branchDinerModelTam.getKhoangcach())+ " km");
//        }

//        holder.cardviewDiner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent iDinerDetails = new Intent(context, DinerDetailsActivity.class);
//                iDinerDetails.putExtra("quanan", (Parcelable) quanAnModel);
//                context.startActivity(iDinerDetails);
//            }
//        });
    }

    //lấy hình ảnh user (mặc định)
    private void setCommentPicture (final CircleImageView circleImageView, String link){
        StorageReference storageUserImage = FirebaseStorage.getInstance().getReference().child("thanhvien").child(link);

        long ONE_MEGABYTE = 1024 * 1024;
        storageUserImage.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                InputStream input=new ByteArrayInputStream(bytes);
                Bitmap ext_pic = BitmapFactory.decodeStream(input);
                circleImageView.setImageBitmap(ext_pic);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //e.printStackTrace();
                Log.e("storagePicture","fail");
            }
        });

    }

    @Override
    public int getItemCount() {
        return quanAnModelList.size();
    }


}
