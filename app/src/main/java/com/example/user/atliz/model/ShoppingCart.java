package com.example.user.atliz.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.user.atliz.ProductActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syed Ikram Shah on 26/10/2017.
 */

public class ShoppingCart {
    public static void addToCart(Product newProduct, Context context) {
        List<Product> cart = loadCart(context);

        cart.add(newProduct);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit1 = sp.edit();
    /* sKey is an array */
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

        }

        mEdit1.commit();
    }
    public static List<Product> loadCart(Context mContext)
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
