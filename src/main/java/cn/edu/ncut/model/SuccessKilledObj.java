package cn.edu.ncut.model;

import java.util.Date;

/**
 * @author NikoBelic
 * @create 16/6/13 15:07
 */
public class SuccessKilledObj
{
    private long seckillId;
    private long userPhone;
    private short status;
    private Date createTime;


    // 多对一
    private SecKillObj seckillObj;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SecKillObj getSeckillObj() {
        return seckillObj;
    }

    public void setSeckillObj(SecKillObj seckillObj) {
        this.seckillObj = seckillObj;
    }

    @Override
    public String toString() {
        return "SuccessKilledObj{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
