package com.example.trackyourpackage;

import android.os.Bundle;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.trackyourpackage.model.Products;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class productsDetailActivity extends AppCompatActivity {
    private FloatingActionButton addToCArt;
    private ImageView productImageDetails;
    private ElegantNumberButton numberBtn;
    private TextView productPriceDetail,productDescriptionDetail,producrNameDetail;
    private  String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        productId=getIntent().getStringExtra("pid");
        setContentView(R.layout.activity_products_detail);
        addToCArt=findViewById(R.id.add_product_to_cart);
        productImageDetails=findViewById(R.id.product_image_detail);
        producrNameDetail=findViewById(R.id.product_name_details);
        productDescriptionDetail=findViewById(R.id.product_description_details);
        productPriceDetail=findViewById(R.id.product_price_details);
        numberBtn=findViewById(R.id.number_btn);
        //Toast.makeText(productsDetailActivity.this,productId+"Heyyy",Toast.LENGTH_SHORT).show();
        getProductDetails(productId);

    }

    private void getProductDetails(final String productId)
    {
        DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {if(snapshot.exists())
            {
                Products products=snapshot.getValue(Products.class);
                //Toast.makeText(productsDetailActivity.this,products.getName()+"Heyyy",Toast.LENGTH_SHORT).show();
                producrNameDetail.setText(products.getName());
                productPriceDetail.setText("Rs "+products.getPrice());
                productDescriptionDetail.setText(products.getDescription());
                Picasso.get().load(products.getImage()).into(productImageDetails);


            }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}