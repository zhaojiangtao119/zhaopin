package com.labelwall.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dao.ProductMapper;
import com.labelwall.mall.dto.ProductDto;
import com.labelwall.mall.service.IProductCategoryService;
import com.labelwall.mall.service.IProductService;
import com.labelwall.util.storage.QiniuStorage;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private IProductCategoryService productCategoryService;

    @Override
    public ResponseObject<PageInfo> getProductList(ProductDto productDto, Integer pageNum, Integer pageSize) {
        if (StringUtils.isBlank(productDto.getKeyword())) {
            productDto.setKeyword(null);
        }
        List<Integer> categoryIds = null;
        //判断categoryId
        if (productDto.getCategoryId() != null) {
            categoryIds = productCategoryService.getCategoryAndChildrenIdByCategoryId(productDto.getCategoryId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<ProductDto> productDtoList = productMapper.getProductList(productDto, categoryIds);
        //TODO 商品图片的转化,只转化主图
        productDtoList.stream().filter(item -> StringUtils.isNotBlank(item.getMainImage())).forEach(item -> {
            String imgUrl = QiniuStorage.getUrl(item.getMainImage());
            item.setMainImage(imgUrl);
        });
        PageInfo pageInfo = new PageInfo(productDtoList);
        return ResponseObject.successStautsData(pageInfo);
    }

    @Override
    public ResponseObject<ProductDto> getProductDetail(Integer productId) {
        if (productId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        ProductDto productDto = productMapper.selectByPrimaryKey(productId);
        if (productDto != null) {
            if (StringUtils.isNotBlank(productDto.getMainImage())) {
                String imgUrl = QiniuStorage.getUrl(productDto.getMainImage());
                productDto.setMainImage(imgUrl);
            }
            return ResponseObject.successStautsData(productDto);
        }
        return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
    }
}
