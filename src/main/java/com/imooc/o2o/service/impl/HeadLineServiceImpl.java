package com.imooc.o2o.service.impl;
/*
 * Created by wxn
 * 2018/9/19 13:03
 */


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.HeadLineDao;
import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.service.HeadLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {

	@Autowired
	private HeadLineDao headLineDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	JedisUtil.Strings jedisStrings;
	private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);


	@Override
	@Transactional
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
		String key = HLLISTKEY;
		List<HeadLine> headLineList = null;
		ObjectMapper mapper = new ObjectMapper();
		if (headLineCondition!=null && headLineCondition.getEnableStatus()!=null){
			key = key + "_" + headLineCondition.getEnableStatus();
		}
		if (!jedisKeys.exists(key)){
			headLineList = headLineDao.queryHeadLine(headLineCondition);
			String jsonString;
			jsonString = mapper.writeValueAsString(headLineList);
			jedisStrings.set(key,jsonString);
		}else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
			headLineList = mapper.readValue(jsonString,javaType);
		}
		return headLineList;
	}
}
