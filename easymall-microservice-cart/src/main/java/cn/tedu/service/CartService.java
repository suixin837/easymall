package cn.tedu.service;

import cn.tedu.mapper.CartMapper;
import com.jt.common.pojo.Cart;
import com.jt.common.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartService {
	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private RestTemplate restTemplate;
	public List<Cart> queryMyCart(String userId) {
		return cartMapper.queryMycart(userId);
	}

	public void saveCart(Cart cart) throws Exception {
		//参数cart已经存在userId和productId
		//利用参数对象,进行查询,判断结果是否为空
		Cart exist=cartMapper.queryCartOne(cart);
		//说明不为空,将存在的数据中num+传递的num更新
		if(exist!=null){
			exist.setNum(exist.getNum()+cart.getNum());
			cartMapper.updateNum(exist);
		}else{//不存在数据需要补充完整car对象,需要利用httpClient
			Product product=restTemplate.getForObject("http://productservice/product/manage/item/"+cart.getProductId(), Product.class);
			if (product != null) {
				cart.setProductName(product.getProductName());
				cart.setProductImage(product.getProductImgurl());
				cart.setProductPrice(product.getProductPrice());
			}
			//调用新增方法,将购物车数据加入数据库表格
			cartMapper.addCart(cart);
		}
	}
	public void updateNum(Cart cart) {
		 cartMapper.updateNum(cart);
	}
	public void deleteCart(Cart cart) {
		cartMapper.deleteCart(cart);
		
	}

}
