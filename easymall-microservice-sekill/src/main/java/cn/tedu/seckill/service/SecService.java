package cn.tedu.seckill.service;

import cn.tedu.seckill.mapper.SecMapper;
import com.jt.common.pojo.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecService {
	@Autowired
	private SecMapper secMapper;
	public List<Seckill> queryAll(){
		return secMapper.queryAll();
	}
	public Seckill queryById(Long seckillId) {
		
		return secMapper.queryById(seckillId);
	}
}
