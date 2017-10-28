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
    public static int addToCart(Product newProduct, Context context) {
        List<Product> cart = loadCart(context);

        cart.add(newProduct);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.clear();
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
            mEdit1.putBoolean("first-checkbox-"+ i, p.isCheckbox1());
            mEdit1.putBoolean("second-checkbox-"+ i, p.isCheckbox2());
        }

        mEdit1.commit();
        return cart.size() - 1;
    }
    public static void updateCartItem(Product updateProduct, Context context) {
        List<Product> cart = loadCart(context);


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.clear();
    /* sKey is an array */
        mEdit1.putInt("cart_size", cart.size());

        for(int i=0;i<cart.size();i++)
        {

            Product p = cart.get(i);
            if(p.getCartItemId() == updateProduct.getCartItemId())
            {
                mEdit1.putFloat("total_price_" + i,(float) (updateProduct.getToalPrice()));
                mEdit1.putFloat("amount_"+ i, (float) updateProduct.getAmount());
                mEdit1.putInt("id_"+ i,  updateProduct.getId());
                mEdit1.putString("message_"+ i, updateProduct.getMessage());
                mEdit1.putString("name_"+ i, updateProduct.getName());
                mEdit1.putString("unit_name_"+ i, updateProduct.getUnitName());
                mEdit1.putFloat("unit_price"+ i, (float) updateProduct.getUnitPrice());
                mEdit1.putBoolean("first-checkbox-"+ i, updateProduct.isCheckbox1());
                mEdit1.putBoolean("second-checkbox-"+ i, updateProduct.isCheckbox2());
            }
            else
            {
                mEdit1.putFloat("total_price_" + i,(float) (p.getToalPrice()));
                mEdit1.putFloat("amount_"+ i, (float) p.getAmount());
                mEdit1.putInt("id_"+ i,  p.getId());
                mEdit1.putString("message_"+ i, p.getMessage());
                mEdit1.putString("name_"+ i, p.getName());
                mEdit1.putString("unit_name_"+ i, p.getUnitName());
                mEdit1.putFloat("unit_price"+ i, (float) p.getUnitPrice());
                mEdit1.putBoolean("first-checkbox-"+ i, p.isCheckbox1());
                mEdit1.putBoolean("second-checkbox-"+ i, p.isCheckbox2());
            }
        }

        mEdit1.commit();
    }
    public static void deleteCartItem(Product updateProduct, Context context) {
        List<Product> cart = loadCart(context);


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.clear();
        int size = cart.size();
    /* sKey is an array */

        boolean removed = false;
        for(int i=0;i<cart.size();i++)
        {

            Product p = cart.get(i);
            if(p.getCartItemId() == updateProduct.getCartItemId())
            {
                size --;
            }
            else
            {
                mEdit1.putFloat("total_price_" + i,(float) (p.getToalPrice()));
                mEdit1.putFloat("amount_"+ i, (float) p.getAmount());
                mEdit1.putInt("id_"+ i,  p.getId());
                mEdit1.putString("message_"+ i, p.getMessage());
                mEdit1.putString("name_"+ i, p.getName());
                mEdit1.putString("unit_name_"+ i, p.getUnitName());
                mEdit1.putFloat("unit_price"+ i, (float) p.getUnitPrice());
                mEdit1.putBoolean("first-checkbox-"+ i, p.isCheckbox1());
                mEdit1.putBoolean("second-checkbox-"+ i, p.isCheckbox2());
            }
        }
        mEdit1.putInt("cart_size", size);
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
            p.setCheckbox1(mSharedPreference1.getBoolean("first-checkbox-" + i, false));
            p.setCheckbox2(mSharedPreference1.getBoolean("second-checkbox-" + i, false));
            p.setCartItemId(i);
            list.add(p);
        }
        return list;
    }
    public static  Product GetItemByCartIndex(int index, Context context)
    {
        Product product =  null;
        List<Product> products = loadCart(context);
        for(int i = 0; i < products.size(); i ++)
        {
            if(i == index) {
                product = products.get(i);
                break;
            }
        }

        return product;
    }
    public static void ClearCart(Context context)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.clear();
        mEdit1.commit();

    }
}
