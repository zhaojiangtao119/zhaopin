package com.labelwall.mall.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.dto.ProductCategoryDto;
import com.labelwall.mall.dto.ProductDto;
import com.labelwall.mall.service.IProductCategoryService;
import com.labelwall.mall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductCategoryService productCategoryService;
    @Autowired
    private IProductService productService;

    @RequestMapping(value = "get_category", method = RequestMethod.GET)
    public ResponseObject<List<ProductCategoryDto>>
    getCategory(@RequestParam(value = "categoryId", required = false) Integer categoryId) {
        return productCategoryService.getCategoryList(categoryId);
    }

    @RequestMapping(value = "get_category_id", method = RequestMethod.GET)
    public ResponseObject<List<Integer>>
    getCategoryByCategoryId(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        return productCategoryService.getCategoryAndChildrenByCategoryId(categoryId);
    }

    /**
     * 获取商品列表，查询条件（category_id,shop_id,keyword,price）
     *
     * @param productDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "get_product_list", method = RequestMethod.POST)
    public ResponseObject<PageInfo> getProductList(ProductDto productDto,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return productService.getProductList(productDto, pageNum, pageSize);
    }

    /**
     * 根据id获取商品详情
     * @param productId
     * @return
     */
    @RequestMapping(value = "get_product_detail", method = RequestMethod.GET)
    public ResponseObject<ProductDto> getProductDetail(@RequestParam(value = "productId") Integer productId) {
        return productService.getProductDetail(productId);
    }
}
