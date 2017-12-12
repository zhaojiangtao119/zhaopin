package com.labelwall.mall.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.entity.School;

import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
public interface ISchoolService {

    ResponseObject<List<School>> getSchoolByProvinceId(Integer provinceId);

}
