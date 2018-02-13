package com.labelwall.mall.service;

import com.labelwall.mall.entity.ServiceType;

import java.util.List;

public interface IShopServiceService {

    List<ServiceType> getServiceListByType(int type);

    ServiceType getServiceById(Integer serviceId);

}
