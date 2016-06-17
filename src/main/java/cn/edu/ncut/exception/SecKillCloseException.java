package cn.edu.ncut.exception;

/**
 * 秒杀关闭异常
 *
 * @author NikoBelic
 * @create 16/6/15 00:02
 */
public class SecKillCloseException extends SecKillException
{
    public SecKillCloseException(String message) {
        super(message);
    }

    public SecKillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
