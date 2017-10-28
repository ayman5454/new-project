package com.example.user.atliz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TestCart extends AppCompatActivity implements View.OnClickListener {


    ListView list;
    Button test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cart);



        list = (ListView) findViewById(R.id.list);
        test = (Button) findViewById(R.id.test);
        test.setOnClickListener(this);


        List<String> cartList = new ArrayList<String>();


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, cartList);

        list.setAdapter(arrayAdapter);
        //cartList.add("stam");

        SharedPreferences sharedPref = getSharedPreferences("info3", Context.MODE_PRIVATE);

        String list1 = sharedPref.getString("product", "");
        cartList.add(sharedPref.getString("",list1));

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == test.getId()){
            Intent intent = new Intent(TestCart.this, Home.class);
            startActivity(intent);
        }
    }
}
