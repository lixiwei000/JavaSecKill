package cn.edu.ncut.service;

import cn.edu.ncut.dao.SecKillDao;
import cn.edu.ncut.dao.SuccessKilledDao;
import cn.edu.ncut.dto.Exposer;
import cn.edu.ncut.dto.SecKillExecution;
import cn.edu.ncut.enums.SecKillStateEnum;
import cn.edu.ncut.exception.RepeatKillException;
import cn.edu.ncut.exception.SecKillCloseException;
import cn.edu.ncut.exception.SecKillException;
import cn.edu.ncut.model.SecKillObj;
import cn.edu.ncut.model.SuccessKilledObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @author NikoBelic
 * @create 16/6/15 00:06
 */
//@Component 组件统称,一般不知道是什么类型的类时候用
@Service
public class SecKillServiceImpl implements SecKillService
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //@Resource,@Inject 这两个是JE22规范注解
    private SecKillDao secKillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    // MD5盐值字符串,用于混淆MD5
    private final String slat = "ZheShiYiGeFuZaDeYanZhi";

    public List<SecKillObj> getSecKillList()
    {
        return secKillDao.queryAll(0,4);
    }

    public SecKillObj getById(long seckillId)
    {
        return secKillDao.queryById(seckillId);
    }

    public Exposer exportSecKillUrl(long seckillId)
    {
        SecKillObj secKill = secKillDao.queryById(seckillId);
        if (secKill == null)
            return new Exposer(false,seckillId);
        Date startTime = secKill.getStartTime();
        Date endTime = secKill.getEndTime();

        // 系统当前时间
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime())
        {
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        // 转换特定字符串的过程,不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private String getMD5(long seckillId)
    {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 使用注解控制事务方法的优点:
     * 1:开发团队达成一致约定,明确标注事务方法的编程风格
     * 2:保证事务方法的执行之间尽可能短,不要穿插其他的网络操作,RPC/Http请求,或者剥离到事务方法外部
     * 3:不是所有的方法都需要事务,如只有一条修改操作,只读操作不需要事务控制
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SecKillException
     * @throws RepeatKillException
     * @throws SecKillCloseException
     */
    @Transactional
    public SecKillExecution executeSecKill(long seckillId, long userPhone, String md5) throws SecKillException, RepeatKillException, SecKillCloseException
    {
        if (md5 == null || !md5.equals(getMD5(seckillId)))
        {
            throw new SecKillException("SecKill Data Rewrite");
        }
        // 执行秒杀逻辑: 减库存 + 记录购买行为
        Date nowTime = new Date();
        int updateCount = secKillDao.reduceNumber(seckillId,nowTime);
        try
        {


            if (updateCount <= 0)
            {
                // 没有更新到记录,秒杀结束
                throw new SecKillCloseException("SecKill Is Closed");
            } else
            {
                // 减库存成功,记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                // 唯一 联合主键
                if (insertCount <= 0)
                {
                    // 重复秒杀
                    throw new RepeatKillException("SecKill Repeated");
                } else
                {
                    // 秒杀成功
                    SuccessKilledObj successKilled = successKilledDao.queryByIdWithSecKill(seckillId, userPhone);
                    return new SecKillExecution(seckillId, SecKillStateEnum.SUCCESS, successKilled);
                }
            }
        }
        catch (SecKillCloseException e1)
        {
            throw e1;
        }
        catch (RepeatKillException e2)
        {
            throw e2;
        }
        catch (Exception e)
        {
            //logger.error(e.getMessage(),e);
            // 所有编译器异常转换为运行期异常
            throw new SecKillException("SecKill Inner Error:" + e.getMessage());
            // 跑出RunTimeException后,Spring会进行Rollback
        }
    }
}
