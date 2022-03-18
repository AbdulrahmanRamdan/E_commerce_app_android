package com.example.ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ecommerce.Adapter.CategoriesArrayAdapter;
import com.example.ecommerce.Recyleview.CategoriesItemDecoration;
import com.example.ecommerce.DataBase.EcommerceDataBase;
import com.example.ecommerce.recyfun.OnRecyclerViewCategoryItemClickListener;
import com.example.ecommerce.R;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {
    RecyclerView categories_recyceler_view ;
    CategoriesArrayAdapter adapter;
    ArrayList<String> titles;
    ArrayList<Integer> images;
    EcommerceDataBase dbobj;
    Cursor cursor_allcategories;
    SharedPreferences shareref;

    int Customer_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);



        // This to Receive The Customer ID from Login Activity To Pass it to Product Activity
        Intent anything = getIntent();
        Customer_ID = anything.getIntExtra("custid" , -1);

        categories_recyceler_view = findViewById(R.id.categories_recyclerview);

        titles = new ArrayList<>();
        images = new ArrayList<>();


        getTitleandImages(getApplicationContext());


        adapter = new CategoriesArrayAdapter(titles, images, new OnRecyclerViewCategoryItemClickListener() {
            @Override
            public void OnItemClick(String s) {
                Intent i = new Intent(getApplicationContext() , ProductsActivity.class);
                Bundle b = new Bundle();
                b.putString("category" , s);
                b.putInt("custid" , Customer_ID);
                i.putExtras(b);
                startActivity(i);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext() , 2 , GridLayoutManager.VERTICAL , false);
        categories_recyceler_view.setAdapter(adapter);
        categories_recyceler_view.setLayoutManager(gridLayoutManager);

        categories_recyceler_view.addItemDecoration(new CategoriesItemDecoration(2, 50, true));
    }

    public void getTitleandImages(Context  context){
        dbobj = new EcommerceDataBase(context);
        cursor_allcategories = dbobj.GetAllCategories();

        while(!cursor_allcategories.isAfterLast()){
            titles.add(cursor_allcategories.getString(1));
            System.out.println(cursor_allcategories.getString(1));
            cursor_allcategories.moveToNext();
        }

        images.add(R.drawable.computer);
        images.add(R.drawable.labtop);
        images.add(R.drawable.mobile_and_tablet);
        images.add(R.drawable.camera);
        images.add(R.drawable.watche);
        images.add(R.drawable.tv);
        images.add(R.drawable.projector);
        images.add(R.drawable.men_clothes);
        images.add(R.drawable.book);
        images.add(R.drawable.sport);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        shareref = getSharedPreferences("Remember Me" , Context.MODE_PRIVATE);
        SharedPreferences.Editor myedit = shareref.edit();
        myedit.putString("Username" , "null");
        myedit.putString("Password" , "null");
        myedit.commit();
        startActivity(new Intent(CategoriesActivity.this,LogInActivity.class));
        return true;
    }
}