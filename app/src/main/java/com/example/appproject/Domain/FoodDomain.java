package com.example.appproject.Domain;

import java.io.Serializable;

public class FoodDomain  implements Serializable {
    private String title ;
    private String pic ;
    private String description ;
    private double fee ;
    private int numberInCart ;

    public FoodDomain(String title, String pic, String description, double fee, int numberInCart) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.numberInCart = numberInCart;
    }

    public FoodDomain(String title,String pic,String description, double fee) {
        this.fee = fee;
        this.description = description;
        this.pic = pic;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
