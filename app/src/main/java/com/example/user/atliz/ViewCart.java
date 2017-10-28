package com.example.user.atliz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.user.atliz.adapters.CartListAdapter;
import com.example.user.atliz.adapters.ProductListAdapter;
import com.example.user.atliz.model.Product;
import com.example.user.atliz.model.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ViewCart extends AppCompatActivity {

    private List<Product> mProducts = new ArrayList<>();
    @BindView(R.id.productsRv)
     RecyclerView productsRv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        productsRv = (RecyclerView) findViewById(R.id.productsRv);
        productsRv.setLayoutManager(new LinearLayoutManager(this));
        productsRv.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );

        //temp dummy data
     /*   mProducts.add(new Product(1, "Goat Meat", 0.0, 2.5, "KG", null));
        mProducts.add(new Product(2, "Chicken Dumpkins", 0.0, 9.99, "Piece", null));
        mProducts.add(new Product(3, "Lamb Leg", 0.0, 12.99, "Piece", null));
        mProducts.add(new Product(4, "Minced Meat (Lamb)", 0.0, 2.3, "Ounce", null));
*/
        mProducts = ShoppingCart.loadCart(ViewCart.this);
        CartListAdapter productListAdapter = new CartListAdapter(mProducts);
        productsRv.setAdapter(productListAdapter);
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


        @Override
        public void onRestart(){
            super.onRestart();
            Intent previewMessage = new Intent(ViewCart.this, ViewCart.class);
            startActivity(previewMessage);
            this.finish();
        }

}
