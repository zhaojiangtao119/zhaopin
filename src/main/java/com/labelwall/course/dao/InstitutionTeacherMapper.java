package com.labelwall.course.dao;


import com.labelwall.course.entity.InstitutionTeacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017-12-28.
 */
public interface InstitutionTeacherMapper {

    /**
     * 获取教师信息列表
     *
     * @param institutionId
     * @return
     */
    List<InstitutionTeacher> getTeacherByInstitutionId(Integer institutionId);

    /**
     * 根据主键获取对象
     *
     * @param teacherId
     * @return
     */
    InstitutionTeacher selectByPrimaryKey(Integer teacherId);

    /**
     * 获取对象集合
     *
     * @param userIdList
     * @return
     */
    List<InstitutionTeacher> selectByIds(@Param("idList")List<Integer> userIdList);
}
