package com.icesquare.OTPlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.auth.ui.phone.VerifyPhoneNumberFragment;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class secondlogin extends AppCompatActivity {

    Button back,next;
    EditText phoneno,username;

    FirebaseDatabase rootnode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_secondlogin);


        back = findViewById(R.id.backbtn);
        next =  findViewById(R.id.nextbtn);
        phoneno =  findViewById(R.id.phoneno);
        username =  findViewById(R.id.regname);


         next.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 rootnode = FirebaseDatabase.getInstance();
                 reference = rootnode.getReference("users");

                 String name = username.getText().toString();
                 String phonenum = phoneno.getText().toString();

                 Intent intent = new Intent(getApplicationContext(),loginpage.class);
                 intent.putExtra("phonenum",phonenum);
                 startActivity(intent);
                 finish();

              //   userhelperclass helperclass = new userhelperclass(name,phonenum);
              //   reference.child(phonenum).setValue(helperclass);
             }
         });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();

            }
        });

    }

    private void back(){

        Intent intent = new Intent(secondlogin.this,MainActivity.class);
        startActivity(intent);
        finish();
    }




}
