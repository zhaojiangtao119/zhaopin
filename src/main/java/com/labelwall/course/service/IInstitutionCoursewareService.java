package com.labelwall.course.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.course.entity.InstitutionCourseware;

import java.util.List;

/**
 * Created by Administrator on 2017-12-27.
 */
public interface IInstitutionCoursewareService {

    /**
     * 根据机构id获取课件
     *
     * @param institutionId
     * @return
     */
    ResponseObject<List<InstitutionCourseware>> getCoursewareByInstitutionId(Integer institutionId);
}
