package com.labelwall.mall.controller;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.entity.City;
import com.labelwall.mall.entity.Province;
import com.labelwall.mall.entity.School;
import com.labelwall.mall.service.ICityService;
import com.labelwall.mall.service.IProvinceService;
import com.labelwall.mall.service.ISchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
@RestController
@RequestMapping("/location/")
public class LocationController {

    @Autowired
    private IProvinceService provinceService;
    @Autowired
    private ICityService cityService;
    @Autowired
    private ISchoolService schoolService;

    /**
     * 获取省份
     *
     * @return
     */
    @RequestMapping(value = "get_province_list", method = RequestMethod.GET)
    public ResponseObject<List<Province>> getProvinceList() {
        ResponseObject<List<Province>> response = provinceService.getProvinceList();
        return response;
    }

    /**
     * 通过省份id（默认是1）获取城市
     *
     * @param provinceId
     * @return
     */
    @RequestMapping(value = "get_city_list_province_id", method = RequestMethod.GET)
    public ResponseObject<List<City>> getCityListByProvinceId(@RequestParam(value = "provinceId", defaultValue = "1") Integer provinceId) {
        ResponseObject<List<City>> response = cityService.getCityListByProvinceId(provinceId);
        return response;
    }

    /**
     * 通过省份id获取学校列表
     *
     * @param provinceId
     * @return
     */
    @RequestMapping(value = "get_school_list_province_id", method = RequestMethod.GET)
    public ResponseObject<List<School>> getSchoolListByProvinceId(@RequestParam(value = "provinceId", defaultValue = "1") Integer provinceId) {
        ResponseObject<List<School>> response = schoolService.getSchoolByProvinceId(provinceId);
        return response;
    }
}
