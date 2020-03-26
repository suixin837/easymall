package cn.tedu.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jt.common.utils.RabbitmqUtils;

/**
 * @author JQ
 */
@Configuration
public class SeckillProductorConfig {
	
	/**
	 *  声明队列
	 *  @return Queue
	 */
	@Bean
	public Queue queue(){
		return new Queue(RabbitmqUtils.queue);
	}

	/**
	 * 声明交换机
	 * @return DirectExchange
	 */
	@Bean
	public DirectExchange ex(){
		return new DirectExchange(RabbitmqUtils.EX);
	}

	/**
	 * 将队列绑定到交换机上
	 * @return Binding
	 */
	@Bean
	public Binding bind(){
		return BindingBuilder.bind(queue()).to(ex())
				.with(RabbitmqUtils.routingKey);
	}
	
}
