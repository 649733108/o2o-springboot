package com.imooc.o2o.config.dao;
/*
 * Created by wxn
 * 2018/11/26 20:09
 */

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class SessionFactoryConfiguration {

	//mybatis-config.xml配置文件的路径
	private static String myBatisConfigFile ;
	//mybatis mapper文件所在路径
	private static String mapperPath ;

	@Value("${mybatis_config_file}")
	public void setMyBatisConfigFile(String myBatisConfigFile) {
		SessionFactoryConfiguration.myBatisConfigFile = myBatisConfigFile;
	}

	@Value("${mapper_path}")
	public void setMapperPath(String mapperPath) {
		SessionFactoryConfiguration.mapperPath = mapperPath;
	}

	//实体类所在package
	@Value("${type_alias_package}")
	private String typeAliasPackage ;

	@Autowired
	private DataSource dataSource;

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		//设置mybatis configuration扫描路径
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(myBatisConfigFile));
		//添加mapper扫描路径
		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
		String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+mapperPath;
		sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));
		//设置DataSource
		sqlSessionFactoryBean.setDataSource(dataSource);
		//设置typeAlias包扫描路径
		sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage);
		return sqlSessionFactoryBean;
	}
}
