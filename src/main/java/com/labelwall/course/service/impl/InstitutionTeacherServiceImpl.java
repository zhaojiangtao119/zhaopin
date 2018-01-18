package com.labelwall.course.service.impl;

import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.course.dao.InstitutionTeacherMapper;
import com.labelwall.course.entity.InstitutionTeacher;
import com.labelwall.course.service.IInstitutionTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-28.
 */
@Service("institutionTeacherService")
public class InstitutionTeacherServiceImpl implements IInstitutionTeacherService {
    @Autowired
    private InstitutionTeacherMapper institutionTeacherMapper;

    @Override
    public ResponseObject<List<InstitutionTeacher>> getTeacherByInstitutionId(Integer institutionId) {
        if(institutionId == null){
            return  ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),ResponseStatus.ERROR_PARAM.getValue());
        }
        List<InstitutionTeacher> institutionTeacherList =
                institutionTeacherMapper.getTeacherByInstitutionId(institutionId);
        return ResponseObject.successStautsData(institutionTeacherList);
    }

    @Override
    public InstitutionTeacher selectByPrimaryKey(Integer teacherId) {
        return institutionTeacherMapper.selectByPrimaryKey(teacherId);
    }

    @Override
    public List<InstitutionTeacher> selectByUserIds(List<Integer> userIdList) {
        return institutionTeacherMapper.selectByIds(userIdList);
    }
}
