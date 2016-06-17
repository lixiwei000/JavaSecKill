package cn.edu.ncut.service;

import cn.edu.ncut.dto.Exposer;
import cn.edu.ncut.dto.SecKillExecution;
import cn.edu.ncut.exception.RepeatKillException;
import cn.edu.ncut.exception.SecKillCloseException;
import cn.edu.ncut.exception.SecKillException;
import cn.edu.ncut.model.SecKillObj;

import java.util.List;

/**
 * 业务接口:站在 使用者 角度设计接口
 * 三个方面:方法定义力度;参数;返回类型
 * Created by NikoBelic on 16/6/14.
 */
public interface SecKillService
{
    /**
     * 查询秒杀记录
     * @return
     */
    List<SecKillObj> getSecKillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    SecKillObj getById(long seckillId);

    /**
     * 秒杀开启时,输出秒杀接口的地址.否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSecKillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SecKillExecution executeSecKill(long seckillId, long userPhone, String md5)
            throws SecKillException,RepeatKillException,SecKillCloseException;
}
