package cn.edu.ncut.exception;

/**
 * 秒杀相关业务异常
 *
 * @author NikoBelic
 * @create 16/6/15 00:03
 */
public class SecKillException extends RuntimeException
{
    public SecKillException(String message) {
        super(message);
    }

    public SecKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
