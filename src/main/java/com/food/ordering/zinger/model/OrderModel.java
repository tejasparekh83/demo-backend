package com.food.ordering.zinger.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.food.ordering.zinger.constant.Enums.OrderStatus;

import java.sql.Timestamp;

public class OrderModel {
    private Integer id;
    private UserModel userModel;
    private ShopModel shopModel;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Kolkata")
    private Timestamp date;

    private OrderStatus orderStatus;

    private Double price;
    private Double deliveryPrice;
    private String deliveryLocation;
    private String cookingInfo;
    private Double rating;
    private String feedback;
    private String secretKey;

    public OrderModel() {
        userModel = new UserModel();
        shopModel = new ShopModel();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public ShopModel getShopModel() {
        return shopModel;
    }

    public void setShopModel(ShopModel shopModel) {
        this.shopModel = shopModel;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getCookingInfo() {
        return cookingInfo;
    }

    public void setCookingInfo(String cookingInfo) {
        this.cookingInfo = cookingInfo;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id=" + id +
                ", userModel=" + userModel +
                ", shopModel=" + shopModel +
                ", date=" + date +
                ", orderStatus=" + orderStatus +
                ", price=" + price +
                ", deliveryPrice=" + deliveryPrice +
                ", deliveryLocation='" + deliveryLocation + '\'' +
                ", cookingInfo='" + cookingInfo + '\'' +
                ", rating=" + rating +
                ", feedback='" + feedback + '\'' +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }
}
