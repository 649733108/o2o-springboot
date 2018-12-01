package com.imooc.o2o.service;
/*
 * Created by wxn
 * 2018/9/19 13:02
 */


import com.imooc.o2o.entity.HeadLine;

import java.io.IOException;
import java.util.List;

public interface HeadLineService {
	public static final String HLLISTKEY = "headlinelist";


	/**
	 * 根据传入的条件返回指定的头条列表
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
