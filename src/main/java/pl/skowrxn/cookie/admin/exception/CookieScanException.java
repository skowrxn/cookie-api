package pl.skowrxn.cookie.admin.exception;

public class CookieScanException extends RuntimeException {

    public CookieScanException(String message) {
        super(message);
    }

    public CookieScanException(String message, Throwable cause) {
        super(message, cause);
    }

    public CookieScanException(Throwable cause) {
        super(cause);
    }


}
