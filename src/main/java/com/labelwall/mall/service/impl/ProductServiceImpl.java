package com.labelwall.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dao.ProductMapper;
import com.labelwall.mall.dto.ProductDto;
import com.labelwall.mall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-06.
 */
@Service("productService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseObject<PageInfo> getProductList(ProductDto productDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductDto> productDtoList = productMapper.getProductList(productDto);
        //TODO 商品图片的转化

        PageInfo pageInfo = new PageInfo(productDtoList);
        return ResponseObject.successStautsData(pageInfo);
    }

    @Override
    public ResponseObject<ProductDto> getProductDetail(Integer productId) {
        if (productId == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        ProductDto productDto = productMapper.selectByPrimaryKey(productId);
        if (productDto != null) {
            return ResponseObject.successStautsData(productDto);
        }
        return ResponseObject.failStatusMessage("获取失败");
    }
}
