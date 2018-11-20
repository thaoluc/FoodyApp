package com.example.tpasus.foodyapp.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MemberModel {

    private DatabaseReference dataNodeMember;

    String hoten, hinhanh, mauser;

    public MemberModel() {
        dataNodeMember = FirebaseDatabase.getInstance().getReference().child("thanhviens");
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hote) {
        this.hoten = hote;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    public void addInfoMember (MemberModel memberModel, String uid){

        dataNodeMember.child(uid).setValue(memberModel);
    }
}
