package api.dongsheng.exception;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int status;

    public BaseException(int status) {
        this.status = status;
    }

    public BaseException(int status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public BaseException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
