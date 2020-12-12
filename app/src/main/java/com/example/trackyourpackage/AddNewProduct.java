package com.example.trackyourpackage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class AddNewProduct extends AppCompatActivity {
    private String CategoryName,inputDescription,inputPrice,inputQuantity,inputLocation,inputSellerName,inputProductName,saveCurrentDate,savecurrentTime;
    private Button AddnewProductBtn;
    private EditText productNAme,productDescription,productPrice,productSeller,productLocation,productquantity;
    private ImageView inputProductImage;
    private static final int GalleryPick=1;
    private Uri ImageUri;
    private String ProductKey,downloadImageUrl;
    private StorageReference ProductImageRf;
    private DatabaseReference ProductDataBaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        CategoryName=getIntent().getExtras().get("Category").toString();
        Toast.makeText(AddNewProduct.this,CategoryName,Toast.LENGTH_SHORT).show();
        AddnewProductBtn=findViewById(R.id.add_new_product_btn);
        productNAme=findViewById(R.id.product_name);
                productDescription=findViewById(R.id.Description);
                productPrice=findViewById(R.id.PriceProduct);
                productSeller=findViewById(R.id.sellerName);
                productLocation=findViewById(R.id.sellerLocation);
                productquantity=findViewById(R.id.quantity);
                ProductDataBaseRef=FirebaseDatabase.getInstance().getReference().child("Products");
                inputProductImage=findViewById(R.id.input_image);
                ProductImageRf= FirebaseStorage.getInstance().getReference().child("Product Images");
                inputProductImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OpenGallery();
                    }
                });
                AddnewProductBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ValidateProductAdded();
                    }
                });




    }


    public void onBackPressed()
    {

        finish();


    }
    private void OpenGallery()
    {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick&&resultCode==RESULT_OK&&data!=null)
        {
            ImageUri=data.getData();
            inputProductImage.setImageURI(ImageUri);

        }
    }
    private void ValidateProductAdded()
    {inputDescription=productDescription.getText().toString();
    inputLocation=productLocation.getText().toString();
    inputPrice=productPrice.getText().toString();
    inputProductName=productNAme.getText().toString();
    inputQuantity=productquantity.getText().toString();
    inputSellerName=productSeller.getText().toString();

    if(ImageUri==null)
    {
        Toast.makeText(AddNewProduct.this,"Product Image is mandatory",Toast.LENGTH_SHORT).show();
    }

    else if (TextUtils.isEmpty(inputProductName))
    {
        Toast.makeText(AddNewProduct.this,"Product Name is mandatory",Toast.LENGTH_SHORT).show();
    }
    else if (TextUtils.isEmpty(inputDescription))
    {
        Toast.makeText(AddNewProduct.this,"Product Description is mandatory",Toast.LENGTH_SHORT).show();
    }
    else if (TextUtils.isEmpty(inputPrice))
    {
        Toast.makeText(AddNewProduct.this,"Product Price is mandatory",Toast.LENGTH_SHORT).show();
    }
    else if (TextUtils.isEmpty(inputQuantity))
    {
        Toast.makeText(AddNewProduct.this,"Product Quantity is mandatory",Toast.LENGTH_SHORT).show();
    }
    else if (TextUtils.isEmpty(inputSellerName))
    {
        Toast.makeText(AddNewProduct.this,"Seller Name is mandatory",Toast.LENGTH_SHORT).show();
    }
    else if (TextUtils.isEmpty(inputLocation))
    {
        Toast.makeText(AddNewProduct.this,"Seller Loaction is mandatory",Toast.LENGTH_SHORT).show();
    }
    else
    {
        StoreProductInfo();

    }

    }
    private void StoreProductInfo()
    {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDAte=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDAte.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss");
        savecurrentTime=currentTime.format(calendar.getTime());
        //Toast.makeText(AddNewProduct.this,ProductKey,Toast.LENGTH_SHORT).show();

        ProductKey=saveCurrentDate+savecurrentTime;

        final StorageReference filePath=ProductImageRf.child(ImageUri.getLastPathSegment()+ProductKey);
        final UploadTask uploadTask=filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.toString();
                Toast.makeText(AddNewProduct.this,"Error "+e,Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddNewProduct.this,"Image Uploaded Successfully",Toast.LENGTH_SHORT).show();
                Task<Uri> urlTAsk=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        downloadImageUrl=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()) {
                            downloadImageUrl=task.getResult().toString();
                            Toast.makeText(AddNewProduct.this, "Product Image Saved To Database Successfully!Getting Download URL", Toast.LENGTH_SHORT).show();
                            SaveProductInfoDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoDatabase()
    {
        HashMap<String,Object> productMap=new HashMap<>();
        productMap.put("pid",ProductKey);
        productMap.put("name",inputProductName);
        productMap.put("description",inputDescription);
        productMap.put("price",inputPrice);
        productMap.put("quantity",inputQuantity);
        productMap.put("seller",inputSellerName);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",savecurrentTime);
        productMap.put("category",CategoryName);
        productMap.put("image",downloadImageUrl);
        ProductDataBaseRef.child(ProductKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(AddNewProduct.this, "Product added successfully to shopping window", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent=new Intent(AddNewProduct.this,ProductsCategoryRetailer.class);
                    startActivity(intent);
                }
                else
                {
                    String message=task.getException().toString();
                    Toast.makeText(AddNewProduct.this,"Error:"+message,Toast.LENGTH_SHORT).show();
                }
            }
        });







    }


}
