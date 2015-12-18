package by.bsu.mobile.exception;

/**
 * Created by Викторианец on 26.11.2015.
 */
public class InvalidTariffDataException extends Exception {
    public InvalidTariffDataException() {
    }

    public InvalidTariffDataException(String message) {
        super(message);
    }

    public InvalidTariffDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTariffDataException(Throwable cause) {
        super(cause);
    }

    public InvalidTariffDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
