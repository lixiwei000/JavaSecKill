package cn.edu.ncut.dto;

import cn.edu.ncut.enums.SecKillStateEnum;
import cn.edu.ncut.model.SuccessKilledObj;

/**
 * 封装秒杀执行后的结果
 *
 * @author NikoBelic
 * @create 16/6/14 23:57
 */
public class SecKillExecution
{
    private long seckillId;

    // 秒杀执行结果状态
    private int state;

    // 状态标识
    private String stateInfo;

    // 秒杀成功对象
    private SuccessKilledObj successKilledObj;

    public SecKillExecution(long seckillId, SecKillStateEnum stateEnum, SuccessKilledObj successKilledObj) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successKilledObj = successKilledObj;
    }

    public SecKillExecution(long seckillId, SecKillStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilledObj getSuccessKilledObj() {
        return successKilledObj;
    }

    public void setSuccessKilledObj(SuccessKilledObj successKilledObj) {
        this.successKilledObj = successKilledObj;
    }

    @Override
    public String toString() {
        return "SecKillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilledObj=" + successKilledObj +
                '}';
    }
}
