package com.imooc.o2o.util;
/*
 * Created by wxn
 * 2018/8/23 20:22
 */


public class PageCalculator {

	public static int calculateRowIndex(int pageIndex, int pageSize) {
		return pageIndex > 0 ? (pageIndex - 1) * pageSize : 0;
	}
}
