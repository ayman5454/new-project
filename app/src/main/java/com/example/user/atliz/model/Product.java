package com.example.user.atliz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshua on 22/10/2017.
 */

public class Product implements Parcelable {
    public static List<Product> GetProductList()
    {
        List<Product> result = new ArrayList<Product>();
        result.add(new Product(1, "Goat Meat", 0.0, 2.5, "KG", null));
        result.add(new Product(2, "Chicken Dumpkins", 0.0, 9.99, "Piece", null));
        result.add(new Product(3, "Lamb Leg", 0.0, 12.99, "Piece", null));
        result.add(new Product(4, "Minced Meat (Lamb)", 0.0, 2.3, "Ounce", null));
        return result;
    }
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String BUNDLE_EXTRA_ID = "product";
    private  int productId;
    private String name;
    private double amount;
    private String unitName;
    private String message;
    private double unitPrice;
    private double totalPrice;
    public Product(int Id, String name, Double amount, Double unitPrice, String unitName, String message) {
        this.productId = Id;
        this.name = name;
        this.amount = amount;
        this.unitName = unitName;
        this.message = message;
        this.unitPrice = unitPrice;
    }
    public double getToalPrice()
    {
        return totalPrice;
    }
    public void setTotalPrice(Double price)
    {
        totalPrice = price;
    }
    public Integer getId()
    {
        return this.productId;
    }
    public void setId(Integer id)
    {
        this.productId = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice= unitPrice;
    }

    private Product(Parcel in) {
        name = in.readString();
        amount = in.readDouble();
        unitName = in.readString();
        message = in.readString();
        unitPrice = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(amount);
        parcel.writeString(unitName);
        parcel.writeString(message);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
