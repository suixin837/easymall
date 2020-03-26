package cn.tedu.service;

import cn.tedu.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import com.jt.common.utils.UUIDUtil;
import com.jt.common.vo.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductService {
    @Resource
    private ProductMapper mapper;
    @Autowired
    private JedisCluster jedis;
    public EasyUIResult productPageQuery(Integer page, Integer rows)  {
        EasyUIResult result = new EasyUIResult();
        //查询总条数
        Integer total =mapper.queryTotal();
        //进行分页处理
        //1.确定起始位置
        Integer start = (page-1)*rows;
        List<Product> list = mapper.queryByPage(start,rows);
        result.setTotal(total);
        result.setRows(list);
        return result;
    }

    public Product queryById(String productId) {
        //将product序列化成String的json字符串,使用jackson的api
        ObjectMapper objectMapper= MapperUtil.MP;
        //创建一个连接对象使用来操作redis缓存技术
        //ShardedJedis jedis = pool.getResource();
        //生成当前业务逻辑的key值,业务信息+productId
        String productKey="product_"+productId;
        try{
            //判断是否有锁
            if (jedis.exists(productKey+".lock")){
                return mapper.queryProductById(productId);
            }
            //判断缓存数据是否存在
            if(jedis.exists(productKey)){//表示可以使用缓存
                //Map<String, String> productMap = jedis.hgetAll(productKey);
                //JSON字符串解析反序列化
                String json=jedis.get(productKey);
                return objectMapper.readValue(json, Product.class);
            }else{
                //商品key不存在,需要访问数据库,将数据存放redis
                Product product=mapper.queryProductById(productId);
                //cong product对象映射到json字符串 writeValueAsString"":""
                String json=objectMapper.writeValueAsString(product);
                //将json存储到redis中
                jedis.set(productKey, json);
                jedis.expire(productKey,60*60*24*2);
                return product;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }/*finally {
            jedis.close();
        }*/
    }

    public void saveProduct(Product product) {
        product.setProductId(UUIDUtil.getUUID().toString());
        mapper.productSave(product);
    }

    public void updateProduct(Product product) {
        //更新之前解锁
        String lock = "product_" + product.getProductId() + ".lock";
        //将锁设置到Redis,并设置超时时间与商品剩余时间一致
        Long leftTime = jedis.ttl("product_" + product.getProductId());
        jedis.setex(lock, Math.toIntExact(leftTime),"");
        //必须更新前删，否则万一删除失败，读的还是旧数据
        jedis.del("product_"+product.getProductId());
        mapper.productUpdate(product);
        jedis.del(lock);
    }
}
