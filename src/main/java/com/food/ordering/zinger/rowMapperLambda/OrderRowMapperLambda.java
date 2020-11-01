package com.food.ordering.zinger.rowMapperLambda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.zinger.constant.Column;
import com.food.ordering.zinger.constant.Enums;
import com.food.ordering.zinger.model.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.food.ordering.zinger.constant.Column.OrderColumn.*;

public class OrderRowMapperLambda {
    public static final RowMapper<OrderItemListModel> OrderByUserIdRowMapperLambda = (rs, rownum) -> {
        OrderItemListModel orderItemListModel = new OrderItemListModel();

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setTransactionId(rs.getString(Column.TransactionColumn.transactionId));
        transactionModel.setPaymentMode(rs.getString(Column.TransactionColumn.paymentMode));

        OrderModel orderModel = new OrderModel();
        orderModel.setId(rs.getInt(id));
        orderModel.setDate(rs.getTimestamp(date));
        orderModel.setPrice(rs.getDouble(price));
        orderModel.setDeliveryPrice(rs.getDouble(deliveryPrice));
        orderModel.setDeliveryLocation(rs.getString(deliveryLocation));
        orderModel.setCookingInfo(rs.getString(cookingInfo));
        orderModel.setRating(rs.getDouble(rating));
        orderModel.setFeedback(rs.getString(feedback));
        orderModel.setSecretKey(rs.getString(secretKey));
        orderModel.setUserModel(null);

        ShopModel shopModel = new ShopModel();
        shopModel.setName(rs.getString(Column.shopName));
        shopModel.setPhotoUrl(rs.getString(Column.ShopColumn.photoUrl));
        shopModel.setMobile(rs.getString(Column.shopMobile));
        shopModel.setPlaceModel(null);

        orderModel.setShopModel(shopModel);
        transactionModel.setOrderModel(orderModel);

        ArrayList<OrderItemModel> orderItemListModelList = new ArrayList<>();
        ArrayList<OrderStatusModel> orderStatusModelList = new ArrayList<>();
        String[] itemNameList = rs.getString(Column.itemName).split(",");
        String[] itemPriceList = rs.getString(Column.itemPrice).split(",");
        String[] isVegList = rs.getString(Column.ItemColumn.isVeg).split(",");
        String[] quantityList = rs.getString(Column.OrderItemColumn.quantity).split(",");
        String[] orderItemPriceList = rs.getString(Column.orderItemPrice).split(",");
        String[] orderStatusList = rs.getString(Column.OrderColumn.status).split(",");
        String[] updatedTimeList = rs.getString(Column.OrderStatusColumn.updatedTime).split(",");

        for (int i = 0; i < itemNameList.length; i++) {

            OrderItemModel orderItemModel = new OrderItemModel();
            orderItemModel.setOrderModel(null);

            ItemModel itemModel = new ItemModel();
            itemModel.setName(itemNameList[i]);
            itemModel.setPrice(Double.valueOf(itemPriceList[i]));
            itemModel.setIsVeg(Integer.valueOf(isVegList[i]));
            itemModel.setShopModel(null);

            orderItemModel.setItemModel(itemModel);
            orderItemModel.setQuantity(Integer.valueOf(quantityList[i]));
            orderItemModel.setPrice(Double.valueOf(orderItemPriceList[i]));
            orderItemListModelList.add(orderItemModel);
        }

        for (int i = 0; i < orderStatusList.length; i++) {
            OrderStatusModel orderStatusModel = new OrderStatusModel();
            orderStatusModel.setOrderStatus(Enums.OrderStatus.valueOf(orderStatusList[i]));

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                orderStatusModel.setUpdatedTime(new Timestamp(formatter.parse(updatedTimeList[i]).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            orderStatusModelList.add(orderStatusModel);
        }
        orderItemListModel.setTransactionModel(transactionModel);
        orderItemListModel.setOrderItemsList(orderItemListModelList);
        orderItemListModel.setOrderStatusModel(orderStatusModelList);
        return orderItemListModel;
    };

    public static final RowMapper<OrderItemListModel> OrderBySearchQueryRowMapperLambda = (rs, rownum) -> {
        OrderItemListModel orderItemListModel = new OrderItemListModel();

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setTransactionId(rs.getString(Column.TransactionColumn.transactionId));
        transactionModel.setPaymentMode(rs.getString(Column.TransactionColumn.paymentMode));

        OrderModel orderModel = new OrderModel();
        orderModel.setId(rs.getInt(id));
        orderModel.setDate(rs.getTimestamp(date));
        orderModel.setPrice(rs.getDouble(price));
        orderModel.setDeliveryPrice(rs.getDouble(deliveryPrice));
        orderModel.setDeliveryLocation(rs.getString(deliveryLocation));
        orderModel.setCookingInfo(rs.getString(cookingInfo));
        orderModel.setRating(rs.getDouble(rating));
        orderModel.setFeedback(rs.getString(feedback));
        orderModel.setSecretKey(rs.getString(secretKey));
        orderModel.setShopModel(null);

        UserModel userModel = new UserModel();
        userModel.setName(rs.getString(Column.userName));
        userModel.setMobile(rs.getString(Column.userMobile));

        orderModel.setUserModel(userModel);
        transactionModel.setOrderModel(orderModel);

        ArrayList<OrderItemModel> orderItemListModelList = new ArrayList<>();
        ArrayList<OrderStatusModel> orderStatusModelList = new ArrayList<>();
        String[] itemNameList = rs.getString(Column.itemName).split(",");
        String[] itemPriceList = rs.getString(Column.itemPrice).split(",");
        String[] isVegList = rs.getString(Column.ItemColumn.isVeg).split(",");
        String[] quantityList = rs.getString(Column.OrderItemColumn.quantity).split(",");
        String[] orderItemPriceList = rs.getString(Column.orderItemPrice).split(",");
        String[] orderStatusList = rs.getString(Column.OrderColumn.status).split(",");
        String[] updatedTimeList = rs.getString(Column.OrderStatusColumn.updatedTime).split(",");

        for (int i = 0; i < itemNameList.length; i++) {
            OrderItemModel orderItemModel = new OrderItemModel();
            orderItemModel.setOrderModel(null);

            ItemModel itemModel = new ItemModel();
            itemModel.setName(itemNameList[i]);
            itemModel.setPrice(Double.valueOf(itemPriceList[i]));
            itemModel.setIsVeg(Integer.valueOf(isVegList[i]));
            itemModel.setShopModel(null);

            orderItemModel.setItemModel(itemModel);
            orderItemModel.setQuantity(Integer.valueOf(quantityList[i]));
            orderItemModel.setPrice(Double.valueOf(orderItemPriceList[i]));
            orderItemListModelList.add(orderItemModel);
        }

        for (int i = 0; i < orderStatusList.length; i++) {
            OrderStatusModel orderStatusModel = new OrderStatusModel();
            orderStatusModel.setOrderStatus(Enums.OrderStatus.valueOf(orderStatusList[i]));

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                orderStatusModel.setUpdatedTime(new Timestamp(formatter.parse(updatedTimeList[i]).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            orderStatusModelList.add(orderStatusModel);
        }
        orderItemListModel.setTransactionModel(transactionModel);
        orderItemListModel.setOrderItemsList(orderItemListModelList);
        orderItemListModel.setOrderStatusModel(orderStatusModelList);
        return orderItemListModel;
    };

    public static final RowMapper<OrderItemListModel> OrderByIdRowMapper = (rs, rownum) -> {
        OrderItemListModel orderItemListModel = new OrderItemListModel();

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setTransactionId(rs.getString(Column.TransactionColumn.transactionId));
        transactionModel.setPaymentMode(rs.getString(Column.TransactionColumn.paymentMode));

        OrderModel orderModel = new OrderModel();
        orderModel.setId(rs.getInt(id));
        orderModel.setDate(rs.getTimestamp(date));
        orderModel.setPrice(rs.getDouble(price));
        orderModel.setDeliveryPrice(rs.getDouble(deliveryPrice));
        orderModel.setDeliveryLocation(rs.getString(deliveryLocation));
        orderModel.setCookingInfo(rs.getString(cookingInfo));
        orderModel.setRating(rs.getDouble(rating));
        orderModel.setFeedback(rs.getString(feedback));
        orderModel.setSecretKey(rs.getString(secretKey));

        ShopModel shopModel = new ShopModel();
        shopModel.setName(rs.getString(Column.shopName));
        shopModel.setId(rs.getInt(Column.shopId));
        shopModel.setPhotoUrl(rs.getString(Column.ShopColumn.photoUrl));
        shopModel.setMobile(rs.getString(Column.shopMobile));
        shopModel.setPlaceModel(null);
        orderModel.setShopModel(shopModel);

        UserModel userModel = new UserModel();
        userModel.setName(rs.getString(Column.userName));
        userModel.setMobile(rs.getString(Column.userMobile));
        try {
            userModel.setNotificationToken(new ObjectMapper().readValue(rs.getString(Column.UserColumn.notifToken), List.class));
        } catch (JsonProcessingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            userModel.setNotificationToken(new ArrayList<String>());
        }

        orderModel.setUserModel(userModel);
        transactionModel.setOrderModel(orderModel);

        ArrayList<OrderItemModel> orderItemListModelList = new ArrayList<>();
        ArrayList<OrderStatusModel> orderStatusModelList = new ArrayList<>();
        String[] itemNameList = rs.getString(Column.itemName).split(",");
        String[] itemPriceList = rs.getString(Column.itemPrice).split(",");
        String[] isVegList = rs.getString(Column.ItemColumn.isVeg).split(",");
        String[] quantityList = rs.getString(Column.OrderItemColumn.quantity).split(",");
        String[] orderItemPriceList = rs.getString(Column.orderItemPrice).split(",");
        String[] orderStatusList = rs.getString(Column.OrderColumn.status).split(",");
        String[] updatedTimeList = rs.getString(Column.OrderStatusColumn.updatedTime).split(",");

        for (int i = 0; i < itemNameList.length; i++) {
            OrderItemModel orderItemModel = new OrderItemModel();
            orderItemModel.setOrderModel(null);

            ItemModel itemModel = new ItemModel();
            itemModel.setName(itemNameList[i]);
            itemModel.setPrice(Double.valueOf(itemPriceList[i]));
            itemModel.setIsVeg(Integer.valueOf(isVegList[i]));
            itemModel.setShopModel(null);

            orderItemModel.setItemModel(itemModel);
            orderItemModel.setQuantity(Integer.valueOf(quantityList[i]));
            orderItemModel.setPrice(Double.valueOf(orderItemPriceList[i]));
            orderItemListModelList.add(orderItemModel);
        }

        for (int i = 0; i < orderStatusList.length; i++) {
            OrderStatusModel orderStatusModel = new OrderStatusModel();
            orderStatusModel.setOrderStatus(Enums.OrderStatus.valueOf(orderStatusList[i]));

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                orderStatusModel.setUpdatedTime(new Timestamp(formatter.parse(updatedTimeList[i]).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            orderStatusModelList.add(orderStatusModel);
        }
        orderItemListModel.setTransactionModel(transactionModel);
        orderItemListModel.setOrderItemsList(orderItemListModelList);
        orderItemListModel.setOrderStatusModel(orderStatusModelList);
        return orderItemListModel;
    };

    public static final RowMapper<OrderModel> orderPriceRowMapperLambda = (rs, rownum) -> {
        OrderModel orderModel = new OrderModel();
        orderModel.setId(rs.getInt(id));
        orderModel.setUserModel(null);
        orderModel.setShopModel(null);
        orderModel.setPrice(rs.getDouble(price));
        return orderModel;
    };

    public static final RowMapper<OrderModel> orderByStatusRowMapperLambda = (rs, rownum) -> {
        OrderModel orderModel = new OrderModel();
        orderModel.setId(rs.getInt(id));
        orderModel.setUserModel(null);
        orderModel.setShopModel(null);
        orderModel.setDate(rs.getTimestamp(date));
        return orderModel;
    };
}
