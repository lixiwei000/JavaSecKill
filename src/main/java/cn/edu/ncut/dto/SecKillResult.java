package cn.edu.ncut.dto;

/**
 * 所有ajax请求返回类型,封装json结果
 * 需要这一样一个VO来封装Json结果
 * @author NikoBelic
 * @create 16/6/28 12:18
 */
public class SecKillResult<T>
{
    private boolean success;
    private T data;
    private String error;

    public SecKillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SecKillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
