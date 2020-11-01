package com.food.ordering.zinger.constant;

import com.food.ordering.zinger.constant.Column.*;
import com.food.ordering.zinger.constant.Enums.OrderStatus;
import com.food.ordering.zinger.model.ItemModel;
import com.food.ordering.zinger.model.OrderItemModel;

import java.util.List;

import static com.food.ordering.zinger.constant.Column.OrderItemColumn.*;
import static com.food.ordering.zinger.constant.Column.*;
import static com.food.ordering.zinger.constant.Enums.UserRole.*;
import static com.food.ordering.zinger.constant.Sql.*;

public class Query {
    public static final class AuditLogQuery {

        public static final String insertLog = INSERT_INTO + ApplicationLogColumn.tableName + LEFT_PARANTHESIS +
                ApplicationLogColumn.requestType + COMMA +
                ApplicationLogColumn.endpointUrl + COMMA +
                ApplicationLogColumn.requestHeader + COMMA +
                ApplicationLogColumn.requestObject + COMMA +
                ApplicationLogColumn.responseObject +
                RIGHT_PARANTHESIS + VALUES + LEFT_PARANTHESIS +
                COLON + ApplicationLogColumn.requestType +
                COMMA_COLON + ApplicationLogColumn.endpointUrl +
                COMMA_COLON + ApplicationLogColumn.requestHeader +
                COMMA_COLON + ApplicationLogColumn.requestObject +
                COMMA_COLON + ApplicationLogColumn.responseObject +
                RIGHT_PARANTHESIS;
    }

    public static final class PlaceQuery {
        public static final String notDeleted = PlaceColumn.isDelete + " = 0";

        public static final String insertPlace = INSERT_INTO + PlaceColumn.tableName + LEFT_PARANTHESIS +
                PlaceColumn.name + COMMA +
                PlaceColumn.iconUrl + COMMA +
                PlaceColumn.address + RIGHT_PARANTHESIS + VALUES + LEFT_PARANTHESIS +
                COLON + PlaceColumn.name +
                COMMA_COLON + PlaceColumn.iconUrl +
                COMMA_COLON + PlaceColumn.address + RIGHT_PARANTHESIS;

        public static final String getAllPlaces = SELECT +
                PlaceColumn.id + COMMA +
                PlaceColumn.name + COMMA +
                PlaceColumn.iconUrl + COMMA +
                PlaceColumn.address + FROM + PlaceColumn.tableName + WHERE +
                notDeleted +
                ORDER_BY + PlaceColumn.name + ASC;

        //TODO: Issue #4 Zinger Admin API for Place
        public static final String updatePlace = UPDATE + PlaceColumn.tableName + SET +
                PlaceColumn.name + EQUAL_COLON + PlaceColumn.name + COMMA +
                PlaceColumn.iconUrl + EQUAL_COLON + PlaceColumn.iconUrl + COMMA +
                PlaceColumn.address + EQUAL_COLON + PlaceColumn.address + WHERE +
                PlaceColumn.id + EQUAL_COLON + PlaceColumn.id;

        public static final String deletePlace = UPDATE + PlaceColumn.tableName + SET +
                PlaceColumn.isDelete + " = 1" + WHERE +
                PlaceColumn.id + EQUAL_COLON + PlaceColumn.id;
    }

    public static final class ItemQuery {
        public static final String notDeleted = ItemColumn.isDelete + " = 0";

        public static final String getItemsByShopId = SELECT +
                ItemColumn.id + COMMA +
                ItemColumn.name + COMMA +
                ItemColumn.price + COMMA +
                ItemColumn.photoUrl + COMMA +
                ItemColumn.category + COMMA +
                ItemColumn.shopId + COMMA +
                ItemColumn.isVeg + COMMA +
                ItemColumn.isAvailable + FROM + ItemColumn.tableName + WHERE +
                ItemColumn.shopId + EQUAL_COLON + ItemColumn.shopId + AND +
                notDeleted;

        public static final String getItemsByName = SELECT +
                ItemColumn.tableName + DOT + ItemColumn.id + COMMA +
                ItemColumn.tableName + DOT + ItemColumn.name + COMMA +
                ItemColumn.tableName + DOT + ItemColumn.price + COMMA +
                ItemColumn.tableName + DOT + ItemColumn.photoUrl + COMMA +
                ItemColumn.tableName + DOT + ItemColumn.category + COMMA +
                ItemColumn.tableName + DOT + ItemColumn.shopId + COMMA +
                ItemColumn.tableName + DOT + ItemColumn.isVeg + COMMA +
                ItemColumn.tableName + DOT + ItemColumn.isAvailable + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.name + AS + shopName + FROM + ItemColumn.tableName +
                INNER_JOIN + ShopColumn.tableName + ON +
                ItemColumn.tableName + DOT + ItemColumn.shopId + EQUALS + ShopColumn.tableName + DOT + ShopColumn.id + AND +
                ShopColumn.tableName + DOT + ShopColumn.placeId + EQUAL_COLON + ShopColumn.placeId + WHERE +
                ItemColumn.tableName + DOT + ItemColumn.name + LIKE + COLON + ItemColumn.name + AND +
                ItemColumn.tableName + DOT + notDeleted;
        public static final String updateItem = UPDATE + ItemColumn.tableName + SET +
                ItemColumn.name + EQUAL_COLON + ItemColumn.name + COMMA +
                ItemColumn.price + EQUAL_COLON + ItemColumn.price + COMMA +
                ItemColumn.photoUrl + EQUAL_COLON + ItemColumn.photoUrl + COMMA +
                ItemColumn.category + EQUAL_COLON + ItemColumn.category + COMMA +
                ItemColumn.isVeg + EQUAL_COLON + ItemColumn.isVeg + COMMA +
                ItemColumn.isAvailable + EQUAL_COLON + ItemColumn.isAvailable + WHERE +
                ItemColumn.id + EQUAL_COLON + ItemColumn.id;
        public static final String deleteItem = UPDATE + ItemColumn.tableName + SET +
                ItemColumn.isDelete + " = 1" + WHERE +
                ItemColumn.id + EQUAL_COLON + ItemColumn.id;


        public static String getInsertItem(List<ItemModel> itemModelList) {
            StringBuilder insertItem = new StringBuilder(INSERT_INTO + ItemColumn.tableName + LEFT_PARANTHESIS +
                    ItemColumn.name + COMMA +
                    ItemColumn.price + COMMA +
                    ItemColumn.photoUrl + COMMA +
                    ItemColumn.category + COMMA +
                    ItemColumn.shopId + COMMA +
                    ItemColumn.isVeg + RIGHT_PARANTHESIS + VALUES);

            for (int i = 0; i < itemModelList.size(); i++) {
                insertItem.append(LEFT_PARANTHESIS)
                        .append(COLON).append(ItemColumn.name).append(i)
                        .append(COMMA_COLON).append(ItemColumn.price).append(i)
                        .append(COMMA_COLON).append(ItemColumn.photoUrl).append(i)
                        .append(COMMA_COLON).append(ItemColumn.category).append(i)
                        .append(COMMA_COLON).append(ItemColumn.shopId).append(i)
                        .append(COMMA_COLON).append(ItemColumn.isVeg).append(i)
                        .append(RIGHT_PARANTHESIS);
                if (i < itemModelList.size() - 1)
                    insertItem.append(COMMA);
            }

            return insertItem.toString();
        }
    }

