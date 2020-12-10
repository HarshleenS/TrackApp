package com.example.trackyourpackage.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackyourpackage.ItemClickListner;
import com.example.trackyourpackage.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{


public TextView txtProductname, txtProductprice,txtProductquan;
private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductname=itemView.findViewById(R.id.cart_product_name);
        txtProductprice=itemView.findViewById(R.id.cart_product_price);
        txtProductquan=itemView.findViewById(R.id.cart_product_quantity);
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view,getAdapterPosition(),false);

    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
