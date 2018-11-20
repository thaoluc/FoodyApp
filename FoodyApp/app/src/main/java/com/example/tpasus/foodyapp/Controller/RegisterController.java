package com.example.tpasus.foodyapp.Controller;

import com.example.tpasus.foodyapp.Model.MemberModel;

public class RegisterController {

    MemberModel memberModel;

    public RegisterController(){
        memberModel = new MemberModel();
    }

    public void addInfoMember (MemberModel memberModel, String uid){
        memberModel.addInfoMember(memberModel, uid);
    }
}
