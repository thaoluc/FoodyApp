package com.example.tpasus.foodyapp.View;

import android.content.Intent;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener
                                                                    , FirebaseAuth.AuthStateListener{

    Button btnLoginGG, btnLoginFB, btnLogin;
    TextView txtForgotPass, txtSignUp, txtForgotPassword;
    EditText edtEmail, edtPassword;
    GoogleApiClient apiClient;
    public static int REQUESTCODE_SIGNIN_GG = 100;
    public static int CHECK_SIGNIN_PROVIDER = 0;
    FirebaseAuth firebaseAuth;
    CallbackManager mCallbackFB;
    LoginManager loginManager;
    List<String> permissionFB = Arrays.asList("email","public_profile");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext()); //khoi tao FbSDK
        setContentView(R.layout.layout_login);  //set layout

        mCallbackFB = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();  //khoi tao loginmanager
        firebaseAuth = FirebaseAuth.getInstance();  //yeu cau chưng thuc FirebaseAuth
        firebaseAuth.signOut();

        btnLoginGG = findViewById(R.id.btnLoginGG);
        btnLoginFB = findViewById(R.id.btnLoginFB);
        txtSignUp=findViewById(R.id.txtSignUp);
        btnLogin=findViewById(R.id.btnLogin);
        edtEmail=findViewById(R.id.edtLoginEmail);
        edtPassword=findViewById(R.id.edtLoginPass);
        txtForgotPassword=findViewById(R.id.txtForgotPassword);

        //gan su kien click, chay vao ham onClick
        btnLoginGG.setOnClickListener(this);
        btnLoginFB.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);

        GetInfoClientGG();  // tao client dang nhap gg
    }

    private void SigninFB(){
        loginManager.logInWithReadPermissions(this, permissionFB); //this: fragment manager, quyen lay email, public_profile(-->lay dc token)
        loginManager.registerCallback(mCallbackFB, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //kiem tra provider fb
                CHECK_SIGNIN_PROVIDER = 2;
                //neu dang nhap thanh cong thi lay token
                String tokenID = loginResult.getAccessToken().getToken();
                //goi ham ChungThucDangNhapFirebase truyen vao tokenID vua lay dc
                ChungThucDangNhapFirebase(tokenID);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    //Khoi tao client cho dang nhap GG
    private void GetInfoClientGG(){
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();
    }
    //end khoi tao client cho dang nhap GG

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    //Mo form dang nhap bang GG
    private void SignInGG (GoogleApiClient googleApiClient){
        CHECK_SIGNIN_PROVIDER = 1;
        Intent iSignInGG = Auth.GoogleSignInApi.getSignInIntent(apiClient); //trang intent dang nhap bang gg vs apiClient vao
        startActivityForResult(iSignInGG, REQUESTCODE_SIGNIN_GG);   //khi dang nhap dc chay vao onActivityResult
        //neu dang nhap thanh cong thi kich hoat onAuthStateChanged da dang ky o onStart
    }
    //end Mo form dang nhap bang GG

    //Lay tokenID da dang nhap bang GG de dang nhap tren firebase
    private void ChungThucDangNhapFirebase (String tokenID){
        if(CHECK_SIGNIN_PROVIDER==1){
            //mo chung thuc dang nhap bang GG goi GoogleAuthProvider
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID,null);
            //goi phuong thuc dang nhap voi auth provider gg
            firebaseAuth.signInWithCredential(authCredential);
        }else if(CHECK_SIGNIN_PROVIDER==2){
            //mo chung thuc dang nhap bang GG goi FacebookAuthProvider
            AuthCredential authCredential = FacebookAuthProvider.getCredential(tokenID);
            //goi phuong thuc dang nhap voi auth provider fb
            firebaseAuth.signInWithCredential(authCredential);
        }

    }
    //end Lay tokenID da dang nhap bang GG de dang nhap tren firebase

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTCODE_SIGNIN_GG) { //kiem tra requestcode dang nhap cua gg
            if (resultCode == RESULT_OK) {  //neu trang thai OK
                //lay kq cua nguoi dung dang nhap bang GG thong qua getSignInResultFromIntent
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount account = signInResult.getSignInAccount();  //lay dc tai khoan,
                String tokenID = account.getIdToken();  //thong qua tk lay dc token
                ChungThucDangNhapFirebase(tokenID); //goi phuong thuc ChungThucDangNhap truyen vao tokenID
            }
        } else{
            mCallbackFB.onActivityResult(requestCode,resultCode,data); //kich hoat vao ham onSuccess (FB)
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    //Lang nge su kien user click vao btnDangnhap gg, fb, email acount
    @Override
    public void onClick(View view) {
        int id  = view.getId();
        switch (id){
            //neu la btn GG thi goi den ham SignInGG
            case R.id.btnLoginGG:
                SignInGG(apiClient);
                break;
            //neu la btn FB thi goi den ham SignInFB
            case R.id.btnLoginFB:
                SigninFB();
                break;
            //chuyen den trang dang ky
            case R.id.txtSignUp:
            {
                Intent iSignUp = new Intent(this,RegisterActivity.class);
                startActivity(iSignUp);
            }
                break;
            case R.id.btnLogin:
                LogIn();
                break;
            case R.id.txtForgotPassword:
            {
                Intent iResetPass = new Intent(this,ResetPassActivity.class);
                startActivity(iResetPass);
            }
                break;
        }
    }
    //end Lang nge su kien user click vao btnDangnhap gg, fb, email acount

    private void LogIn()
    {
        String email = edtEmail.getText().toString();
        boolean checkemail=checkEmail(email);
        String password = edtPassword.getText().toString();

        if(checkemail)
        {
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,getString(R.string.notifyloginerror),Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, getString(R.string.loginsuccesful), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(LoginActivity.this, getString(R.string.notifyemailerror), Toast.LENGTH_SHORT).show();
        }

    }

    //Kiem tra dinh dang email
    private boolean checkEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    //end Kiem tra dinh dang email

    //Su kien dc kich hoat khi nguoi dung dang nhap hoac dang xuat
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        //thong qua user biet nguoi dung dang nhap thanh cong hay that bai
        FirebaseUser user = firebaseAuth.getCurrentUser();  //lay user vua dang nhap bang AuthGG
        if(user != null)    //dang nhap thanh cong chuyen den trang chu
        {
            Intent iHomePage = new Intent(this, HomePageActivity.class);
            startActivity(iHomePage);
        }
    }
    //end Su kien dc kich hoat khi nguoi dung dang nhap hoac dang xuat
}
