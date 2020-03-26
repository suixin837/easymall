package cn.tedu.search.controller;

import cn.tedu.search.service.SearchProService;
import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜索控制器
 * @author JQ
 */
@RestController
@RequestMapping("search/manage")
public class SearchController {

	/**
	 * 注入已经创建好的TransportClient
	 */
	@Resource
	private TransportClient search;
	@Resource
	private SearchProService service;

	/**
	 * 将商品信息存到ES中进行相应分词处理
	 * @return String
	 */
	@RequestMapping("create")
	public String createEmIndex() {
		try {
			service.createIndex();
			return "ok";
		}catch (Exception e){
			System.err.println(e.getMessage());
			return "fail";
		}
	}
	/**
	 * 根据productName实现商品的数据查询
	 * @param page
	 * @param rows
	 * @param q
	 * @return List<Product>
	 * @throws Exception
	 */
	@RequestMapping("query")
	public List<Product> queryProduct(Integer page,Integer rows,@RequestParam(value="query")String q) throws Exception{
		//生成query对象
		MatchQueryBuilder query = 
			QueryBuilders.matchQuery("productName", q);
		//调用客户端的search方法,将query传递,将分页条件传递
		//调整起始位置
		int start=(page-1)*rows;
		SearchResponse response = search.prepareSearch("emindex").setQuery(query)
		.setFrom(start).setSize(rows).get();
		//获取响应中的hits对象
		SearchHits hits = response.getHits();
		System.out.println("一共查到"+hits.getTotalHits());
		List<Product> pList= new ArrayList<>();
		//循环遍历查到的结果
		for (SearchHit hit : hits) {
			//封装一个product对象,获取hit中的source字符串
			String json=hit.getSourceAsString();
			//product的json字符串
			Product product= MapperUtil.MP.readValue(json, Product.class);
			pList.add(product);
		}
		return pList;
	}
}
