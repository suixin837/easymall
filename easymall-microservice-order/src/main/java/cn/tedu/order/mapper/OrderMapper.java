package cn.tedu.order.mapper;

import com.jt.common.pojo.Order;

import java.util.Date;
import java.util.List;

/**
 * @author JQ
 */
public interface OrderMapper {

	/**
	 * 查询我的订单
	 * @param userId
	 * @return List<Order>
	 */
	List<Order> queryMyOrder(String userId);

	/**
	 * 订单增加
	 * @param order
	 */
	void addOrder(Order order);

	/**
	 * 删除订单
	 * @param orderId
	 */
	void deleteOrder(String orderId);

	/**
	 * @param otTime
	 */
	void deleteOTOrder(Date otTime);

}
