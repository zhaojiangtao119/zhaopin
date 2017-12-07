package com.labelwall.mall.service.impl;

import com.google.common.collect.Lists;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.common.ResponseStatus;
import com.labelwall.mall.dao.ShoppingMapper;
import com.labelwall.mall.dto.ShoppingDto;
import com.labelwall.mall.entity.Shop;
import com.labelwall.mall.entity.Shopping;
import com.labelwall.mall.service.IShoppingService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-06.
 */
@Service("shoppingService")
public class ShoppingServiceImpl implements IShoppingService {

    @Autowired
    private ShoppingMapper shoppingMapper;

    @Override
    public ResponseObject<List<ShoppingDto>> getShoppingByUserId(Integer userId) {
        List<ShoppingDto> shoppingDtoList = Lists.newArrayList();
        List<Shopping> shopList = shoppingMapper.getShoppingByUserId(userId);
        if (CollectionUtils.isNotEmpty(shopList)) {
            for (Shopping item : shopList) {
                ShoppingDto dto = new ShoppingDto();
                BeanUtils.copyProperties(item, dto);
                shoppingDtoList.add(dto);
            }
        }
        return ResponseObject.successStautsData(shoppingDtoList);
    }

    @Override
    public ResponseObject<ShoppingDto> addShopping(ShoppingDto shoppingDto) {
        if (StringUtils.isBlank(shoppingDto.getReceiverName()) || StringUtils.isBlank(shoppingDto.getReceiverMobile())) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        Shopping shopping = new Shopping();
        BeanUtils.copyProperties(shoppingDto, shopping);
        int rowCount = shoppingMapper.insertSelective(shopping);
        if (rowCount > 0) {
            //新增成功
            ShoppingDto shoppingDtoNew = shoppingMapper.selectByPrimaryKey(shopping.getUserId(), shopping.getId());
            return ResponseObject.successStautsData(shoppingDtoNew);
        }
        return ResponseObject.failStatusMessage("添加失败");
    }

    @Override
    public ResponseObject<ShoppingDto> getShoppingById(Integer userId, Integer shoppingId) {
        if (shoppingId == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        ShoppingDto shoppingDto = shoppingMapper.selectByPrimaryKey(userId, shoppingId);
        if (shoppingDto != null) {
            return ResponseObject.successStautsData(shoppingDto);
        }
        return ResponseObject.failStatusMessage("获取失败");
    }

    @Override
    public ResponseObject<ShoppingDto> updateShopping(ShoppingDto shoppingDto) {
        if (StringUtils.isBlank(shoppingDto.getReceiverName()) || StringUtils.isBlank(shoppingDto.getReceiverMobile())) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        Shopping shopping = new Shopping();
        BeanUtils.copyProperties(shoppingDto,shopping);
        int rowCount = shoppingMapper.updateByPrimaryKeySelective(shopping);
        if (rowCount > 0) {
            ShoppingDto shoppingDtoNew = shoppingMapper.selectByPrimaryKey(shopping.getUserId(), shopping.getId());
            return ResponseObject.successStautsData(shoppingDtoNew);
        }
        return ResponseObject.failStatusMessage("修改失败,数据回显");
    }

    @Override
    public ResponseObject removeShopping(Integer userId, Integer shoppingId) {
        if (shoppingId == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        int rowCount = shoppingMapper.deleteByPrimaryKey(userId, shoppingId);
        if (rowCount > 0) {
            return ResponseObject.successStatusMessage("删除成功");
        }
        return ResponseObject.failStatusMessage("删除失败");
    }
}
