package com.imooc.o2o.service;
/*
 * Created by wxn
 * 2018/10/31 2:35
 */


public interface CacheService {
	/**
	 * 依据key前缀删除匹配该模式下的所有key-value
	 * 如传入shopCategory,则shopCategory_allfirstlevel等以shopCategory打头的
	 * key-value都会删除
	 */
	void removeFromCache(String keyPrefix);
}
