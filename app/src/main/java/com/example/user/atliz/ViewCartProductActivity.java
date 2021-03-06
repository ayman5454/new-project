package com.example.user.atliz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.example.user.atliz.model.Product;
import com.example.user.atliz.model.ShoppingCart;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewCartProductActivity extends AppCompatActivity {


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
    Snackbar snackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart_product);
        ButterKnife.bind(this);

        int productId = getIntent().getIntExtra(Product.BUNDLE_EXTRA_ID, -1);
        mProduct = ShoppingCart.GetItemByCartIndex(productId, ViewCartProductActivity.this);
        // mProduct = getIntent().getParcelableExtra(Product.BUNDLE_EXTRA_ID);
        updateUI();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.delete_cart_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Confirm removing cart item?", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).setAction("Yes! Remove from Cart!", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShoppingCart.deleteCartItem(mProduct, ViewCartProductActivity.this);
                        finish();
                    }
                }).show();
            }
        });
    }

    private void updateUI() {
        this.setTitle(mProduct.getName() + "(" + mProduct.getUnitPrice() + "/" + mProduct.getUnitName() + ")");
        productAmount.setText(mProduct.getUnitName());
        boolean AmountChanging = false;
        boolean PriceChanging = false;
        amountEt.setText(String.format("%.02f", (float) mProduct.getAmount()));
        priceEt.setText(String.format("%.02f", (float) mProduct.getToalPrice()));
        chkCalcPrice.setChecked(mProduct.isCheckbox1());
        chkCalcAmount.setChecked(mProduct.isCheckbox2());
        messageEt.setText(mProduct.getMessage());
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
        double amount = 0.0;
        double price = 0.0;
        boolean hasError = false;
        try{
            amount=Double.parseDouble(amountEt.getText().toString());
        }catch (Exception ex)
        {
            amountEt.setError("Please check this");
            hasError = true;
        }
        try{
            price = Double.parseDouble(priceEt.getText().toString());

        }catch (Exception ex)
        {
            hasError = true;
            priceEt.setError("Please check this");
        }
        if(!hasError)
        {
            mProduct.setAmount(amount);
            mProduct.setTotalPrice(price);
            mProduct.setMessage(messageEt.getText().toString());
            mProduct.setCheckbox1(chkCalcPrice.isChecked());
            mProduct.setCheckbox2(chkCalcAmount.isChecked());
            //cart.add(mProduct);
            ShoppingCart.updateCartItem(mProduct, ViewCartProductActivity.this);
        }
        //List<Product> cart = loadCart(ProductActivity.this);

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewCartProductActivity.this, ViewCart.class);
        startActivity(intent);
        finish();
    }
}
