package com.labelwall.course.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.course.entity.InstitutionTeacher;

import java.util.List;

/**
 * Created by Administrator on 2017-12-28.
 */
public interface IInstitutionTeacherService {
    /**
     * 根据机构id获取教师信息
     *
     * @param institutionId
     * @return
     */
    ResponseObject<List<InstitutionTeacher>> getTeacherByInstitutionId(Integer institutionId);

    /**
     * 通过主键获取教师信息
     *
     * @param teacherId
     * @return
     */
    InstitutionTeacher selectByPrimaryKey(Integer teacherId);

    /**
     * 获取用户关注的教师信息
     *
     * @param userIdList
     * @return
     */
    List<InstitutionTeacher> selectByUserIds(List<Integer> userIdList);
}
