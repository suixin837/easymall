package cn.tedu.order.job;

import cn.tedu.order.mapper.OrderMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;



/**
 * 超时订单delete语句的调用者
 * @author admin
 *
 */
/*
public class OrderOTJob extends QuartzJobBean{
	//实现父类的方法,完成定时任务的调用
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//获取spring的上下文对象context
		ApplicationContext applicationContext=
			(ApplicationContext) context
			.getJobDetail().getJobDataMap()
		.get("applicationContext");
		//调用mapper的方法执行定时任务,任务就是删除超时订单
		//System.out.println("定时任务开始执行");
		Date otTime=new Date(new Date().getTime()
				-1000*60*60*24);
		applicationContext.getBean(OrderMapper.class)
		.deleteOTOrder(otTime);
		//System.out.println("定时任务执行完毕");
	}
	
}
*/