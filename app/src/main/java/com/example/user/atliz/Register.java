package com.example.user.atliz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText phone1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText etxtPhoneCode;
    private String mVerificationId;

    Button b1,signIn, jump;

    Button viewCart;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Note that this will not work on emulator, this requires a real device
        phone1 = (EditText) findViewById(R.id.phone);
        etxtPhoneCode = (EditText) findViewById(R.id.etxtPhoneCode);

        this.viewCart = (Button) findViewById(R.id.view_cart);



        b1= (Button) findViewById(R.id.send);
        signIn= (Button) findViewById(R.id.SignIn);
        jump = (Button) findViewById(R.id.jump);

        jump.setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                Log.d("AUTH_USER", String.valueOf(user));
                if (user != null) {
                    Intent intent = new Intent(Register.this, AtlizShopActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Register.this,"logged in", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
        viewCart.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, ViewCart.class));
            }
        });
    }


    @OnClick({R.id.view_cart})
    public void ViewCart(View view)
    {

    }
    public void requestCode(View view) {
        String phoneNumber = phone1.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(Register.this, "please enter your phone number!", Toast.LENGTH_SHORT).show();

        return;
    }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, 60, TimeUnit.SECONDS, Register.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        //Called if it is not needed to enter verification code
                        signInWithCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        //incorrect phone number, verification code, emulator, etc.
                        Toast.makeText(Register.this, "onVerificationFailed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        //now the code has been sent, save the verificationId we may need it
                        super.onCodeSent(verificationId, forceResendingToken);


                        mVerificationId = verificationId;
                        phone1.setVisibility(View.GONE);
                        b1.setVisibility(View.GONE);
                        //name1.setVisibility(View.GONE);
                        //city1.setVisibility(View.GONE);
                        //address1.setVisibility(View.GONE);
                       // tv1.setVisibility(View.GONE);
                        //tv2.setVisibility(View.GONE);
                        //tv3.setVisibility(View.GONE);
                       // tv4.setVisibility(View.GONE);
                        etxtPhoneCode.setVisibility(View.VISIBLE);
                        signIn.setVisibility(View.VISIBLE);


                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String verificationId) {
                        //called after timeout if onVerificationCompleted has not been called
                        super.onCodeAutoRetrievalTimeOut(verificationId);
                        Toast.makeText(Register.this, "TimeOut", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, AtlizShopActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Register.this, "verification faild", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void signIn(View view) {
        String code = etxtPhoneCode.getText().toString();
        if (TextUtils.isEmpty(code))

        signInWithCredential(PhoneAuthProvider.getCredential(mVerificationId, code));
    }


    @Override
    public void onClick(View view) {

            Intent intent = new Intent(Register.this, AtlizShopActivity.class);
        startActivity(intent);
    }
}