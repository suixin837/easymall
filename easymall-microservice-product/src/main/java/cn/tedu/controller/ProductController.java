package cn.tedu.controller;

import cn.tedu.service.ProductService;
import com.jt.common.pojo.Product;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/product/manage/")
public class ProductController {
    @Autowired
    private ProductService service;
    @RequestMapping("pageManage")
    public EasyUIResult productPageQuery(Integer page,Integer rows){
        EasyUIResult result =service.productPageQuery(page,rows);
        return  result;
    }
    //根据id查询商品
    @RequestMapping("item/{productId}")
    public Product queryById(@PathVariable String productId){
        Product product=service.queryById(productId);
        System.out.println("添加购物车信息："+product);
        return product;
    }
    //商品新增
    @RequestMapping("save")
    public SysResult saevProduct(Product product){
        try{
            service.saveProduct(product);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201, "", null);
        }
    }
    //商品更新
    @RequestMapping("update")
    public SysResult updateProduct(Product product){
        try{
            service.updateProduct(product);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201, "", null);
        }
    }
}
