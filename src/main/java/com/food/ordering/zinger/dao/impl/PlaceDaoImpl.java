package com.food.ordering.zinger.dao.impl;

import com.food.ordering.zinger.constant.Column.PlaceColumn;
import com.food.ordering.zinger.constant.Enums.Priority;
import com.food.ordering.zinger.constant.ErrorLog;
import com.food.ordering.zinger.constant.Query.PlaceQuery;
import com.food.ordering.zinger.dao.interfaces.PlaceDao;
import com.food.ordering.zinger.model.PlaceModel;
import com.food.ordering.zinger.model.Response;
import com.food.ordering.zinger.rowMapperLambda.PlaceRowMapperLambda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.food.ordering.zinger.constant.ErrorLog.*;

/**
 * PlaceDao is responsible for CRUD operations in
 * Place table in MySQL.
 *
 * @implNote Request Header (RH) parameter is sent in all endpoints
 * to avoid unauthorized access to our service.
 * @implNote All endpoint services are audited for both success and error responses
 * using "AuditLogDao".
 * <p>
 * Endpoints starting with "/place" invoked here.
 */
@Repository
public class PlaceDaoImpl implements PlaceDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Inserts the place details.
     * Authorized by SUPER_ADMIN only.
     *
     * @param placeModel PlaceModel
     * @return success response if the insert is successful.
     */
    @Override
    public Response<String> insertPlace(PlaceModel placeModel) {
        Response<String> response = new Response<>();

        try {
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue(PlaceColumn.name, placeModel.getName())
                    .addValue(PlaceColumn.address, placeModel.getAddress())
                    .addValue(PlaceColumn.iconUrl, placeModel.getIconUrl());

            int responseValue = namedParameterJdbcTemplate.update(PlaceQuery.insertPlace, parameters);
            if (responseValue > 0) {
                response.setCode(ErrorLog.CodeSuccess);
                response.setMessage(ErrorLog.Success);
                response.setData(ErrorLog.Success);
                response.prioritySet(Priority.LOW);
            } else {
                response.setCode(CDNU1100);
                response.setMessage(PlaceDetailNotUpdated);
            }
        } catch (Exception e) {
            response.setCode(CE1101);
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return response;
    }

    /**
     * Gets all places
     *
     * @return the list of place details
     * @implNote Used in the registration process to choose the
     * place the user belongs.
     */
    @Override
    public Response<List<PlaceModel>> getAllPlaces() {
        Response<List<PlaceModel>> response = new Response<>();
        List<PlaceModel> list = null;

        try {
            list = namedParameterJdbcTemplate.query(PlaceQuery.getAllPlaces, PlaceRowMapperLambda.placeRowMapperLambda);
        } catch (Exception e) {
            response.setCode(CDNA1102);
            response.setMessage(PlaceDetailNotAvailable);
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            if (list != null) {
                response.setCode(list.isEmpty() ? ErrorLog.CodeEmpty : ErrorLog.CodeSuccess);
                response.setMessage(ErrorLog.Success);
                response.setData(list);
                response.prioritySet(Priority.LOW);
            }
        }

        return response;
    }
}
