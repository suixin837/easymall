package cn.tedu.mapper;

import com.jt.common.pojo.Cart;

import java.util.List;

public interface CartMapper {

	/**
	 * 根据用户id查询所有购物车信息
	 * @param userId
	 * @return List<Cart>
	 */
	List<Cart> queryMycart(String userId);

	/**
	 * 查询某一个购物车商品的信息
	 * @param cart
	 * @return Cart
	 */
	Cart queryCartOne(Cart cart);

	/**
	 * 更新购物车中已存在商品的数量
	 * @param exist
	 */
	void updateNum(Cart exist);

	/**
	 * 添加商品到购物车
	 * @param cart
	 */
	void addCart(Cart cart);

	/**
	 * 将商品移除购物车
	 * @param cart
	 */
	void deleteCart(Cart cart);

}
