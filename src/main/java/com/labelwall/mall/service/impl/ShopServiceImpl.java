package com.labelwall.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dao.ShopMapper;
import com.labelwall.mall.dto.ShopDto;
import com.labelwall.mall.service.IShopService;
import com.labelwall.util.storage.QiniuStorage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-06.
 */
@Service("shopService")
public class ShopServiceImpl implements IShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public ResponseObject<PageInfo> getShopList(ShopDto shopDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShopDto> shopDtoList = shopMapper.getShopList(shopDto);
        if (CollectionUtils.isNotEmpty(shopDtoList)) {
            //加载店主的头像
            for (ShopDto item : shopDtoList) {
                if (StringUtils.isNotBlank(item.getUserDto().getHead())) {
                    item.getUserDto().setHead(QiniuStorage.getUserHeadUrl(item.getUserDto().getHead()));
                } else {
                    item.getUserDto().setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
                }
            }
        }
        PageInfo pageInfo = new PageInfo(shopDtoList);
        return ResponseObject.successStautsData(pageInfo);
    }

    @Override
    public ResponseObject<ShopDto> getShopDetail(Integer shopId) {
        if (shopId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        ShopDto shopDto = shopMapper.selectByPrimaryKey(shopId);
        if (shopDto != null) {
            return ResponseObject.successStautsData(shopDto);
        }
        return ResponseObject.fail(ResponseStatus.FAIL.getCode(),ResponseStatus.FAIL.getValue());
    }
}
