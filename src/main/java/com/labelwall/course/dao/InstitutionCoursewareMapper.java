package com.labelwall.course.dao;


import com.labelwall.course.entity.InstitutionCourseware;

import java.util.List;

/**
 * Created by Administrator on 2017-12-27.
 */
public interface InstitutionCoursewareMapper {

    List<InstitutionCourseware> getCoursewareByInstitutionId(Integer institutionId);

}
