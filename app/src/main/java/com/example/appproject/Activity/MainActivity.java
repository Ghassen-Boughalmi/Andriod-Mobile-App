package com.example.appproject.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.Adaptor.CategoryAdaptor;
import com.example.appproject.Adaptor.PopulerAdaptor;
import com.example.appproject.Domain.CategoryDomain;
import com.example.appproject.Domain.FoodDomain;
import com.example.appproject.Helper.DatabaseHelper;
import com.example.appproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView.Adapter adapter ,adapter2;
    private RecyclerView recyclerViewCategoryList , recyclerViewPopulerList ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;

//        });
        ((TextView)findViewById(R.id.username)).setText(getIntent().getStringExtra("username"));
        recyclerViewCategory();
        recyclerViewPopuler();
        bottomNavigation() ;
    }

    private void bottomNavigation(){
        FloatingActionButton floatingActionButton= findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
    }
    private void recyclerViewCategory(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView) ;
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ArrayList<CategoryDomain> category = dbHelper.getAllCategories();

        adapter = new CategoryAdaptor(category) ;
        recyclerViewCategoryList.setAdapter(adapter);
    }
    private void recyclerViewPopuler(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false) ;
        recyclerViewPopulerList = findViewById(R.id.recyclerView2);
        recyclerViewPopulerList.setLayoutManager(linearLayoutManager);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ArrayList<FoodDomain> foodList = dbHelper.getAllFoods();
        adapter2 = new PopulerAdaptor(foodList);
        recyclerViewPopulerList.setAdapter(adapter2);
    }
}