package com.example.user.atliz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Home extends AppCompatActivity implements View.OnClickListener {
    Button meat;
    Button chicken;
    Button cow;
    Button grill;
    Button stake;
    Button cart;

    EditText etKg;
    EditText etMoney;

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        meat = (Button) findViewById(R.id.meat);
        chicken = (Button) findViewById(R.id.chicken);
        cow = (Button) findViewById(R.id.cow);
        grill = (Button) findViewById(R.id.grill);
        stake = (Button) findViewById(R.id.stake);
        cart = (Button) findViewById(R.id.cart);


        etKg = (EditText) findViewById(R.id.etKg);
        etMoney = (EditText) findViewById(R.id.etMoney);


        layout = (LinearLayout) findViewById(R.id.layout);


        meat.setOnClickListener(this);
        chicken.setOnClickListener(this);
        cow.setOnClickListener(this);
        grill.setOnClickListener(this);
        stake.setOnClickListener(this);
        cart.setOnClickListener(this);
    }




    @Override
    public void onClick(View view) {
        switch(view.getId()) {

            case R.id.meat:
                meat.setVisibility(View.GONE);
                chicken.setVisibility(View.GONE);
                cow.setVisibility(View.VISIBLE);
                break;
            case R.id.chicken:
                meat.setVisibility(View.GONE);
                chicken.setVisibility(View.GONE);
                break;
            case R.id.cow:
                cow.setVisibility(View.GONE);
                grill.setVisibility(View.VISIBLE);
                break;
            case R.id.grill:
                grill.setVisibility(View.GONE);
                stake.setVisibility(View.VISIBLE);
                break;
            case R.id.stake:
                grill.setVisibility(View.GONE);
                stake.setVisibility(View.GONE);
                cart.setVisibility(View.VISIBLE);
                etMoney.setVisibility(View.VISIBLE);

                break;
            case R.id.cart:
                SharedPreferences sharedPref = getSharedPreferences("info3", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("product", etMoney.getText().toString());
                editor.apply();

                Intent intent = new Intent(Home.this, TestCart.class);
                startActivity(intent);

                Toast.makeText(Home.this,"dd", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}






