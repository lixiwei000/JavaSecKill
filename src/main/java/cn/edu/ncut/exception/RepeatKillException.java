package cn.edu.ncut.exception;

/**
 * 重复秒杀异常(运行期异常)
 * @author NikoBelic
 * @create 16/6/15 00:00
 */
public class RepeatKillException extends SecKillException
{
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