    public static final class OrderQuery {
        public static final String pageNum = "pageNum";
        public static final String pageCount = "pageCount";
        public static final String getOrderByUserId = SELECT +
                LHS + DOT + OrderColumn.id + COMMA +
                LHS + DOT + OrderColumn.date + COMMA +
                LHS + DOT + OrderColumn.price + COMMA +
                LHS + DOT + OrderColumn.deliveryPrice + COMMA +
                LHS + DOT + OrderColumn.deliveryLocation + COMMA +
                LHS + DOT + OrderColumn.cookingInfo + COMMA +
                LHS + DOT + OrderColumn.rating + COMMA +
                LHS + DOT + OrderColumn.feedback + COMMA +
                LHS + DOT + OrderColumn.secretKey + COMMA +
                LHS + DOT + TransactionColumn.transactionId + COMMA +
                LHS + DOT + TransactionColumn.paymentMode + COMMA +
                LHS + DOT + shopName + COMMA +
                LHS + DOT + ShopColumn.photoUrl + COMMA +
                LHS + DOT + shopMobile + COMMA +
                LHS + DOT + itemName + COMMA +
                LHS + DOT + itemPrice + COMMA +
                LHS + DOT + ItemColumn.isVeg + COMMA +
                LHS + DOT + quantity + COMMA +
                LHS + DOT + orderItemPrice + COMMA +
                RHS + DOT + OrderColumn.status + COMMA +
                RHS + DOT + OrderStatusColumn.updatedTime + FROM + LEFT_PARANTHESIS + SELECT +
                OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.date + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.price + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.deliveryPrice + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.deliveryLocation + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.cookingInfo + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.rating + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.feedback + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.secretKey + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.transactionId + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.paymentMode + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.name + AS + shopName + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.photoUrl + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.mobile + AS + shopMobile + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.name + RIGHT_PARANTHESIS + AS + itemName + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.price + RIGHT_PARANTHESIS + AS + itemPrice + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.isVeg + RIGHT_PARANTHESIS + AS + ItemColumn.isVeg + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderItemColumn.tableName + DOT + quantity + RIGHT_PARANTHESIS + AS + quantity + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderItemColumn.tableName + DOT + price + RIGHT_PARANTHESIS + AS + orderItemPrice + FROM + OrderColumn.tableName +
                INNER_JOIN + TransactionColumn.tableName + ON +
                OrderColumn.tableName + DOT + OrderColumn.id + EQUALS + TransactionColumn.tableName + DOT + TransactionColumn.orderId + AND +
                OrderColumn.tableName + DOT + OrderColumn.userId + EQUAL_COLON + UserColumn.id +
                INNER_JOIN + OrderItemColumn.tableName + ON +
                OrderColumn.tableName + DOT + OrderColumn.id + EQUALS + OrderItemColumn.tableName + DOT + OrderItemColumn.orderId +
                INNER_JOIN + ItemColumn.tableName + ON +
                ItemColumn.tableName + DOT + ItemColumn.id + EQUALS + OrderItemColumn.tableName + DOT + itemId +
                INNER_JOIN + ShopColumn.tableName + ON +
                ShopColumn.tableName + DOT + ShopColumn.id + EQUALS + OrderColumn.tableName + DOT + OrderColumn.shopId +
                GROUP_BY + OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.transactionId +
                ORDER_BY + OrderColumn.tableName + DOT + OrderColumn.date + DESC +
                LIMIT + COLON + pageCount + OFFSET + COLON + pageNum + RIGHT_PARANTHESIS + AS + LHS + COMMA + LEFT_PARANTHESIS + SELECT +

                OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderStatusColumn.tableName + DOT + OrderStatusColumn.status + RIGHT_PARANTHESIS + AS + OrderStatusColumn.status + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderStatusColumn.tableName + DOT + OrderStatusColumn.updatedTime + RIGHT_PARANTHESIS + AS + OrderStatusColumn.updatedTime + FROM + OrderColumn.tableName +
                INNER_JOIN + OrderStatusColumn.tableName + ON +
                OrderStatusColumn.tableName + DOT + OrderStatusColumn.orderId + EQUALS + OrderColumn.tableName + DOT + OrderColumn.id + AND +
                OrderColumn.tableName + DOT + OrderColumn.userId + EQUAL_COLON + UserColumn.id +
                GROUP_BY + OrderColumn.tableName + DOT + OrderColumn.id +
                ORDER_BY + OrderColumn.tableName + DOT + OrderColumn.date + DESC +
                LIMIT + COLON + pageCount + OFFSET + COLON + pageNum + RIGHT_PARANTHESIS + AS + RHS + WHERE +
                LHS + DOT + OrderColumn.id + EQUALS + RHS + DOT + OrderColumn.id;
        public static final String getOrderByShopIdPaginated = SELECT +
                LHS + DOT + OrderColumn.id + COMMA +
                LHS + DOT + OrderColumn.date + COMMA +
                LHS + DOT + OrderColumn.price + COMMA +
                LHS + DOT + OrderColumn.deliveryPrice + COMMA +
                LHS + DOT + OrderColumn.deliveryLocation + COMMA +
                LHS + DOT + OrderColumn.cookingInfo + COMMA +
                LHS + DOT + OrderColumn.rating + COMMA +
                LHS + DOT + OrderColumn.feedback + COMMA +
                LHS + DOT + OrderColumn.secretKey + COMMA +
                LHS + DOT + TransactionColumn.transactionId + COMMA +
                LHS + DOT + TransactionColumn.paymentMode + COMMA +
                LHS + DOT + userName + COMMA +
                LHS + DOT + userMobile + COMMA +
                LHS + DOT + itemName + COMMA +
                LHS + DOT + itemPrice + COMMA +
                LHS + DOT + ItemColumn.isVeg + COMMA +
                LHS + DOT + quantity + COMMA +
                LHS + DOT + orderItemPrice + COMMA +
                RHS + DOT + OrderColumn.status + COMMA +
                RHS + DOT + OrderStatusColumn.updatedTime + FROM + LEFT_PARANTHESIS + SELECT +
                OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.date + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.price + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.deliveryPrice + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.deliveryLocation + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.cookingInfo + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.rating + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.feedback + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.secretKey + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.transactionId + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.paymentMode + COMMA +
                UserColumn.tableName + DOT + UserColumn.name + AS + userName + COMMA +
                UserColumn.tableName + DOT + UserColumn.mobile + AS + userMobile + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.name + RIGHT_PARANTHESIS + AS + itemName + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.price + RIGHT_PARANTHESIS + AS + itemPrice + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.isVeg + RIGHT_PARANTHESIS + AS + ItemColumn.isVeg + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderItemColumn.tableName + DOT + quantity + RIGHT_PARANTHESIS + AS + quantity + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderItemColumn.tableName + DOT + price + RIGHT_PARANTHESIS + AS + orderItemPrice + FROM + OrderColumn.tableName +
                INNER_JOIN + TransactionColumn.tableName + ON +
                OrderColumn.tableName + DOT + OrderColumn.id + EQUALS + TransactionColumn.tableName + DOT + TransactionColumn.orderId + AND +
                OrderColumn.tableName + DOT + OrderColumn.shopId + EQUAL_COLON + ShopColumn.id + AND +
                OrderColumn.tableName + DOT + OrderColumn.status + IN + LEFT_PARANTHESIS +
                SINGLE_QUOTE + OrderStatus.CANCELLED_BY_SELLER.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.CANCELLED_BY_USER.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.COMPLETED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.REFUND_INITIATED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.REFUND_COMPLETED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.DELIVERED.name() + SINGLE_QUOTE + RIGHT_PARANTHESIS +
                INNER_JOIN + OrderItemColumn.tableName + ON +
                OrderColumn.tableName + DOT + OrderColumn.id + EQUALS + OrderItemColumn.tableName + DOT + OrderItemColumn.orderId +
                INNER_JOIN + ItemColumn.tableName + ON +
                ItemColumn.tableName + DOT + ItemColumn.id + EQUALS + OrderItemColumn.tableName + DOT + itemId +
                INNER_JOIN + UserColumn.tableName + ON +
                UserColumn.tableName + DOT + UserColumn.id + EQUALS + OrderColumn.tableName + DOT + OrderColumn.userId +
                GROUP_BY + OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.transactionId +
                ORDER_BY + OrderColumn.tableName + DOT + OrderColumn.date + DESC +
                LIMIT + COLON + pageCount + OFFSET + COLON + pageNum + RIGHT_PARANTHESIS + AS + LHS + COMMA + LEFT_PARANTHESIS + SELECT +

                OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderStatusColumn.tableName + DOT + OrderStatusColumn.status + RIGHT_PARANTHESIS + AS + OrderStatusColumn.status + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderStatusColumn.tableName + DOT + OrderStatusColumn.updatedTime + RIGHT_PARANTHESIS + AS + OrderStatusColumn.updatedTime + FROM + OrderColumn.tableName +
                INNER_JOIN + OrderStatusColumn.tableName + ON +
                OrderStatusColumn.tableName + DOT + OrderStatusColumn.orderId + EQUALS + OrderColumn.tableName + DOT + OrderColumn.id + AND +
                OrderColumn.tableName + DOT + OrderColumn.shopId + EQUAL_COLON + ShopColumn.id + AND +
                OrderColumn.tableName + DOT + OrderColumn.status + IN + LEFT_PARANTHESIS +
                SINGLE_QUOTE + OrderStatus.CANCELLED_BY_SELLER.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.CANCELLED_BY_USER.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.COMPLETED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.REFUND_INITIATED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.REFUND_COMPLETED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.DELIVERED.name() + SINGLE_QUOTE + RIGHT_PARANTHESIS +
                GROUP_BY + OrderColumn.tableName + DOT + OrderColumn.id +
                ORDER_BY + OrderColumn.tableName + DOT + OrderColumn.date + DESC +
                LIMIT + COLON + pageCount + OFFSET + COLON + pageNum + RIGHT_PARANTHESIS + AS + RHS + WHERE +
                LHS + DOT + OrderColumn.id + EQUALS + RHS + DOT + OrderColumn.id;
        public static final String getOrderByShopId = SELECT +
                LHS + DOT + OrderColumn.id + COMMA +
                LHS + DOT + OrderColumn.date + COMMA +
                LHS + DOT + OrderColumn.price + COMMA +
                LHS + DOT + OrderColumn.deliveryPrice + COMMA +
                LHS + DOT + OrderColumn.deliveryLocation + COMMA +
                LHS + DOT + OrderColumn.cookingInfo + COMMA +
                LHS + DOT + OrderColumn.rating + COMMA +
                LHS + DOT + OrderColumn.feedback + COMMA +
                LHS + DOT + OrderColumn.secretKey + COMMA +
                LHS + DOT + TransactionColumn.transactionId + COMMA +
                LHS + DOT + TransactionColumn.paymentMode + COMMA +
                LHS + DOT + userName + COMMA +
                LHS + DOT + userMobile + COMMA +
                LHS + DOT + itemName + COMMA +
                LHS + DOT + itemPrice + COMMA +
                LHS + DOT + ItemColumn.isVeg + COMMA +
                LHS + DOT + quantity + COMMA +
                LHS + DOT + orderItemPrice + COMMA +
                RHS + DOT + OrderColumn.status + COMMA +
                RHS + DOT + OrderStatusColumn.updatedTime + FROM + LEFT_PARANTHESIS + SELECT +
                OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.date + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.price + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.deliveryPrice + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.deliveryLocation + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.cookingInfo + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.rating + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.feedback + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.secretKey + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.transactionId + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.paymentMode + COMMA +
                UserColumn.tableName + DOT + UserColumn.name + AS + userName + COMMA +
                UserColumn.tableName + DOT + UserColumn.mobile + AS + userMobile + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.name + RIGHT_PARANTHESIS + AS + itemName + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.price + RIGHT_PARANTHESIS + AS + itemPrice + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.isVeg + RIGHT_PARANTHESIS + AS + ItemColumn.isVeg + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderItemColumn.tableName + DOT + quantity + RIGHT_PARANTHESIS + AS + quantity + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderItemColumn.tableName + DOT + price + RIGHT_PARANTHESIS + AS + orderItemPrice + FROM + OrderColumn.tableName +
                INNER_JOIN + TransactionColumn.tableName + ON +
                OrderColumn.tableName + DOT + OrderColumn.id + EQUALS + TransactionColumn.tableName + DOT + TransactionColumn.orderId + AND +
                OrderColumn.tableName + DOT + OrderColumn.shopId + EQUAL_COLON + ShopColumn.id + AND +
                OrderColumn.tableName + DOT + OrderColumn.status + IN + LEFT_PARANTHESIS +
                SINGLE_QUOTE + OrderStatus.PLACED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.ACCEPTED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.READY.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.OUT_FOR_DELIVERY.name() + SINGLE_QUOTE + RIGHT_PARANTHESIS +
                INNER_JOIN + OrderItemColumn.tableName + ON +
                OrderColumn.tableName + DOT + OrderColumn.id + EQUALS + OrderItemColumn.tableName + DOT + OrderItemColumn.orderId +
                INNER_JOIN + ItemColumn.tableName + ON +
                ItemColumn.tableName + DOT + ItemColumn.id + EQUALS + OrderItemColumn.tableName + DOT + itemId +
                INNER_JOIN + UserColumn.tableName + ON +
                UserColumn.tableName + DOT + UserColumn.id + EQUALS + OrderColumn.tableName + DOT + OrderColumn.userId +
                GROUP_BY + OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.transactionId +
                ORDER_BY + OrderColumn.tableName + DOT + OrderColumn.date + RIGHT_PARANTHESIS + AS + LHS + COMMA + LEFT_PARANTHESIS + SELECT +

                OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderStatusColumn.tableName + DOT + OrderStatusColumn.status + RIGHT_PARANTHESIS + AS + OrderStatusColumn.status + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderStatusColumn.tableName + DOT + OrderStatusColumn.updatedTime + RIGHT_PARANTHESIS + AS + OrderStatusColumn.updatedTime + FROM + OrderColumn.tableName +
                INNER_JOIN + OrderStatusColumn.tableName + ON +
                OrderStatusColumn.tableName + DOT + OrderStatusColumn.orderId + EQUALS + OrderColumn.tableName + DOT + OrderColumn.id + AND +
                OrderColumn.tableName + DOT + OrderColumn.shopId + EQUAL_COLON + ShopColumn.id + AND +
                OrderColumn.tableName + DOT + OrderColumn.status + IN + LEFT_PARANTHESIS +
                SINGLE_QUOTE + OrderStatus.PLACED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.ACCEPTED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.READY.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.OUT_FOR_DELIVERY.name() + SINGLE_QUOTE + RIGHT_PARANTHESIS +
                GROUP_BY + OrderColumn.tableName + DOT + OrderColumn.id +
                ORDER_BY + OrderColumn.tableName + DOT + OrderColumn.date + RIGHT_PARANTHESIS + AS + RHS + WHERE +
                LHS + DOT + OrderColumn.id + EQUALS + RHS + DOT + OrderColumn.id;
        public static final String getOrderByFilterPaginated = SELECT +
                LHS + DOT + OrderColumn.id + COMMA +
                LHS + DOT + OrderColumn.date + COMMA +
                LHS + DOT + OrderColumn.price + COMMA +
                LHS + DOT + OrderColumn.deliveryPrice + COMMA +
                LHS + DOT + OrderColumn.deliveryLocation + COMMA +
                LHS + DOT + OrderColumn.cookingInfo + COMMA +
                LHS + DOT + OrderColumn.rating + COMMA +
                LHS + DOT + OrderColumn.feedback + COMMA +
                LHS + DOT + OrderColumn.secretKey + COMMA +
                LHS + DOT + TransactionColumn.transactionId + COMMA +
                LHS + DOT + TransactionColumn.paymentMode + COMMA +
                LHS + DOT + userName + COMMA +
                LHS + DOT + userMobile + COMMA +
                LHS + DOT + itemName + COMMA +
                LHS + DOT + itemPrice + COMMA +
                LHS + DOT + ItemColumn.isVeg + COMMA +
                LHS + DOT + quantity + COMMA +
                LHS + DOT + orderItemPrice + COMMA +
                RHS + DOT + OrderColumn.status + COMMA +
                RHS + DOT + OrderStatusColumn.updatedTime + FROM + LEFT_PARANTHESIS + SELECT +
                OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.date + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.price + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.deliveryPrice + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.deliveryLocation + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.cookingInfo + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.rating + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.feedback + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.secretKey + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.transactionId + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.paymentMode + COMMA +
                UserColumn.tableName + DOT + UserColumn.name + AS + userName + COMMA +
                UserColumn.tableName + DOT + UserColumn.mobile + AS + userMobile + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.name + RIGHT_PARANTHESIS + AS + itemName + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.price + RIGHT_PARANTHESIS + AS + itemPrice + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.isVeg + RIGHT_PARANTHESIS + AS + ItemColumn.isVeg + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderItemColumn.tableName + DOT + quantity + RIGHT_PARANTHESIS + AS + quantity + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderItemColumn.tableName + DOT + price + RIGHT_PARANTHESIS + AS + orderItemPrice + FROM + OrderColumn.tableName +
                INNER_JOIN + TransactionColumn.tableName + ON +
                OrderColumn.tableName + DOT + OrderColumn.id + EQUALS + TransactionColumn.tableName + DOT + TransactionColumn.orderId + AND +
                OrderColumn.tableName + DOT + OrderColumn.shopId + EQUAL_COLON + ShopColumn.id + AND +
                OrderColumn.tableName + DOT + OrderColumn.status + IN + LEFT_PARANTHESIS +
                SINGLE_QUOTE + OrderStatus.CANCELLED_BY_SELLER.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.CANCELLED_BY_USER.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.COMPLETED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.REFUND_INITIATED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.REFUND_COMPLETED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.DELIVERED.name() + SINGLE_QUOTE + RIGHT_PARANTHESIS +
                INNER_JOIN + OrderItemColumn.tableName + ON +
                OrderColumn.tableName + DOT + OrderColumn.id + EQUALS + OrderItemColumn.tableName + DOT + OrderItemColumn.orderId +
                INNER_JOIN + ItemColumn.tableName + ON +
                ItemColumn.tableName + DOT + ItemColumn.id + EQUALS + OrderItemColumn.tableName + DOT + itemId +
                INNER_JOIN + UserColumn.tableName + ON +
                UserColumn.tableName + DOT + UserColumn.id + EQUALS + OrderColumn.tableName + DOT + OrderColumn.userId + WHERE +
                OrderColumn.tableName + DOT + OrderColumn.id + LIKE + COLON + searchQuery + OR +
                UserColumn.tableName + DOT + UserColumn.name + LIKE + COLON + searchQuery + OR +
                UserColumn.tableName + DOT + UserColumn.mobile + LIKE + COLON + searchQuery +
                GROUP_BY + OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.transactionId +
                ORDER_BY + OrderColumn.tableName + DOT + OrderColumn.date + DESC +
                LIMIT + COLON + pageCount + OFFSET + COLON + pageNum + RIGHT_PARANTHESIS + AS + LHS + COMMA + LEFT_PARANTHESIS + SELECT +

                OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderStatusColumn.tableName + DOT + OrderStatusColumn.status + RIGHT_PARANTHESIS + AS + OrderStatusColumn.status + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderStatusColumn.tableName + DOT + OrderStatusColumn.updatedTime + RIGHT_PARANTHESIS + AS + OrderStatusColumn.updatedTime + FROM + OrderColumn.tableName +
                INNER_JOIN + OrderStatusColumn.tableName + ON +
                OrderStatusColumn.tableName + DOT + OrderStatusColumn.orderId + EQUALS + OrderColumn.tableName + DOT + OrderColumn.id + AND +
                OrderColumn.tableName + DOT + OrderColumn.shopId + EQUAL_COLON + ShopColumn.id + AND +
                OrderColumn.tableName + DOT + OrderColumn.status + IN + LEFT_PARANTHESIS +
                SINGLE_QUOTE + OrderStatus.CANCELLED_BY_SELLER.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.CANCELLED_BY_USER.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.COMPLETED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.REFUND_INITIATED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.REFUND_COMPLETED.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + OrderStatus.DELIVERED.name() + SINGLE_QUOTE + RIGHT_PARANTHESIS +
                INNER_JOIN + UserColumn.tableName + ON +
                UserColumn.tableName + DOT + UserColumn.id + EQUALS + OrderColumn.tableName + DOT + OrderColumn.userId + WHERE +
                OrderColumn.tableName + DOT + OrderColumn.id + LIKE + COLON + searchQuery + OR +
                UserColumn.tableName + DOT + UserColumn.name + LIKE + COLON + searchQuery + OR +
                UserColumn.tableName + DOT + UserColumn.mobile + LIKE + COLON + searchQuery +
                GROUP_BY + OrderColumn.tableName + DOT + OrderColumn.id +
                ORDER_BY + OrderColumn.tableName + DOT + OrderColumn.date + DESC +
                LIMIT + COLON + pageCount + OFFSET + COLON + pageNum + RIGHT_PARANTHESIS + AS + RHS + WHERE +
                LHS + DOT + OrderColumn.id + EQUALS + RHS + DOT + OrderColumn.id;
        public static final String getOrderById = SELECT +
                LHS + DOT + OrderColumn.id + COMMA +
                LHS + DOT + OrderColumn.date + COMMA +
                LHS + DOT + OrderColumn.price + COMMA +
                LHS + DOT + OrderColumn.deliveryPrice + COMMA +
                LHS + DOT + OrderColumn.deliveryLocation + COMMA +
                LHS + DOT + OrderColumn.cookingInfo + COMMA +
                LHS + DOT + OrderColumn.rating + COMMA +
                LHS + DOT + OrderColumn.feedback + COMMA +
                LHS + DOT + OrderColumn.secretKey + COMMA +
                LHS + DOT + TransactionColumn.transactionId + COMMA +
                LHS + DOT + TransactionColumn.paymentMode + COMMA +
                LHS + DOT + userName + COMMA +
                LHS + DOT + UserColumn.notifToken + COMMA +
                LHS + DOT + userMobile + COMMA +
                LHS + DOT + shopName + COMMA +
                LHS + DOT + ShopColumn.photoUrl + COMMA +
                LHS + DOT + shopMobile + COMMA +
                LHS + DOT + shopId + COMMA +
                LHS + DOT + itemName + COMMA +
                LHS + DOT + itemPrice + COMMA +
                LHS + DOT + ItemColumn.isVeg + COMMA +
                LHS + DOT + quantity + COMMA +
                LHS + DOT + orderItemPrice + COMMA +
                RHS + DOT + OrderColumn.status + COMMA +
                RHS + DOT + OrderStatusColumn.updatedTime + FROM + LEFT_PARANTHESIS + SELECT +
                OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.date + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.price + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.deliveryPrice + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.deliveryLocation + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.cookingInfo + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.rating + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.feedback + COMMA +
                OrderColumn.tableName + DOT + OrderColumn.secretKey + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.transactionId + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.paymentMode + COMMA +
                UserColumn.tableName + DOT + UserColumn.name + AS + userName + COMMA +
                UserColumn.tableName + DOT + UserColumn.notifToken + COMMA +
                UserColumn.tableName + DOT + UserColumn.mobile + AS + userMobile + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.id + AS + shopId + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.name + AS + shopName + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.photoUrl + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.mobile + AS + shopMobile + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.name + RIGHT_PARANTHESIS + AS + itemName + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.price + RIGHT_PARANTHESIS + AS + itemPrice + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + ItemColumn.tableName + DOT + ItemColumn.isVeg + RIGHT_PARANTHESIS + AS + ItemColumn.isVeg + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderItemColumn.tableName + DOT + quantity + RIGHT_PARANTHESIS + AS + quantity + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderItemColumn.tableName + DOT + price + RIGHT_PARANTHESIS + AS + orderItemPrice + FROM + OrderColumn.tableName +
                INNER_JOIN + TransactionColumn.tableName + ON +
                OrderColumn.tableName + DOT + OrderColumn.id + EQUALS + TransactionColumn.tableName + DOT + TransactionColumn.orderId + AND +
                OrderColumn.tableName + DOT + OrderColumn.id + EQUAL_COLON + OrderColumn.id +
                INNER_JOIN + OrderItemColumn.tableName + ON +
                OrderColumn.tableName + DOT + OrderColumn.id + EQUALS + OrderItemColumn.tableName + DOT + OrderItemColumn.orderId +
                INNER_JOIN + ItemColumn.tableName + ON +
                ItemColumn.tableName + DOT + ItemColumn.id + EQUALS + OrderItemColumn.tableName + DOT + itemId +
                INNER_JOIN + UserColumn.tableName + ON +
                UserColumn.tableName + DOT + UserColumn.id + EQUALS + OrderColumn.tableName + DOT + OrderColumn.userId +
                INNER_JOIN + ShopColumn.tableName + ON +
                ShopColumn.tableName + DOT + ShopColumn.id + EQUALS + OrderColumn.tableName + DOT + OrderColumn.shopId +
                GROUP_BY + OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                TransactionColumn.tableName + DOT + TransactionColumn.transactionId + RIGHT_PARANTHESIS + AS + LHS + COMMA + LEFT_PARANTHESIS + SELECT +

                OrderColumn.tableName + DOT + OrderColumn.id + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderStatusColumn.tableName + DOT + OrderStatusColumn.status + RIGHT_PARANTHESIS + AS + OrderStatusColumn.status + COMMA +
                GROUP_CONCAT + LEFT_PARANTHESIS + OrderStatusColumn.tableName + DOT + OrderStatusColumn.updatedTime + RIGHT_PARANTHESIS + AS + OrderStatusColumn.updatedTime + FROM + OrderColumn.tableName +
                INNER_JOIN + OrderStatusColumn.tableName + ON +
                OrderStatusColumn.tableName + DOT + OrderStatusColumn.orderId + EQUALS + OrderColumn.tableName + DOT + OrderColumn.id + AND +
                OrderColumn.tableName + DOT + OrderColumn.id + EQUAL_COLON + OrderColumn.id +
                GROUP_BY + OrderColumn.tableName + DOT + OrderColumn.id + RIGHT_PARANTHESIS + AS + RHS + WHERE +
                LHS + DOT + OrderColumn.id + EQUALS + RHS + DOT + OrderColumn.id;
        public static final String getOrderPriceById = SELECT +
                OrderColumn.id + COMMA +
                OrderColumn.price + FROM + OrderColumn.tableName + WHERE +
                OrderColumn.id + EQUAL_COLON + OrderColumn.id;

