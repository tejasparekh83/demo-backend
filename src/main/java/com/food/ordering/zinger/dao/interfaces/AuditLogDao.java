package com.food.ordering.zinger.dao.interfaces;

import com.food.ordering.zinger.model.Response;
import com.food.ordering.zinger.model.logger.ApplicationLogModel;

public interface AuditLogDao {
    Response<String> insertLog(ApplicationLogModel applicationLogModel);
}
