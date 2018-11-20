package com.example.tpasus.foodyapp.View;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tpasus.foodyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtResetEmail;
    Button btnSendEmail;
    TextView txtSignUp1;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgotpassword);

        edtResetEmail=findViewById(R.id.edtResetEmail);
        btnSendEmail=findViewById(R.id.btnSendEmail);
        txtSignUp1=findViewById(R.id.txtSignUp1);

        btnSendEmail.setOnClickListener(this);
        txtSignUp1.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnSendEmail:
                String email = edtResetEmail.getText().toString();
                boolean checkemail = checkEmail(email);

                if(checkemail)
                {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ResetPassActivity.this, getString(R.string.notify_sendmailsuccessful), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(ResetPassActivity.this, getString(R.string.notifyemailerror), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.txtSignUp1:
            {
                Intent iSignup=new Intent(ResetPassActivity.this, RegisterActivity.class);
                startActivity(iSignup);
            }
                break;
        }
    }

    //Kiem tra dinh dang email
    private boolean checkEmail(String email){
       return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    //end Kiem tra dinh dang email
}
