package cn.tedu.user.service;

import cn.tedu.user.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.pojo.User;
import com.jt.common.utils.MD5Util;
import com.jt.common.utils.MapperUtil;
import com.jt.common.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

@Service
public class UserService {
    @Autowired
    private UserMapper mapper;
    /**
     * 方式二：
     * @Autowired
     *     private ShardedJedisPool pool;
     *     ShardedJedis jedis = pool.getResource();
     */
    //方式三：通过RedisCluster集群实现
    @Autowired
    private JedisCluster cluster;

    /**
     * 查询用户是否已存在
     * @param userName
     * @return Integer
     */
    public Integer checkUsername(String userName) {
        return mapper.queryUsername(userName);
    }

    /**
     * 注册用户
     * @param user
     */
    public void userSave(User user) {
        //生成id
        user.setUserId(UUIDUtil.getUUID());
        //密码加密
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        mapper.addUser(user);
    }
    /**
     * 用户登录
     */
    public String doLogin(User user) {
        //密码调整成加密字符串
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        //方式二 ：ShardedJedis连接池实现
        //ShardedJedis jedis = pool.getResource();
        //调用持久层验证用户登录合法
        try{
            User existUser=mapper.login(user);
            if(existUser!=null){
                //登录合法,实现最多一个用户登录逻辑
                //判断当前用户userId是否有数据返回
                String existsTicket = cluster.get(existUser.getUserId());
                if(StringUtils.isNotEmpty(existsTicket)){
                    //说明已经有登录过的人了,删了顶替他
                    cluster.del(existsTicket);
                }
                //说明登录合法,生成redis的key值
                String ticket=MD5Util.md5("JT_TICKET"+existUser.getUserId()+System.currentTimeMillis());
                ObjectMapper objectMapper=new ObjectMapper();
                String json=objectMapper.writeValueAsString(ticket);
                //记录自己的登录ticket
                cluster.set(existUser.getUserId(), json);
                String userJson = MapperUtil.MP.writeValueAsString(existUser);
                //将用户数据存储到redis集群
                cluster.set(ticket, userJson);
                //设置超时时间30分钟
                cluster.expire(ticket, 60*30);
                return ticket;
            }else{
                //说明用户名密码不合法
                return "";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }/*finally {
            jedis.close();
        }*/
    }
    public String queryTicket(String ticket) {
       //ShardedJedis jedis = pool.getResource();
        //判断超时
        Long lastedTime = cluster.ttl(ticket);
        //判断超时0<latedTime<60*10
        if(lastedTime>0&&lastedTime<60*10){
            //续租操作,延长5分钟,极限逻辑
            //redis集群中对于已经删除的数据做超时没有返回结果的0
            cluster.expire(ticket, (int)(lastedTime+60*5));
        }
        return cluster.get(ticket);
    }
}
