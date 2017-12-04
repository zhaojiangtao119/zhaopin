package com.labelwall.mall.service;

import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.entity.City;

import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
public interface ICityService {

    ResponseObject<List<City>> getCityListByProvinceId(Integer provinceId);

}
