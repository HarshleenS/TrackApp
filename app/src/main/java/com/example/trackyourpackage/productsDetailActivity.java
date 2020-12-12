package com.example.trackyourpackage;

import android.content.Intent;
import android.os.Bundle;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.trackyourpackage.PrevalentCustomer.PrevalentCustomer;
import com.example.trackyourpackage.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class productsDetailActivity extends AppCompatActivity {
    private Button addToCArt;
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
        addToCArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(productsDetailActivity.this,"Pressed"+PrevalentCustomer.currentonlineUser.getPhone(),Toast.LENGTH_SHORT).show();
                addingToCartList();

            }
        });


    }

    private void addingToCartList()
    {String saveCurrentTime,saveCurremtDate;
        Calendar calForDate= Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurremtDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());

         final DatabaseReference cartListRef=FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String,Object> cartMap=new HashMap<>();
        cartMap.put("pid",productId);
        cartMap.put("name",producrNameDetail.getText().toString());
        cartMap.put("price",productPriceDetail.getText().toString());
        cartMap.put("date",saveCurremtDate);
       cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberBtn.getNumber());

//        for (Map.Entry<String, Object> entry : cartMap.entrySet()) {
//            //System.out.println(entry.getKey() + ":" + entry.getValue().toString());
//            Toast.makeText(productsDetailActivity.this,entry.getKey() + ":" + entry.getValue().toString()+" ",Toast.LENGTH_LONG).show();
//
//        }

        //Toast.makeText(productsDetailActivity.this,"Pressed oooooo"+PrevalentCustomer.currentonlineUser.getPhone(),Toast.LENGTH_SHORT).show();
        //Log.i("printtt",PrevalentCustomer.currentonlineUser.getPhone());

        cartListRef.child("User View").child(PrevalentCustomer.currentonlineUser.getPhone())
                .child("Products")
                .child(productId)
                .updateChildren(cartMap).
                addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                { //Toast.makeText(productsDetailActivity.this,"Added to cart successfully",Toast.LENGTH_SHORT).show();
                  cartListRef.child("Retailer View").child(PrevalentCustomer.currentonlineUser.getPhone()).child("Products").child(productId).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>()
                  {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {if(task.isSuccessful())
                    {
                        Toast.makeText(productsDetailActivity.this,"Added to cart successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(productsDetailActivity.this,Display.class);
                        startActivity(intent);
                    }


                    }
               });

                }
            }
        });






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