package com.labelwall.course.service.impl;

import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.course.dao.InstitutionCoursewareMapper;
import com.labelwall.course.entity.InstitutionCourseware;
import com.labelwall.course.service.IInstitutionCoursewareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-27.
 */
@Service("institutionCoursewareService")
public class InstitutionCoursewareServiceImpl implements IInstitutionCoursewareService {

    @Autowired
    private InstitutionCoursewareMapper institutionCoursewareMapper;

    @Override
    public ResponseObject<List<InstitutionCourseware>> getCoursewareByInstitutionId(Integer institutionId) {
        if(institutionId == null){
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),ResponseStatus.ERROR_PARAM.getValue());
        }
        List<InstitutionCourseware> institutionCoursewareList =
                institutionCoursewareMapper.getCoursewareByInstitutionId(institutionId);
        return ResponseObject.successStautsData(institutionCoursewareList);
    }
}
