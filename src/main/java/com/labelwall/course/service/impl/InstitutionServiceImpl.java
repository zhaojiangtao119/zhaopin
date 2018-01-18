package com.labelwall.course.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.course.dao.InstitutionMapper;
import com.labelwall.course.dto.InstitutionDto;
import com.labelwall.course.entity.Institution;
import com.labelwall.course.service.IInstitutionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-26.
 */
@Service("institutionService")
public class InstitutionServiceImpl implements IInstitutionService {
    @Autowired
    private InstitutionMapper institutionMapper;

    /**
     * 推荐机构的获取5
     * @return
     */
    @Override
    public ResponseObject<List<Institution>> getRecommed() {
        List<Institution> institutionList = institutionMapper.getRecommed();
        if(CollectionUtils.isEmpty(institutionList)){
            return ResponseObject.fail(ResponseStatus.FAIL.getCode(),ResponseStatus.FAIL.getValue());
        }
        return ResponseObject.successStautsData(institutionList);
    }

    @Override
    public ResponseObject<PageInfo> getInstitutions(InstitutionDto institutionDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isBlank(institutionDto.getKeyword())){
            institutionDto.setKeyword(null);
        }
        List<Institution> institutionList = institutionMapper.getInstitutions(institutionDto);
        PageInfo pageInfo = new PageInfo(institutionList);
        return ResponseObject.successStautsData(pageInfo);
    }

    @Override
    public ResponseObject<Institution> getInstitution(Integer institutionId) {
        if(institutionId == null){
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),ResponseStatus.ERROR_PARAM.getValue());
        }
        Institution institution = institutionMapper.selectByPrimaryKey(institutionId);
        if(institution == null){
            return ResponseObject.fail(ResponseStatus.FAIL.getCode(),ResponseStatus.FAIL.getValue());
        }
        return ResponseObject.successStautsData(institution);
    }

}
