package com.imooc.o2o.config.redis;
/*
 * Created by wxn
 * 2018/11/26 21:35
 */


import com.imooc.o2o.cache.JedisPoolWriper;
import com.imooc.o2o.cache.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {

	@Value("${redis.hostname}")
	private String hostname;
	@Value("${redis.port}")
	private int port;
	@Value("${redis.pool.maxTotal}")
	private int maxTotal;
	@Value("${redis.pool.maxIdle}")
	private int maxIdle;
	@Value("${redis.pool.maxWaitMillis}")
	private long maxWaitMillis;
	@Value("${redis.pool.testOnBorrow}")
	private boolean testOnBorrow;

	@Autowired
	private JedisPoolConfig jedisPoolConfig;
	@Autowired
	private JedisPoolWriper jedisPoolWriper;
	@Autowired
	private JedisUtil jedisUtil;

	/**
	 * 创建redis连接池
	 */
	@Bean(name = "jedisPoolConfig")
	public JedisPoolConfig createJedisPoolConfig(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

		return jedisPoolConfig;
	}

	/**
	 * 创建Redis连接池 并做相关配置
	 */
	@Bean(name = "jedisWritePool")
	public JedisPoolWriper createJedisPoolWriper(){
		JedisPoolWriper jedisPoolWriper = new JedisPoolWriper(jedisPoolConfig,hostname,port);
		return jedisPoolWriper;
	}

	/**
	 * 创建Redis工具类 封装好Redis的连接以进行相关的操作
	 */
	@Bean(name = "jedisUtil")
	public JedisUtil createJedisUtil(){
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setJedisPool(jedisPoolWriper);
		return jedisUtil;
	}

	/**
	 * Redis的key操作
	 */
	@Bean(name = "jedisKeys")
	public JedisUtil.Keys createJedisKeys(){
		return jedisUtil.new Keys();
	}

	/**
	 * Redis的String操作
	 */
	@Bean(name = "jedisStrings")
	public JedisUtil.Strings createJedisStrings(){
		return jedisUtil.new Strings();
	}

}
