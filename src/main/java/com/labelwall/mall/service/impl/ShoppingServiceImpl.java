package com.labelwall.mall.service.impl;

import com.google.common.collect.Lists;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dao.ShoppingMapper;
import com.labelwall.mall.dto.ShoppingDto;
import com.labelwall.mall.entity.Shopping;
import com.labelwall.mall.service.IOrderService;
import com.labelwall.mall.service.IShoppingService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-06.
 */
@Service("shoppingService")
public class ShoppingServiceImpl implements IShoppingService {

    @Autowired
    private ShoppingMapper shoppingMapper;
    @Autowired
    private IOrderService orderService;

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
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        Shopping shopping = new Shopping();
        BeanUtils.copyProperties(shoppingDto, shopping);
        int rowCount = shoppingMapper.insertSelective(shopping);
        if (rowCount > 0) {
            //新增成功
            ShoppingDto shoppingDtoNew = shoppingMapper.selectByPrimaryKey(shopping.getUserId(), shopping.getId());
            return ResponseObject.successStautsData(shoppingDtoNew);
        }
        return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
    }

    @Override
    public ResponseObject<ShoppingDto> getShoppingById(Integer userId, Integer shoppingId) {
        if (shoppingId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        ShoppingDto shoppingDto = shoppingMapper.selectByPrimaryKey(userId, shoppingId);
        if (shoppingDto != null) {
            return ResponseObject.successStautsData(shoppingDto);
        }
        return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
    }

    @Override
    public ResponseObject<ShoppingDto> updateShopping(ShoppingDto shoppingDto) {
        if (StringUtils.isBlank(shoppingDto.getReceiverName()) || StringUtils.isBlank(shoppingDto.getReceiverMobile())) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        Shopping shopping = new Shopping();
        BeanUtils.copyProperties(shoppingDto, shopping);
        int rowCount = shoppingMapper.updateByPrimaryKeySelective(shopping);
        if (rowCount > 0) {
            ShoppingDto shoppingDtoNew = shoppingMapper.selectByPrimaryKey(shopping.getUserId(), shopping.getId());
            return ResponseObject.successStautsData(shoppingDtoNew);
        }
        return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
    }

    @Override
    public ResponseObject removeShopping(Integer userId, Integer shoppingId) {
        if (shoppingId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        int rowCount = shoppingMapper.deleteByPrimaryKey(userId, shoppingId);
        if (rowCount > 0) {
            return ResponseObject.successStatus();
        }
        return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
    }


    @Override
    public ResponseObject updateDefaultShopping(Integer userId, Integer shoppingId, Long orderNo) {
        if (userId == null || shoppingId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        //1.先修改该用户的所有收货地址为全部不选中
        int rowCount = shoppingMapper.updateSelected(userId);
        if (rowCount > 0) {
            //2.修改选中的地址selected为1
            int rowCountTwo = shoppingMapper.updateSelectedById(userId, shoppingId);
            if (rowCountTwo > 0) {
                //3.修改指定单的配送地址
                if (orderNo != null) {
                    orderService.updateOrderShopping(orderNo, userId, shoppingId);
                }
                //修改成功
                return ResponseObject.successStatus();
            }
        }
        return ResponseObject.failStatusMessage("修改失败");
    }
}
