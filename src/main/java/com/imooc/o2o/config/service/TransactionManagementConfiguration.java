package com.imooc.o2o.config.service;
/*
 * Created by wxn
 * 2018/11/26 21:27
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@Configuration
//首先使用注解@EnableTransactionManagement开启事务支持后
//在Service方法上添加注解@Transactional即可
@EnableTransactionManagement
public class TransactionManagementConfiguration implements TransactionManagementConfigurer {

	@Autowired
	private DataSource dataSource;

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
}
