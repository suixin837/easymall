package cn.tedu.order.controller;

import cn.tedu.order.service.OrderService;
import com.jt.common.pojo.Order;
import com.jt.common.vo.SysResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JQ
 */
@RestController
@RequestMapping("order/manage")
public class OrderController {
	@Resource
	private OrderService orderService;

	/**
	 * 查询我的订单数据
	 * @param userId
	 * @return List<Order>
	 */
	@RequestMapping("query/{userId}")
	public List<Order> queryMyOrders(@PathVariable String userId){
		return orderService.queryMyOrders(userId);
	}

	/**
	 * 订单新增
	 * @param order
	 * @return SysResult
	 */
	@RequestMapping("save")
	public SysResult saveOrder(Order order){
		try{
			orderService.saveOrder(order);
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, "", null);
		}
	}

	/**
	 * 订单删除
	 * @param orderId
	 * @return SysResult
	 */
	@RequestMapping("delete/{orderId}")
	public SysResult deleteOrder(@PathVariable String orderId){
        System.out.println(orderId);
		try{
			orderService.deleteOrder(orderId);
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, "", null);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