        public static final String updateOrderRating = UPDATE + OrderColumn.tableName + SET +
                OrderColumn.rating + EQUAL_COLON + OrderColumn.rating + COMMA +
                OrderColumn.feedback + EQUAL_COLON + OrderColumn.feedback + WHERE +
                OrderColumn.id + EQUAL_COLON + OrderColumn.id;

        public static String getInsertOrder(List<OrderItemModel> orderItemModelList) {
            StringBuilder insertOrderItem = new StringBuilder(INSERT_INTO + OrderItemColumn.tableName + LEFT_PARANTHESIS +
                    orderId + COMMA +
                    itemId + COMMA +
                    OrderItemColumn.quantity + COMMA +
                    OrderItemColumn.price + RIGHT_PARANTHESIS + VALUES);

            for (int i = 0; i < orderItemModelList.size(); i++) {
                insertOrderItem.append(LEFT_PARANTHESIS)
                        .append(COLON).append(orderId).append(i)
                        .append(COMMA_COLON).append(itemId).append(i)
                        .append(COMMA_COLON).append(quantity).append(i)
                        .append(COMMA_COLON).append(price).append(i)
                        .append(RIGHT_PARANTHESIS);
                if (i < orderItemModelList.size() - 1)
                    insertOrderItem.append(COMMA);
            }

            return insertOrderItem.toString();
        }

        public static String getOrderByStatus(List<OrderStatus> orderStatusList) {
            StringBuilder getOrderByStatus = new StringBuilder(SELECT +
                    OrderColumn.id + COMMA +
                    OrderColumn.date + FROM + OrderColumn.tableName + WHERE +
                    OrderColumn.status + IN + LEFT_PARANTHESIS);

            for (int i = 0; i < orderStatusList.size(); i++) {
                getOrderByStatus.append(SINGLE_QUOTE).append(orderStatusList.get(i).name()).append(SINGLE_QUOTE);
                if (i < orderStatusList.size() - 1)
                    getOrderByStatus.append(COMMA);
                else
                    getOrderByStatus.append(RIGHT_PARANTHESIS);
            }

            return getOrderByStatus.toString();
        }
    }

    public static final class UserInviteQuery {
        public static final String notDeleted = UserInviteColumn.isDelete + " = 0";

        public static final String inviteSeller = INSERT_INTO + UserInviteColumn.tableName + LEFT_PARANTHESIS +
                UserInviteColumn.mobile + COMMA +
                UserInviteColumn.role + COMMA +
                UserInviteColumn.shopId + RIGHT_PARANTHESIS + VALUES + LEFT_PARANTHESIS +
                COLON + UserInviteColumn.mobile +
                COMMA_COLON + UserInviteColumn.role +
                COMMA_COLON + UserInviteColumn.shopId + RIGHT_PARANTHESIS;

        public static final String verifyInvite = SELECT +
                UserInviteColumn.role + FROM + UserInviteColumn.tableName + WHERE +
                UserInviteColumn.mobile + EQUAL_COLON + UserInviteColumn.mobile + AND +
                UserInviteColumn.shopId + EQUAL_COLON + UserInviteColumn.shopId + AND +
                notDeleted + AND +
                TIMESTAMPDIFF + LEFT_PARANTHESIS + MINUTE + COMMA + UserInviteColumn.invitedAt + COMMA + CURRENT_TIMESTAMP + RIGHT_PARANTHESIS + LESS_THAN + 15 +
                ORDER_BY + UserInviteColumn.invitedAt + DESC + LIMIT + 1;

        public static final String deleteInvite = UPDATE + UserInviteColumn.tableName + SET +
                UserInviteColumn.isDelete + " = 1" + WHERE +
                UserInviteColumn.mobile + EQUAL_COLON + UserInviteColumn.mobile + AND +
                UserInviteColumn.role + EQUAL_COLON + UserInviteColumn.role + AND +
                UserInviteColumn.shopId + EQUAL_COLON + UserInviteColumn.shopId;
    }

    public static final class ShopQuery {
        public static final String notDeleted = ShopColumn.isDelete + " = 0";

        public static final String insertConfiguration = INSERT_INTO + ConfigurationColumn.tableName + LEFT_PARANTHESIS +
                ConfigurationColumn.shopId + COMMA +
                ConfigurationColumn.merchantId + COMMA +
                ConfigurationColumn.deliveryPrice + RIGHT_PARANTHESIS + VALUES + LEFT_PARANTHESIS +
                COLON + ConfigurationColumn.shopId +
                COMMA_COLON + ConfigurationColumn.merchantId +
                COMMA_COLON + ConfigurationColumn.deliveryPrice + RIGHT_PARANTHESIS;

        public static final String getShopByPlaceId = SELECT +
                ShopColumn.tableName + DOT + ShopColumn.id + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.name + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.mobile + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.photoUrl + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.coverUrls + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.openingTime + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.closingTime + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.merchantId + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.deliveryPrice + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.isDeliveryAvailable + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.isOrderTaken + COMMA +
                RatingColumn.tableName + DOT + RatingColumn.rating + COMMA +
                RatingColumn.tableName + DOT + RatingColumn.userCount + FROM + ShopColumn.tableName +
                INNER_JOIN + ConfigurationColumn.tableName + ON +
                ShopColumn.tableName + DOT + ShopColumn.placeId + EQUAL_COLON + ShopColumn.placeId + AND +
                notDeleted + AND +
                ShopColumn.tableName + DOT + ShopColumn.id + EQUALS + ConfigurationColumn.tableName + DOT + ConfigurationColumn.shopId +
                INNER_JOIN + RatingColumn.tableName + ON +
                ShopColumn.tableName + DOT + ShopColumn.id + EQUALS + RatingColumn.tableName + DOT + RatingColumn.shopId;

        public static final String getShopById = SELECT +
                ShopColumn.tableName + DOT + ShopColumn.id + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.name + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.mobile + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.photoUrl + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.coverUrls + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.openingTime + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.closingTime + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.merchantId + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.deliveryPrice + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.isDeliveryAvailable + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.isOrderTaken + COMMA +
                RatingColumn.tableName + DOT + RatingColumn.rating + COMMA +
                RatingColumn.tableName + DOT + RatingColumn.userCount + FROM + ShopColumn.tableName +
                INNER_JOIN + ConfigurationColumn.tableName + ON +
                ShopColumn.tableName + DOT + ShopColumn.id + EQUAL_COLON + ShopColumn.id + AND +
                notDeleted + AND +
                ShopColumn.tableName + DOT + ShopColumn.id + EQUALS + ConfigurationColumn.tableName + DOT + ConfigurationColumn.shopId +
                INNER_JOIN + RatingColumn.tableName + ON +
                ShopColumn.tableName + DOT + ShopColumn.id + EQUALS + RatingColumn.tableName + DOT + RatingColumn.shopId;

