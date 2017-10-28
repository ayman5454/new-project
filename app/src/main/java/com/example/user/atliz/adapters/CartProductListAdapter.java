package com.example.user.atliz.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.atliz.ProductActivity;
import com.example.user.atliz.R;
import com.example.user.atliz.ViewCartProductActivity;
import com.example.user.atliz.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joshua on 22/10/2017.
 */

public class CartProductListAdapter extends RecyclerView.Adapter<CartProductListAdapter.ProductViewHolder> {
    private List<Product> items;
    private Context mContext;

    public CartProductListAdapter(List<Product> items) {
        this.items = items;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.product_item_layout, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product product = items.get(position);

        holder.bind(product);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ViewCartProductActivity.class);

                intent.putExtra(Product.BUNDLE_EXTRA_ID, product.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    static class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView nameTv;

        ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Product product) {
            nameTv.setText(product.getName() + " ( " + product.getUnitPrice() + "/" + product.getUnitName() + ")");
        }
    }

}