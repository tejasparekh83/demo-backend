package com.food.ordering.zinger.service.impl;

import com.food.ordering.zinger.constant.ErrorLog;
import com.food.ordering.zinger.dao.interfaces.NotifyDao;
import com.food.ordering.zinger.dao.interfaces.OrderDao;
import com.food.ordering.zinger.exception.GenericException;
import com.food.ordering.zinger.model.OrderItemListModel;
import com.food.ordering.zinger.model.OrderModel;
import com.food.ordering.zinger.model.Response;
import com.food.ordering.zinger.model.TransactionTokenModel;
import com.food.ordering.zinger.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    NotifyDao notifyDao;


    @Override
    public Response<TransactionTokenModel> insertOrder(OrderItemListModel orderItemListModel) {
        Response<TransactionTokenModel> response = new Response<>();
        try {
            response = orderDao.insertOrder(orderItemListModel);
        } catch (GenericException e) {
            response = e.getResponse();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response<String> placeOrder(Integer orderId) {
        Response<String> response = orderDao.placeOrder(orderId);
        if (response.getCode().equals(ErrorLog.CodeSuccess)) {
            notifyDao.notifyOrderStatusToSeller(orderDao.getOrderById(orderId));
        }
        return response;
    }

    @Override
    public Response<List<OrderItemListModel>> getOrderByUserId(Integer userId, Integer pageNum, Integer pageCount) {
        Response<List<OrderItemListModel>> response = orderDao.getOrderByUserId(userId, pageNum, pageCount);
        return response;
    }

    @Override
    public Response<List<OrderItemListModel>> getOrderBySearchQuery(Integer shopId, String searchItem, Integer pageNum, Integer pageCount) {
        Response<List<OrderItemListModel>> response = orderDao.getOrderBySearchQuery(shopId, searchItem, pageNum, pageCount);
        return response;
    }

    @Override
    public Response<List<OrderItemListModel>> getOrderByShopIdPagination(Integer shopId, Integer pageNum, Integer pageCount) {
        Response<List<OrderItemListModel>> response = orderDao.getOrderByShopIdPagination(shopId, pageNum, pageCount);
        return orderDao.getOrderByShopIdPagination(shopId, pageNum, pageCount);
    }

    @Override
    public Response<List<OrderItemListModel>> getOrderByShopId(Integer shopId) {
        Response<List<OrderItemListModel>> response = orderDao.getOrderByShopId(shopId);
        return orderDao.getOrderByShopId(shopId);
    }

    @Override
    public Response<OrderItemListModel> getOrderById(Integer id) {
        Response<OrderItemListModel> response = orderDao.getOrderById(id);
        try {
            if (response.getCode().equals(ErrorLog.CodeSuccess))
                response.getData().getTransactionModel().getOrderModel().getUserModel().setNotificationToken(null);
        } catch (Exception e) {
        }
        return response;
    }

    @Override
    public Response<String> updateOrderRating(OrderModel orderModel) {
        Response<String> response = orderDao.updateOrderRating(orderModel);
        return response;
    }

    @Override
    public Response<String> updateOrderStatus(OrderModel orderModel) {
        Response<String> response = orderDao.updateOrderStatus(orderModel);
        if (response.getCode().equals(ErrorLog.CodeSuccess)) {
            switch (orderModel.getOrderStatus()) {
                case CANCELLED_BY_USER:
                    notifyDao.notifyOrderStatusToSeller(orderDao.getOrderById(orderModel.getId()));
                    break;
                default:
                    notifyDao.notifyOrderStatus(orderDao.getOrderById(orderModel.getId()));
            }
        }
        return response;
    }
}
