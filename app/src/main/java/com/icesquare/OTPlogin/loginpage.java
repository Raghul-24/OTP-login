package com.icesquare.OTPlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class loginpage extends AppCompatActivity {

    String verificationCodebySystem;
    Button changenum,verifybutton;
    EditText verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loginpage);

        changenum = findViewById(R.id.changenum);
        verifybutton = findViewById(R.id.verifybtn);
        verify = findViewById(R.id.verify);
        
        String phoneno = getIntent().getStringExtra("phonenum");
        
        senVerificationCodeToUser(phoneno);

        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = verify.getText().toString();
                if (code.isEmpty()||code.length()<6){
                    verify.setError("wrong OTP");
                    verify.requestFocus();
                }
                else {
                    Intent intent = new Intent(loginpage.this, profilepage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


        changenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changenum();
            }
        });
    }

    private void senVerificationCodeToUser(String phoneno) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
               "+ 91" + phoneno,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodebySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if (code!= null) {
                verifycode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(loginpage.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    };

    private void  verifycode(String CodeByuser){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodebySystem,CodeByuser);
        signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(loginpage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Intent intent = new  Intent(loginpage.this,profilepage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(loginpage.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void changenum(){

        Intent intent = new Intent(loginpage.this,secondlogin.class);
        startActivity(intent);
        finish();
    }


}
