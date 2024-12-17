package com.example.appproject.Helper;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appproject.Domain.FoodDomain;
import com.example.appproject.Interface.ChangeNumberItemListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ManagmentCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }


    public void insertFood(FoodDomain item) {
        ArrayList<Object> listFood = getListCart();
        boolean existArray = false;
        int n = 0;

        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i) instanceof FoodDomain) {
                FoodDomain food = (FoodDomain) listFood.get(i);
                if (food.getTitle().equals(item.getTitle())) {
                    existArray = true;
                    n = i;
                    break;
                }
            }
        }
        if(existArray){
            ((FoodDomain) listFood.get(n)).setNumberInCart(item.getNumberInCart());
        }else{
            listFood.add(item) ;
        }
        tinyDB.putListObject("CartList",listFood);
        Toast.makeText(context,"Added To your Cart",Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Object> getListCart() {
        return tinyDB.getListObject("CartList",FoodDomain.class);
    }
    public void plusNumberFood(ArrayList<FoodDomain> listFood, int position , ChangeNumberItemListener changeNumberItemListener){
        listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart()+1);
        tinyDB.putListObject("CartList", new ArrayList<>(listFood));
        changeNumberItemListener.changed();
    }

    public void minusNumberFood(ArrayList<FoodDomain> listFood, int position , ChangeNumberItemListener changeNumberItemListener){
        if(listFood.get(position).getNumberInCart() == 1)
            listFood.remove(position);
        else
            listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart()-1);
        tinyDB.putListObject("CartList", new ArrayList<>(listFood));
        changeNumberItemListener.changed();
    }
    public Double getTotalFee(){
        ArrayList<Object> listFood = getListCart();
        double fee = 0 ;
        for(int i = 0 ; i < listFood.size() ;++i ){
            if (listFood.get(i) instanceof FoodDomain) {
                FoodDomain food = (FoodDomain) listFood.get(i);
                fee += food.getFee() * food.getNumberInCart();
            }
        }
        return fee ;
    }
}

