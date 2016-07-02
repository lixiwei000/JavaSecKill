package cn.edu.ncut.dao;

import cn.edu.ncut.model.SecKillObj;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by NikoBelic on 16/6/13.
 */
public interface SecKillDao
{
    /**
     * 减少库存
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    /**
     * 主键查询
     * @param seckillId
     * @return
     */
    SecKillObj queryById(long seckillId);

    /**
     * 限制查询
     * @param offset
     * @param limit
     * @return
     */
    List<SecKillObj> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 使用存储过程执行秒杀
     * @param paramMap
     */
    void killByProcedure(Map<String,Object> paramMap);
}
