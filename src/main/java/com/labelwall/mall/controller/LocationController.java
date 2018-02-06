package com.labelwall.mall.controller;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.entity.City;
import com.labelwall.mall.entity.County;
import com.labelwall.mall.entity.Province;
import com.labelwall.mall.entity.School;
import com.labelwall.mall.service.ICityService;
import com.labelwall.mall.service.ICountyService;
import com.labelwall.mall.service.IProvinceService;
import com.labelwall.mall.service.ISchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    private ICountyService countyService;
    @Autowired
    private ISchoolService schoolService;

    /**
     * 获取省份
     *
     *
     * @return
     */
    @RequestMapping(value = "get_province_list", method = RequestMethod.GET)
    public ResponseObject<List<Province>> getProvinceList(HttpServletResponse response) {
        //response.setHeader("Access-Control-Allow-Origin","*");
        ResponseObject<List<Province>> responseObject = provinceService.getProvinceList();
        return responseObject;
    }

    /**
     * 通过省份id（默认是1）获取城市
     *
     * @param provinceId
     * @return
     */
    @RequestMapping(value = "get_city_list_province_id", method = RequestMethod.GET)
    public ResponseObject<List<City>>
    getCityListByProvinceId(@RequestParam(value = "provinceId", defaultValue = "1") Integer provinceId) {
        ResponseObject<List<City>> response = cityService.getCityListByProvinceId(provinceId);
        return response;
    }

    /**
     * 获取区县，通过provinceId,cityId
     *
     * @param provinceId
     * @param cityId
     * @return
     */
    @RequestMapping(value = "get_county_list", method = RequestMethod.GET)
    public ResponseObject<List<County>>
    getCountyByProvinceIdCityId(@RequestParam(value = "provinceId", defaultValue = "1") Integer provinceId,
                                @RequestParam(value = "cityId", required = false) Integer cityId) {
        ResponseObject<List<County>> response = countyService.getCountyListByProvinceIdCityId(provinceId, cityId);
        return response;
    }

    /**
     * 通过省份id获取学校列表
     *
     * @param provinceId
     * @return
     */
    @RequestMapping(value = "get_school_list_province_id", method = RequestMethod.GET)
    public ResponseObject<List<School>>
    getSchoolListByProvinceId(@RequestParam(value = "provinceId", defaultValue = "1") Integer provinceId) {
        ResponseObject<List<School>> response = schoolService.getSchoolByProvinceId(provinceId);
        return response;
    }
}
