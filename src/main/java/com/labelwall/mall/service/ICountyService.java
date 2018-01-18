package com.labelwall.mall.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.entity.County;

import java.util.List;

/**
 * Created by Administrator on 2017-12-19.
 */
public interface ICountyService {
    /**
     * 获取区县列表
     *
     * @param provinceId
     * @param cityId
     * @return
     */
    ResponseObject<List<County>> getCountyListByProvinceIdCityId(Integer provinceId, Integer cityId);

    /**
     * 查询指定的countyId
     *
     * @param cityId
     * @param name
     * @return
     */
    Integer findIdByCountyName(Integer cityId, String name);
}
