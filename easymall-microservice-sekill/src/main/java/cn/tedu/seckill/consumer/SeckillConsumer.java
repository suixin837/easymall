package cn.tedu.seckill.consumer;

import cn.tedu.seckill.mapper.SecMapper;
import cn.tedu.seckill.mapper.SucMapper;
import com.jt.common.pojo.Success;
import com.jt.common.utils.RabbitmqUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author JQ
 */
@Component
public class SeckillConsumer {

    @Resource
    private SecMapper mapper;
    @Resource
    private SucMapper sucMapper;
    @Resource
    private JedisCluster cluster;

    @RabbitListener(queues = RabbitmqUtils.queue)
    public void processMsg(String msg) {
        System.out.println("消费者的消息是"+msg);
        if (!cluster.exists("seckilllist")||cluster.rpop("seckilllist")==null){
            System.out.println("商品秒杀完毕");
            return;
        }
        String phone = msg.split("/")[0];
        long seckillId = Long.parseLong(msg.split("/")[1]);
        //根据id判断是否有库存,并且判断时间是否符合
        Date nowTime = new Date();
        Integer result = mapper.updateNumber(seckillId,nowTime);
        if (result == 0){
            //库存修改失败
            System.out.println("shibaile");
            return;
        }else {
            //秒杀成功，增加订单
            Success success = new Success();
            success.setCreateTime(nowTime);
            success.setSeckillId(seckillId);
            success.setUserPhone(phone);
            success.setState(0);
            sucMapper.saveSuc(success);
        }

    }

}
