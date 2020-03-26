package cn.tedu.repository.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConfigurationProperties("spring.datasource")
public class DataSourceConfig {

    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private Integer maxActive;//最大连接数50
    private Integer maxIdle;//最大空闲10
    private Integer minIdle;//最小空闲5
    private Integer initialSize;//初始化连接数6

    @Bean
    @Primary //当容器中存在多个同类对象，优先使用带有该注解的
    public DataSourceConfig initDruidDataSource(){
        DataSourceConfig config = new DataSourceConfig();
        config.setDriverClassName(driverClassName);
        config.setUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaxActive(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setInitialSize(initialSize);
        return  config;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
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

    public Integer getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }
}
