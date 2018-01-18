package com.labelwall.course.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.course.dto.InstitutionDto;
import com.labelwall.course.entity.Institution;
import com.labelwall.course.service.IInstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017-12-29.
 */
@RestController
@RequestMapping("/institution/")
public class InstitutionController {

    @Autowired
    private IInstitutionService institutionService;

    /**
     * 获取默认区域的机构，通过provinceId,cityId
     *
     * @return
     */
    @RequestMapping(value = "recommend", method = RequestMethod.GET)
    public ResponseObject<List<Institution>> getRecommend() {
        return institutionService.getRecommed();
    }


    /**
     * 分页获取机构
     *
     * @param institutionDto
     * @return
     */
    @RequestMapping(value = "get_institutions/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResponseObject<PageInfo> getInstitutions(InstitutionDto institutionDto,
                                                    @PathVariable("pageNum") Integer pageNum,
                                                    @PathVariable("pageSize") Integer pageSize) {
        return institutionService.getInstitutions(institutionDto, pageNum, pageSize);
    }

    /**
     * 获取够一个机构详情
     *
     * @param institutionId
     * @return
     */
    @RequestMapping(value = "get_detail/{institutionId}", method = RequestMethod.GET)
    public ResponseObject<Institution> getInstitution(@PathVariable("institutionId") Integer institutionId) {
        return institutionService.getInstitution(institutionId);
    }
}
