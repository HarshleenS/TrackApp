package com.example.trackyourpackage.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trackyourpackage.ItemClickListner;
import com.example.trackyourpackage.R;


public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;



    public ProductViewHolder(View itemView)
    {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.Product_Description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.Product_Price);
    }
    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }


    @Override
    public void onClick(View view)
    {listner.onClick(view, getAdapterPosition(), false);

    }
}
