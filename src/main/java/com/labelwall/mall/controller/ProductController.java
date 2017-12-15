package com.labelwall.mall.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.ProductCategoryDto;
import com.labelwall.mall.dto.ProductDto;
import com.labelwall.mall.service.IProductCategoryService;
import com.labelwall.mall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取产品的分类（一级分类和二级分类）
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_category", method = RequestMethod.GET)
    public ResponseObject<List<ProductCategoryDto>>
    getCategory(@RequestParam(value = "categoryId", required = false) Integer categoryId) {
        return productCategoryService.getCategoryList(categoryId);
    }

    /**
     * 递归获取分类的id(包括其后代id)
     *
     * @param categoryId
     * @return
     */
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
    @RequestMapping(value = "get_product_list/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResponseObject<PageInfo> getProductList(ProductDto productDto,
                                                   @PathVariable(value = "pageNum") Integer pageNum,
                                                   @PathVariable(value = "pageSize") Integer pageSize) {
        return productService.getProductList(productDto, pageNum, pageSize);
    }

    /**
     * 根据id获取商品详情
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "get_product_detail/{productId}", method = RequestMethod.GET)
    public ResponseObject<ProductDto> getProductDetail(@PathVariable(value = "productId") Integer productId) {
        return productService.getProductDetail(productId);
    }
}
