package com.imooc.o2o.config.dao;
/*
 * Created by wxn
 * 2018/11/26 19:56
 */

import com.imooc.o2o.util.DESUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

@Configuration
//配置mybatis的mapper扫描路径
@MapperScan("com.imooc.o2o.dao")
public class DataSourceConfiguration {
	@Value("${jdbc.driver}")
	private String jdbcDriver;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;

	/**
	 * 生成与spring-dao.xml对应的bean dataSource
	 */
	@Bean(name = "dataSource")
	public ComboPooledDataSource createDataSource() throws PropertyVetoException {
		//生成DataSource实例
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		//跟配置文件一样设置以下信息
		dataSource.setDriverClass(jdbcDriver);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUser(DESUtils.getDecryptString(jdbcUsername));
		dataSource.setPassword(DESUtils.getDecryptString(jdbcPassword));
		dataSource.setMaxPoolSize(30);
		dataSource.setMinPoolSize(10);
		dataSource.setAutoCommitOnClose(false);
		dataSource.setCheckoutTimeout(10000);
		dataSource.setAcquireRetryAttempts(2);

		return dataSource;
	}
}
