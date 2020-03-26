package cn.tedu.seckill.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.pojo.Seckill;

public interface SecMapper {

	List<Seckill> queryAll();

	Seckill queryById(Long seckillId);
	
	Integer updateNumber(@Param("seckillId") Long seckillId, @Param("nowTime") Date nowTime);

	void updateNumberPlus(Long seckillId);

}
