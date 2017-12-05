package com.labelwall.mall.service.impl;

import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.dao.CityMapper;
import com.labelwall.mall.entity.City;
import com.labelwall.mall.service.ICityService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
@Service("cityService")
public class CityServiceImpl implements ICityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public ResponseObject<List<City>> getCityListByProvinceId(Integer provinceId) {
        if(provinceId == null){
            provinceId = 1;
        }
        List<City> cityList = cityMapper.getCityListByProvinceId(provinceId);
        if(CollectionUtils.isEmpty(cityList)){
            return ResponseObject.failStatusMessage("获取失败");
        }
        return ResponseObject.successStautsData(cityList);
    }
}
