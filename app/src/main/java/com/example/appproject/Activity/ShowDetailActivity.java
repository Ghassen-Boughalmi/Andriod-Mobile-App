package com.example.appproject.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.appproject.Domain.FoodDomain;
import com.example.appproject.Helper.ManagmentCart;
import com.example.appproject.R;

public class ShowDetailActivity extends AppCompatActivity {

    private TextView addToCartBtn;
    private TextView titleTxt ,feeTxt ,descriptionTxt ,numberOrederTxt ;
    private ImageView plusBtn, minusBtn, picFood ;
    private FoodDomain object ;
    private int numberOrder = 1 ;
    private ManagmentCart managmentCart ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_detail);
        managmentCart = new ManagmentCart(this) ;

        initView();
        getBundle();

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
 //       });
    }

    private void getBundle(){
        object = (FoodDomain) getIntent().getSerializableExtra("object") ;
        int drawableResourceId = this.getResources().getIdentifier(object.getPic(),"drawable",this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(picFood) ;
        titleTxt.setText(object.getTitle());
        feeTxt.setText("$"+object.getFee());
        descriptionTxt.setText(object.getDescription());
        numberOrederTxt.setText(String.valueOf(numberOrder));
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrederTxt.setText(String.valueOf(++numberOrder));

            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOrder > 1 ) --numberOrder ;
                numberOrederTxt.setText(String.valueOf(numberOrder));

            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberInCart(numberOrder);
                managmentCart.insertFood(object);
            }
        });
    }
    private void initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn) ;
        titleTxt = findViewById(R.id.titleTxt) ;
        feeTxt = findViewById(R.id.priceTxt) ;
        descriptionTxt = findViewById(R.id.descriptionTxt) ;
        numberOrederTxt = findViewById(R.id.numberOrderTxt) ;
        plusBtn = findViewById(R.id.plusBtn) ;
        minusBtn = findViewById(R.id.minusBtn) ;
        picFood = findViewById(R.id.picfood);
    }

}