        public static final String updateShop = UPDATE + ShopColumn.tableName + COMMA + ConfigurationColumn.tableName + SET +
                ShopColumn.tableName + DOT + ShopColumn.name + EQUAL_COLON + ShopColumn.name + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.photoUrl + EQUAL_COLON + ShopColumn.photoUrl + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.coverUrls + EQUAL_COLON + ShopColumn.coverUrls + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.mobile + EQUAL_COLON + ShopColumn.mobile + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.openingTime + EQUAL_COLON + ShopColumn.openingTime + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.closingTime + EQUAL_COLON + ShopColumn.closingTime + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.merchantId + EQUAL_COLON + ConfigurationColumn.merchantId + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.deliveryPrice + EQUAL_COLON + ConfigurationColumn.deliveryPrice + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.isDeliveryAvailable + EQUAL_COLON + ConfigurationColumn.isDeliveryAvailable + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.isOrderTaken + EQUAL_COLON + ConfigurationColumn.isOrderTaken + WHERE +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.shopId + EQUALS + ShopColumn.tableName + DOT + ShopColumn.id + AND +
                ShopColumn.tableName + DOT + ShopColumn.id + EQUAL_COLON + ShopColumn.id;

        public static final String deleteShop = UPDATE + ShopColumn.tableName + SET +
                ShopColumn.isDelete + " = 1" + WHERE +
                ShopColumn.id + EQUAL_COLON + ShopColumn.id;
    }

    public static final class TransactionQuery {
        public static final String insertTransaction = INSERT_INTO + TransactionColumn.tableName + LEFT_PARANTHESIS +
                TransactionColumn.transactionId + COMMA +
                TransactionColumn.orderId + COMMA +
                TransactionColumn.bankTransactionId + COMMA +
                TransactionColumn.currency + COMMA +
                TransactionColumn.responseCode + COMMA +
                TransactionColumn.responseMessage + COMMA +
                TransactionColumn.gatewayName + COMMA +
                TransactionColumn.bankName + COMMA +
                TransactionColumn.paymentMode + COMMA +
                TransactionColumn.checksumHash + RIGHT_PARANTHESIS + VALUES + LEFT_PARANTHESIS +
                COLON + TransactionColumn.transactionId +
                COMMA_COLON + TransactionColumn.orderId +
                COMMA_COLON + TransactionColumn.bankTransactionId +
                COMMA_COLON + TransactionColumn.currency +
                COMMA_COLON + TransactionColumn.responseCode +
                COMMA_COLON + TransactionColumn.responseMessage +
                COMMA_COLON + TransactionColumn.gatewayName +
                COMMA_COLON + TransactionColumn.bankName +
                COMMA_COLON + TransactionColumn.paymentMode +
                COMMA_COLON + TransactionColumn.checksumHash + RIGHT_PARANTHESIS;

        public static final String updateTransaction = UPDATE + TransactionColumn.tableName + SET +
                TransactionColumn.responseCode + EQUAL_COLON + TransactionColumn.responseCode + COMMA +
                TransactionColumn.responseMessage + EQUAL_COLON + TransactionColumn.responseMessage + COMMA +
                TransactionColumn.date + EQUALS + CURRENT_TIMESTAMP + WHERE +
                TransactionColumn.transactionId + EQUAL_COLON + TransactionColumn.transactionId;
    }

    public static final class UserPlaceQuery {
        public static final String insertUserPlace = INSERT_INTO + UserPlaceColumn.tableName + LEFT_PARANTHESIS +
                UserPlaceColumn.userId + COMMA +
                UserPlaceColumn.placeId + RIGHT_PARANTHESIS + VALUES + LEFT_PARANTHESIS +
                COLON + UserPlaceColumn.userId +
                COMMA_COLON + UserPlaceColumn.placeId + RIGHT_PARANTHESIS;

        public static final String updatePlaceById = UPDATE + UserPlaceColumn.tableName + SET +
                UserPlaceColumn.placeId + EQUAL_COLON + UserPlaceColumn.placeId + WHERE +
                UserPlaceColumn.userId + EQUAL_COLON + UserPlaceColumn.userId;
    }

    public static final class UserQuery {
        public static final String notDeleted = UserColumn.isDelete + " = 0";

        public static final String getUserIdByMobile = SELECT +
                UserColumn.id + FROM + UserColumn.tableName + WHERE +
                UserColumn.mobile + EQUAL_COLON + UserColumn.mobile;

        public static final String getSellerByShopId = SELECT +
                UserColumn.tableName + DOT + UserColumn.id + COMMA +
                UserColumn.tableName + DOT + UserColumn.name + COMMA +
                UserColumn.tableName + DOT + UserColumn.email + COMMA +
                UserColumn.tableName + DOT + UserColumn.mobile + COMMA +
                UserColumn.tableName + DOT + UserColumn.role + FROM + UserColumn.tableName +
                INNER_JOIN + UserShopColumn.tableName + ON +
                UserColumn.tableName + DOT + UserColumn.id + EQUALS + UserShopColumn.tableName + DOT + UserShopColumn.userId + AND +
                UserColumn.tableName + DOT + notDeleted + AND +
                UserShopColumn.tableName + DOT + UserShopColumn.shopId + EQUAL_COLON + UserShopColumn.shopId + AND +
                UserColumn.tableName + DOT + UserColumn.role + IN + LEFT_PARANTHESIS + SINGLE_QUOTE + SELLER.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + DELIVERY.name() + SINGLE_QUOTE + RIGHT_PARANTHESIS +
                UNION + SELECT +
                "0" + COMMA +
                SINGLE_QUOTE + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + SINGLE_QUOTE + COMMA +
                UserInviteColumn.mobile + COMMA +
                UserInviteColumn.role + FROM + UserInviteColumn.tableName + WHERE +
                UserInviteColumn.shopId + EQUAL_COLON + UserShopColumn.shopId + AND +
                UserInviteQuery.notDeleted + AND +
                TIMESTAMPDIFF + LEFT_PARANTHESIS + MINUTE + COMMA + UserInviteColumn.invitedAt + COMMA + CURRENT_TIMESTAMP + RIGHT_PARANTHESIS + LESS_THAN + 15 + AND +
                UserInviteColumn.role + IN + LEFT_PARANTHESIS + SINGLE_QUOTE + SELLER.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + DELIVERY.name() + SINGLE_QUOTE + COMMA +
                SINGLE_QUOTE + SHOP_OWNER.name() + SINGLE_QUOTE + RIGHT_PARANTHESIS;

