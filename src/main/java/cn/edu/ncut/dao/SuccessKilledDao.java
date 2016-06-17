package cn.edu.ncut.dao;

import cn.edu.ncut.model.SuccessKilledObj;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by NikoBelic on 16/6/13.
 */
public interface SuccessKilledDao
{
    // 添加秒杀记录
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
    // 查询秒杀记录
    SuccessKilledObj queryByIdWithSecKill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
