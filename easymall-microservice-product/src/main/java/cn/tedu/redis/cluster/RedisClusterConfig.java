package cn.tedu.redis.cluster;

/**
 * 每个configuration的代码,都对应xml一部分配置
 *
 * @author admin
 */
/*
@Configuration
@ConfigurationProperties(prefix="spring.redis.cluster")
public class RedisClusterConfig {
	private List<String> nodes;
	private Integer maxTotal;
	private Integer maxIdle;
	private Integer minIdle;
	
	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

	public Integer getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	@Bean//初始化方法构造一个jedisCluster对象
	public JedisCluster redisInit(){
		try{
			Set<HostAndPort> set=new HashSet<HostAndPort>();
			for (String node : nodes) {
				//"10.9.39.13:8000",解析ip和port
				String host=node.split(":")[0];
				int port=Integer.parseInt(node.split(":")[1]);
				set.add(new HostAndPort(host,port));
			}
			//利用其它属性,编写config对象
			JedisPoolConfig config=new JedisPoolConfig();
			config.setMaxTotal(maxTotal);
			config.setMaxIdle(maxIdle);
			config.setMinIdle(minIdle);
			return new JedisCluster(set,config);
		}catch(Exception e){
			//说明构造过程出现了一些问题,一般是因为没有提供
			//redis相关配置
			System.out.println("由于配置文件没有正确生成jedisCluster对象");
			return null;
		}
	}
}
*/
