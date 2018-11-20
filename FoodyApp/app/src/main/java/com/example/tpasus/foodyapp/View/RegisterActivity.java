package com.example.tpasus.foodyapp.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tpasus.foodyapp.Controller.RegisterController;
import com.example.tpasus.foodyapp.Model.MemberModel;
import com.example.tpasus.foodyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSign_up;
    EditText edtSign_upEmail, edtSign_upPass, edtRe_EnterPass;
    FirebaseAuth firebaseAuth;
    RegisterController registerController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);

        firebaseAuth=FirebaseAuth.getInstance();

        btnSign_up=findViewById(R.id.btnSign_up);
        edtSign_upEmail=findViewById(R.id.edtSign_upEmail);
        edtSign_upPass=findViewById(R.id.edtSign_upPass);
        edtRe_EnterPass=findViewById(R.id.edtRe_enterPass);

        btnSign_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Kiem tra dang ky
        final String email = edtSign_upEmail.getText().toString();
        String password = edtSign_upPass.getText().toString();
        String re_enterpass = edtRe_EnterPass.getText().toString();
        String error = getString(R.string.error);

        if(email.trim().length() == 0){
            error += getString(R.string.email);
            Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
        }else if(password.trim().length() == 0 ){
            error += getString(R.string.password);
            Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
        }else if(!re_enterpass.equals(password)){
            Toast.makeText(this,getString(R.string.notifyerror),Toast.LENGTH_SHORT).show();
        }
        //end Kiem tra dang ky

        //k loi, tao user vs email&pass da dang ky
        else{
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        MemberModel memberModel = new MemberModel();
                        memberModel.setHoten(email);
                        memberModel.setHinhanh("user.png");
                        String uid = task.getResult().getUser().getUid();

                        registerController = new RegisterController();
                        registerController.addInfoMember(memberModel, uid);
                        Toast.makeText(RegisterActivity.this, getString(R.string.notifysign_up),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
