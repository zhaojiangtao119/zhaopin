package com.labelwall.mall.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.entity.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
public interface ICityService {

    ResponseObject<List<City>> getCityListByProvinceId(Integer provinceId);

    /**
     * 获取指定城市id
     *
     * @param provinceId
     * @param name
     * @return
     */
    Integer findIdByCityName(Integer provinceId, String name);
}
