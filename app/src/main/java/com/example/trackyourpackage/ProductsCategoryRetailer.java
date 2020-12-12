package com.example.trackyourpackage;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import io.paperdb.Paper;

public class ProductsCategoryRetailer extends AppCompatActivity {
    private ImageView cat1,cat2,cat3,cat4,cat5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_category_retailer);
        cat1=findViewById(R.id.category11);
        cat2=findViewById(R.id.category21);
        cat3=findViewById(R.id.category31);
        cat4=findViewById(R.id.category41);
        cat5=findViewById(R.id.category51);

        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               // Toast.makeText(ProductsCategoryRetailer.this,"Hello cat",Toast.LENGTH_SHORT).show();

                Intent intent= new Intent(ProductsCategoryRetailer.this,AddNewProduct.class);
                intent.putExtra("Category","Category-1");
                startActivity(intent);


            }
        });




     cat2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            Intent intent= new Intent(ProductsCategoryRetailer.this,AddNewProduct.class);
            intent.putExtra("Category","Category-2");
            startActivity(intent);

        }
    });


        cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent= new Intent(ProductsCategoryRetailer.this,AddNewProduct.class);
                intent.putExtra("Category","Category-3");
                startActivity(intent);


            }
        });

        cat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent= new Intent(ProductsCategoryRetailer.this,AddNewProduct.class);
                intent.putExtra("Category","Category-4");
                startActivity(intent);

            }
        });
        cat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent= new Intent(ProductsCategoryRetailer.this,AddNewProduct.class);
                intent.putExtra("Category","Category-5");
                startActivity(intent);

            }
        });



    }

    public void onBackPressed()
    {
        Paper.book().destroy();

        //startActivity(new Intent(getApplicationContext(), SelectLoginPage.class));
        finish();

        return;
    }
}