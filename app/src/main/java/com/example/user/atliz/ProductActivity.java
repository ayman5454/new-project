package com.example.user.atliz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.atliz.model.Product;
import com.example.user.atliz.model.ShoppingCart;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ProductActivity extends AppCompatActivity {

    private Product mProduct;
    boolean amountChaning, priceChanging;
    @BindView(R.id.priceEt)
    EditText priceEt;

    @BindView(R.id.amountEt)
    EditText amountEt;

    @BindView(R.id.messageEt)
    EditText messageEt;
    @BindView(R.id.itemAmount)
    TextView productAmount;
    @BindView(R.id.calc_amount)
    CheckBox chkCalcAmount;
    @BindView(R.id.calc_price)
    CheckBox chkCalcPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        int productId = getIntent().getIntExtra(Product.BUNDLE_EXTRA_ID, -1);
       // mProduct = ShoppingCart.GetItemByCartIndex(productId, ProductActivity.this);
        for (Product p: Product.GetProductList()
             ) {
            if(productId == p.getId())
            {
                mProduct = p;
                break;
            }
        }
       // mProduct = getIntent().getParcelableExtra(Product.BUNDLE_EXTRA_ID);
        updateUI();
    }

    private void updateUI() {
        this.setTitle(mProduct.getName() + "(" + mProduct.getUnitPrice() + "/" + mProduct.getUnitName() + ")");
        productAmount.setText(mProduct.getUnitName());
          boolean AmountChanging = false;
        boolean PriceChanging = false;
     //   amountEt.setText(String.format("%.02f", mProduct.getAmount()));
     //   priceEt.setText(String.format("%.02f", mProduct.getToalPrice()));
    //    chkCalcPrice.setChecked(mProduct.isCheckbox1());
    //    chkCalcAmount.setChecked(mProduct.isCheckbox2());
        amountEt.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {

                    amountChaning = false;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                if(!priceChanging)
                    amountChaning = true;
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!priceChanging)
                {
                    try{
                        int Amount = Integer.parseInt(amountEt.getText().toString());
                        double Price = Amount * mProduct.getUnitPrice();
                        priceEt.setText(String.format("%.02f", (float) Price));
                    }catch (Exception exception)
                    {
                        Log.e("error converting amount", "EEE");
                    }

                }
            }
        });
        priceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!amountChaning)
                    priceChanging = true;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!amountChaning)
                {
                    try{
                        float price = Float.parseFloat(priceEt.getText().toString());
                        double amount =  price / mProduct.getUnitPrice();
                        amountEt.setText(String.format("%.02f", (float) amount));
                    }catch (Exception ex)
                    {

                    }

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                priceChanging = false;
            }
        });

    }


    @OnClick({R.id.calc_price})
    void  onCalcPrice(View view)
    {

    }

    @OnClick({R.id.calc_amount})
    void  onCalcAmount(View view)
    {

    }


    @OnClick({R.id.addToCartBtn})
    void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.addToCartBtn:
                addToCart();
                break;
        }
    }

    private void addToCart() {
        //List<Product> cart = loadCart(ProductActivity.this);
        mProduct.setAmount(Double.parseDouble(amountEt.getText().toString()));
        mProduct.setTotalPrice(Double.parseDouble(priceEt.getText().toString()));
        mProduct.setMessage(messageEt.getText().toString());
        mProduct.setCheckbox1(chkCalcPrice.isChecked());
        mProduct.setCheckbox2(chkCalcAmount.isChecked());
        //cart.add(mProduct);
        int newItem =  ShoppingCart.addToCart(mProduct, ProductActivity.this);
        Intent intent = new Intent(ProductActivity.this, ViewCartProductActivity.class);

        intent.putExtra(Product.BUNDLE_EXTRA_ID, newItem);
        startActivity(intent);
        finish();
        return;
       // SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
      //  SharedPreferences.Editor mEdit1 = sp.edit();
    /* sKey is an array
        mEdit1.putInt("cart_size", cart.size());

        for(int i=0;i<cart.size();i++)
        {

          Product p = cart.get(i);


           mEdit1.putFloat("total_price_" + i,(float) (p.getToalPrice()));
            mEdit1.putFloat("amount_"+ i, (float) p.getAmount());
            mEdit1.putInt("id_"+ i,  p.getId());
            mEdit1.putString("message_"+ i, p.getMessage());
            mEdit1.putString("name_"+ i, p.getName());
            mEdit1.putString("unit_name_"+ i, p.getUnitName());
            mEdit1.putFloat("unit_price"+ i, (float) p.getUnitPrice());
            mEdit1.putBoolean("item_checkbox1_" + i, p.isCheckbox1());
            mEdit1.putBoolean("item_checkbox2_"+ i, p.isCheckbox2());
        }

          mEdit1.commit(); */
    }
    public List<Product> loadCart(Context mContext)
    {
        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(mContext);
        List<Product> list = new ArrayList<Product>();
        int size = mSharedPreference1.getInt("cart_size", 0);

        for(int i=0;i<size;i++)
        {
            Product p = new Product(0, "", 0.0, 0.0, "", "");
            p.setTotalPrice((double)mSharedPreference1.getFloat("total_price_" + i, 0));
            p.setAmount(mSharedPreference1.getFloat("amount_" + i, 0));
            p.setId(mSharedPreference1.getInt("id_" + i, 0));
            p.setMessage(mSharedPreference1.getString("message_" + i, ""));
            p.setName(mSharedPreference1.getString("name_"+ i, ""));
            p.setUnitName(mSharedPreference1.getString("unit_name_" + i , ""));
            p.setUnitPrice((double) mSharedPreference1.getFloat("unit_price" + i, 0));
            list.add(p);
        }
        return list;
    }
}
