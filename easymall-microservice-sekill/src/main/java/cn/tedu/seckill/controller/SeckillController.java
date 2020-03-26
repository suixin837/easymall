package cn.tedu.seckill.controller;

import cn.tedu.seckill.service.SecService;
import com.jt.common.pojo.Seckill;
import com.jt.common.utils.RabbitmqUtils;
import com.jt.common.vo.SysResult;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JQ
 */
@RestController
@RequestMapping("seckill/manage/")
public class SeckillController {

	@Autowired
	private SecService secService;

	/**
	 * 查询seckill表格所有内容
	 * @return List<Seckill>
	 */
	@RequestMapping("list")
	public List<Seckill> queryAll(){
		return secService.queryAll();
	}

	/**
	 * 秒杀商品的详情
	 * @param seckillId
	 * @return Seckill
	 */
	@RequestMapping("detail")
	public Seckill detail(Long seckillId){
		return secService.queryById(seckillId);
	}

	/**
	 * 开始秒杀发送消息
	 */
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private RabbitTemplate restTemplate;
	@RequestMapping("{seckillId}")
	public SysResult startSeckill(@PathVariable Long seckillId){
		//验证登录
		try{
			//随机生成用户手机号
			String phone = "1836666" + RandomUtils.nextInt(1000,9999);
			String msg = phone +"/"+seckillId;
					rabbitTemplate.convertAndSend(
							RabbitmqUtils.EX,RabbitmqUtils.routingKey,msg);
					return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, "", null);
		}
	}
}