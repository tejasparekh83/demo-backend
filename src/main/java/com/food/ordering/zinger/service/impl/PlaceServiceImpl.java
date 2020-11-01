package com.food.ordering.zinger.service.impl;

import com.food.ordering.zinger.dao.interfaces.PlaceDao;
import com.food.ordering.zinger.model.PlaceModel;
import com.food.ordering.zinger.model.Response;
import com.food.ordering.zinger.service.interfaces.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    PlaceDao placeDao;


    @Override
    public Response<String> insertPlace(PlaceModel placeModel) {
        Response<String> response = placeDao.insertPlace(placeModel);
        return response;
    }

    @Override
    public Response<List<PlaceModel>> getAllPlaces() {
        Response<List<PlaceModel>> response = placeDao.getAllPlaces();
        return response;
    }
}