        public static final String updateUser = UPDATE + UserColumn.tableName + SET +
                UserColumn.name + EQUAL_COLON + UserColumn.name + COMMA +
                UserColumn.oauthId + EQUAL_COLON + UserColumn.oauthId + COMMA +
                UserColumn.email + EQUAL_COLON + UserColumn.email + COMMA +
                UserColumn.mobile + EQUAL_COLON + UserColumn.mobile + WHERE +
                UserColumn.id + EQUAL_COLON + UserColumn.id;

        public static final String updateUserNotificationToken = UPDATE + UserColumn.tableName + SET +
                UserColumn.notifToken + EQUAL_COLON + UserColumn.notifToken + WHERE +
                UserColumn.id + EQUAL_COLON + UserColumn.id;

        public static final String updateRole = UPDATE + UserColumn.tableName + SET +
                UserColumn.role + EQUAL_COLON + UserColumn.role + WHERE +
                UserColumn.id + EQUAL_COLON + UserColumn.id;

        public static final String loginUserByMobileOauth = SELECT +
                UserColumn.id + COMMA +
                UserColumn.name + COMMA +
                UserColumn.email + COMMA +
                UserColumn.role + FROM + UserColumn.tableName + WHERE +
                UserColumn.mobile + EQUAL_COLON + UserColumn.mobile + AND +
                UserColumn.oauthId + EQUAL_COLON + UserColumn.oauthId + AND +
                notDeleted;

        public static final String customerLogin = SELECT +
                UserColumn.tableName + DOT + UserColumn.id + COMMA +
                UserColumn.tableName + DOT + UserColumn.name + COMMA +
                UserColumn.tableName + DOT + UserColumn.email + COMMA +
                UserColumn.tableName + DOT + UserColumn.role + COMMA +
                PlaceColumn.tableName + DOT + PlaceColumn.id + AS + UserPlaceColumn.placeId + COMMA +
                PlaceColumn.tableName + DOT + PlaceColumn.name + AS + Column.placeName + COMMA +
                PlaceColumn.tableName + DOT + PlaceColumn.iconUrl + COMMA +
                PlaceColumn.tableName + DOT + PlaceColumn.address + AS + Column.placeAddress + FROM + UserColumn.tableName +
                INNER_JOIN + UserPlaceColumn.tableName + ON +
                UserColumn.tableName + DOT + UserColumn.id + EQUALS + UserPlaceColumn.tableName + DOT + UserPlaceColumn.userId + AND +
                UserColumn.tableName + DOT + notDeleted +
                INNER_JOIN + PlaceColumn.tableName + ON +
                PlaceColumn.tableName + DOT + PlaceColumn.id + EQUALS + UserPlaceColumn.tableName + DOT + UserPlaceColumn.placeId + AND +
                PlaceColumn.tableName + DOT + PlaceQuery.notDeleted + WHERE +
                UserColumn.mobile + EQUAL_COLON + UserColumn.mobile + AND +
                UserColumn.oauthId + EQUAL_COLON + UserColumn.oauthId;

        public static final String sellerLogin = SELECT +
                UserColumn.tableName + DOT + UserColumn.id + COMMA +
                UserColumn.tableName + DOT + UserColumn.name + COMMA +
                UserColumn.tableName + DOT + UserColumn.email + COMMA +
                UserColumn.tableName + DOT + UserColumn.role + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.id + AS + UserShopColumn.shopId + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.name + AS + shopName + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.mobile + AS + Column.shopMobile + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.photoUrl + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.coverUrls + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.openingTime + COMMA +
                ShopColumn.tableName + DOT + ShopColumn.closingTime + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.merchantId + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.deliveryPrice + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.isDeliveryAvailable + COMMA +
                ConfigurationColumn.tableName + DOT + ConfigurationColumn.isOrderTaken + COMMA +
                RatingColumn.tableName + DOT + RatingColumn.rating + COMMA +
                RatingColumn.tableName + DOT + RatingColumn.userCount + FROM + UserColumn.tableName +
                INNER_JOIN + UserShopColumn.tableName + ON +
                UserColumn.tableName + DOT + UserColumn.id + EQUALS + UserShopColumn.tableName + DOT + UserShopColumn.userId + AND +
                UserColumn.tableName + DOT + UserColumn.role + NOT_EQUALS + SINGLE_QUOTE + Enums.UserRole.CUSTOMER.name() + SINGLE_QUOTE + AND +
                UserColumn.tableName + DOT + notDeleted +
                INNER_JOIN + ShopColumn.tableName + ON +
                ShopColumn.tableName + DOT + ShopColumn.id + EQUALS + UserShopColumn.tableName + DOT + UserShopColumn.shopId + AND +
                ShopColumn.tableName + DOT + ShopQuery.notDeleted +
                INNER_JOIN + ConfigurationColumn.tableName + ON +
                ShopColumn.tableName + DOT + ShopColumn.id + EQUALS + ConfigurationColumn.tableName + DOT + ConfigurationColumn.shopId +
                INNER_JOIN + RatingColumn.tableName + ON +
                ShopColumn.tableName + DOT + ShopColumn.id + EQUALS + RatingColumn.tableName + DOT + RatingColumn.shopId + WHERE +
                UserColumn.tableName + DOT + UserColumn.mobile + EQUAL_COLON + UserColumn.mobile + AND +
                UserColumn.tableName + DOT + UserColumn.oauthId + EQUAL_COLON + UserColumn.oauthId + AND +
                UserColumn.tableName + DOT + UserColumn.role + NOT_EQUALS + SINGLE_QUOTE + Enums.UserRole.CUSTOMER.name() + SINGLE_QUOTE;

        public static final String validateUser = SELECT +
                UserColumn.id + FROM + UserColumn.tableName + WHERE +
                UserColumn.id + EQUAL_COLON + UserColumn.id + AND +
                UserColumn.oauthId + EQUAL_COLON + UserColumn.oauthId + AND +
                UserColumn.role + EQUAL_COLON + UserColumn.role + AND +
                notDeleted;
    }

    public static final class UserShopQuery {
        public static final String insertUserShop = INSERT_INTO + UserShopColumn.tableName + LEFT_PARANTHESIS +
                UserShopColumn.userId + COMMA +
                UserShopColumn.shopId + RIGHT_PARANTHESIS + VALUES + LEFT_PARANTHESIS +
                COLON + UserShopColumn.userId +
                COMMA_COLON + UserShopColumn.shopId + RIGHT_PARANTHESIS;

        public static final String updateShopById = UPDATE + UserShopColumn.tableName + SET +
                UserShopColumn.shopId + EQUAL_COLON + UserShopColumn.shopId + WHERE +
                UserShopColumn.userId + EQUAL_COLON + UserShopColumn.userId;
    }
}
