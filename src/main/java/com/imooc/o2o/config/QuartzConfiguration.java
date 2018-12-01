package com.imooc.o2o.config;
/*
 * Created by wxn
 * 2018/12/1 23:00
 */

import com.imooc.o2o.service.ProductSellDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfiguration {

	@Autowired
	private ProductSellDailyService productSellDailyService;
	@Autowired
	private MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean;
	@Autowired
	private CronTriggerFactoryBean productSellDailyTriggerFactory;

	@Bean(name = "jobDetailFactory")
	public MethodInvokingJobDetailFactoryBean createJobDetail(){
		MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
		jobDetailFactoryBean.setName("job_product_sell_daily_group");
		jobDetailFactoryBean.setConcurrent(false);
		jobDetailFactoryBean.setTargetObject(productSellDailyService);
		jobDetailFactoryBean.setTargetMethod("dailyCalculate");
		return jobDetailFactoryBean;
	}

	@Bean(name = "productSellDailyTriggerFactory")
	public CronTriggerFactoryBean createProductSellDailyTrigger(){
		CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
		triggerFactoryBean.setName("product_sell_daily_trigger");
		triggerFactoryBean.setGroup("job_product_sell_daily_group");
		triggerFactoryBean.setJobDetail(methodInvokingJobDetailFactoryBean.getObject());
		triggerFactoryBean.setCronExpression("0 0 0 * * ? *");
		return triggerFactoryBean;
	}

	@Bean(name = "schedulerFactory")
	public SchedulerFactoryBean createSchedulerFactory(){
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setTriggers(productSellDailyTriggerFactory.getObject());
		return schedulerFactoryBean;
	}
}
