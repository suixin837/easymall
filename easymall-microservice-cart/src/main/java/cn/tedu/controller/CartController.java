package cn.tedu.controller;

import cn.tedu.service.CartService;
import com.jt.common.pojo.Cart;
import com.jt.common.vo.SysResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart/manage")
public class CartController {
    @Autowired
    private CartService cartService;
    //查询我的购物车
    @RequestMapping("query")
    public List<Cart> queryMyCart(String userId) {
        if (StringUtils.isNotEmpty(userId)){
            return cartService.queryMyCart(userId);
        }
        return null;

    }
    //新增我的购物车商品数据
    @RequestMapping("save")
    public SysResult saveCart(Cart cart) throws Exception{
        try{
            cartService.saveCart(cart);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201, e.getMessage(), null);
        }

    }
    //更新商品数量
    @RequestMapping("update")
    public SysResult updateCart(Cart cart){
        try{
            cartService.updateNum(cart);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201, e.getMessage(), null);
        }
    }
    @RequestMapping("delete")
    public SysResult deleteCart(Cart cart){
        try{
            cartService.deleteCart(cart);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201, e.getMessage(), null);
        }
    }
}
