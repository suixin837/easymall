package cn.tedu.order.service;

import cn.tedu.order.mapper.OrderMapper;
import com.jt.common.pojo.Order;
import com.jt.common.utils.UUIDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author JQ
 */
@Service
public class OrderService {
	@Resource
	private OrderMapper orderMapper;
	public List<Order> queryMyOrders(String userId) {
		return orderMapper.queryMyOrder(userId);
	}
	public void saveOrder(Order order) {
		//补充数据
		order.setOrderId(UUIDUtil.getUUID());
		//订单时间
		order.setOrderTime(new Date());
		//支付状态
		order.setOrderPaystate(0);
		System.out.println("订单信息："+order);
		orderMapper.addOrder(order);
	}
	public void deleteOrder(String orderId) {
		orderMapper.deleteOrder(orderId);
	}

}
