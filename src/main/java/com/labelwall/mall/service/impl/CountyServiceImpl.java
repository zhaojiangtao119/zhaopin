package com.labelwall.mall.service.impl;

import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dao.CountyMapper;
import com.labelwall.mall.entity.County;
import com.labelwall.mall.service.ICountyService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-19.
 */
@Service("countyService")
public class CountyServiceImpl implements ICountyService {

    @Autowired
    private CountyMapper countyMapper;


    @Override
    public ResponseObject<List<County>> getCountyListByProvinceIdCityId(Integer provinceId, Integer cityId) {
        List<County> countyList = countyMapper.getCountyListByProvinceIdCityId(provinceId, cityId);
        if (CollectionUtils.isEmpty(countyList)) {
            return ResponseObject.fail(ResponseStatus.FAIL.getCode(),
                    ResponseStatus.FAIL.getValue());
        }
        return ResponseObject.successStautsData(countyList);
    }

    @Override
    public Integer findIdByCountyName(Integer cityId, String name) {
        if (cityId != null && StringUtils.isNotBlank(name)) {
            Integer countyId = countyMapper.findByCountyName(cityId, name);
            return countyId;
        }
        return null;
    }
}
