package cn.tedu.search.config;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

@Configuration
public class ESConfig {
	@Value("${spring.es.nodes:null}")
	private String nodes;
	@Value("${spring.es.clusterName:null}")
	private String clusterName;
	@Bean
	public TransportClient esInit(){
		try{
			System.err.println("================="+clusterName);
			Settings setting= Settings.builder()
					.put("cluster.name",clusterName).build();
			TransportClient client=new PreBuiltTransportClient(setting);
			//添加节点信息,连接集群操作es
			String[] node=nodes.split(",");
			for (String hostAndPort : node) {
				//解析ip地址,解析port端口使用
				String host=hostAndPort.split(":")[0];
				int port=Integer.parseInt(hostAndPort.split(":")[1]);
				TransportAddress address=
				new TransportAddress(
						InetAddress.getByName(host),port);
				client.addTransportAddress(address);
			}
			return client;
		}catch(Exception e){
			System.out.println("由于es配置导致TransportClient无法使用");
			return null;
		}
	}
}
