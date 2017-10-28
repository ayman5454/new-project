package com.example.user.atliz;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.user.atliz.model.Product;
import com.example.user.atliz.adapters.ProductListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AtlizShopActivity extends AppCompatActivity {

    @BindView(R.id.productsRv)
    RecyclerView productsRv;

    private List<Product> mProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atliz_shop);
        ButterKnife.bind(this);

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
     mProducts = Product.GetProductList();
        ProductListAdapter productListAdapter = new ProductListAdapter(mProducts);
        productsRv.setAdapter(productListAdapter);
    }
}
