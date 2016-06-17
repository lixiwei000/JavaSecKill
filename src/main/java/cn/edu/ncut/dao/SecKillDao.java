package cn.edu.ncut.dao;

import cn.edu.ncut.model.SecKillObj;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by NikoBelic on 16/6/13.
 */
public interface SecKillDao
{
    // 减少库存
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    // 主键查询
    SecKillObj queryById(long seckillId);

    // 限制查询
    List<SecKillObj> queryAll(@Param("offset") int offset, @Param("limit") int limit);

}
