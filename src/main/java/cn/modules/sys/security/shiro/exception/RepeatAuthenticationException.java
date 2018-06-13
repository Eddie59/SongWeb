package cn.modules.sys.security.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * RepeatAuthenticationException class
 *
 * @author Administrator
 * @date
 */
public class RepeatAuthenticationException extends AuthenticationException {

    public RepeatAuthenticationException() {
        super();
    }

    public RepeatAuthenticationException(String message) {
        super(message);
    }

    public RepeatAuthenticationException(Throwable cause) {
        super(cause);
    }

    public RepeatAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
