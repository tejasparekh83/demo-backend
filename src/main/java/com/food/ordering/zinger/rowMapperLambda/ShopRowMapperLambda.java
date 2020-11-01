package com.food.ordering.zinger.rowMapperLambda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.zinger.model.ConfigurationModel;
import com.food.ordering.zinger.model.RatingModel;
import com.food.ordering.zinger.model.ShopConfigurationModel;
import com.food.ordering.zinger.model.ShopModel;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

import static com.food.ordering.zinger.constant.Column.ConfigurationColumn.*;
import static com.food.ordering.zinger.constant.Column.RatingColumn.rating;
import static com.food.ordering.zinger.constant.Column.RatingColumn.userCount;
import static com.food.ordering.zinger.constant.Column.ShopColumn.*;

public class ShopRowMapperLambda {
    public static final RowMapper<ShopConfigurationModel> shopRowMapperLambda = (rs, rownum) -> {

        ShopConfigurationModel shopConfigurationModel = new ShopConfigurationModel();

        ShopModel shopModel = new ShopModel();
        shopModel.setId(rs.getInt(id));
        shopModel.setName(rs.getString(name));
        shopModel.setPhotoUrl(rs.getString(photoUrl));

        try {
            shopModel.setCoverUrls(new ObjectMapper().readValue(rs.getString(coverUrls), List.class));
        } catch (JsonProcessingException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            shopModel.setCoverUrls(new ArrayList<>());
        }

        shopModel.setPlaceModel(null);
        shopModel.setMobile(rs.getString(mobile));
        shopModel.setOpeningTime(rs.getTime(openingTime));
        shopModel.setClosingTime(rs.getTime(closingTime));


        ConfigurationModel configurationModel = new ConfigurationModel();
        configurationModel.setShopModel(null);
        configurationModel.setDeliveryPrice(rs.getDouble(deliveryPrice));
        configurationModel.setIsDeliveryAvailable(rs.getInt(isDeliveryAvailable));
        configurationModel.setIsOrderTaken(rs.getInt(isOrderTaken));
        configurationModel.setMerchantId(rs.getString(merchantId));


        RatingModel ratingModel = new RatingModel();
        ratingModel.setShopModel(null);
        ratingModel.setRating(rs.getDouble(rating));
        ratingModel.setUserCount(rs.getInt(userCount));

        shopConfigurationModel.setShopModel(shopModel);
        shopConfigurationModel.setRatingModel(ratingModel);
        shopConfigurationModel.setConfigurationModel(configurationModel);

        return shopConfigurationModel;
    };
}
