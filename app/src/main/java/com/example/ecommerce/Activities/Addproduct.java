package com.example.ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ecommerce.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

public class Addproduct extends AppCompatActivity {
    ImageView imageView;
    String image;
    EditText name;
    EditText price;
    EditText Quantity;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        name=findViewById(R.id.editTextTextPersonName);
        price=findViewById(R.id.editTextTextPersonName2);
        Quantity=findViewById(R.id.editTextTextPersonName3);
         imageView=findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"pick an image"),1);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            try {
                InputStream inputStream=getContentResolver().openInputStream(data.getData());
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                //encode to string
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[]bytes=stream.toByteArray();
                image=Base64.getEncoder().encodeToString(bytes);
               // byte[]b=Base64.getDecoder().decode(image);
               // Bitmap bit=BitmapFactory.decodeByteArray(b,0,b.length);
                imageView.setImageBitmap(bitmap);


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(Addproduct.this,LogInActivity.class));
        return true;
    }
}