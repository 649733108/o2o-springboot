package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/11/28 2:16
 */


import com.imooc.o2o.entity.Award;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AwardDao {


	List<Award> queryAwardList(@Param("awardCondition") Award awardCondition,
							   @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	int queryAwardCount(@Param("awardCondition") Award awardCondition);

	Award queryAwardByAwardId(long awardId);

	int insertAward(Award award);

	int updateAward(Award award);

	int deleteAward(@Param("awardId") long awardId,@Param("shopId")long shopId);
}
