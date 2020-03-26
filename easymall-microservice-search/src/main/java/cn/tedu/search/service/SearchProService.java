package cn.tedu.search.service;

import cn.tedu.search.mapper.SearchMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SearchProService {
    @Resource
    private SearchMapper mapper;
    @Resource
    private TransportClient client;

    public void createIndex() throws JsonProcessingException {
        //读取数据库内容
        List<Product> list = mapper.queryAll();


        //对象装华为json
        for (Product product : list) {
            String json = MapperUtil.MP.writeValueAsString(product);
            //存入索引
            IndexRequestBuilder request = client.prepareIndex("emindex", "product", product.getProductId());
            //elastic6.8.0需要封装数据如果是json数据，需要加 XContentType.JSON参数，指定参数类型
            request.setSource(json, XContentType.JSON).get();
        }
    }
}
