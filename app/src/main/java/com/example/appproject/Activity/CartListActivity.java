package com.example.appproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.Adaptor.CartListAdapter;
import com.example.appproject.Adaptor.CategoryAdaptor;
import com.example.appproject.Domain.FoodDomain;
import com.example.appproject.Helper.ManagmentCart;
import com.example.appproject.Interface.ChangeNumberItemListener;
import com.example.appproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter ;
    private RecyclerView recyclerViewList ;
    private ManagmentCart managmentCart ;
    TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    private double tax ;
    private ScrollView scrollView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart_list);
        managmentCart = new ManagmentCart(this) ;
        initView();
        initList() ;
        CalculateCart() ;
        bottomNavigation() ;
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
    private void bottomNavigation(){
        FloatingActionButton floatingActionButton= findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this,CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerView) ;
        totalFeeTxt = findViewById(R.id.TotalFeeTxt) ;
        deliveryTxt = findViewById(R.id.delFeesTxt) ;
        taxTxt = findViewById(R.id.taxTxt);
        totalTxt = findViewById(R.id.totalPriceTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView3) ;
        recyclerViewList = findViewById(R.id.rv);
    }
    private void initList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false) ;
        recyclerViewList.setLayoutManager(linearLayoutManager);
        ArrayList<FoodDomain> foodList = new ArrayList<>();
        for (Object obj : managmentCart.getListCart()) {
            if (obj instanceof FoodDomain) {
                foodList.add((FoodDomain) obj);
            }
        }
        adapter = new CartListAdapter(foodList, this, new ChangeNumberItemListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if(managmentCart.getListCart().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }else{
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }

    }
    private void CalculateCart(){
        double percentTax = 0.02 ;
        double delivery = 10 ;

        tax = (double) Math.round((managmentCart.getTotalFee() * percentTax) * 100) /100 ;
        double total = (double) Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) /100 ;
        double itemTotal = Math.round(managmentCart.getTotalFee()*100)/100 ;

        totalFeeTxt.setText("$"+itemTotal);
        taxTxt.setText("$"+tax);
        deliveryTxt.setText("$"+delivery);
        totalTxt.setText("$"+total);

    }
}