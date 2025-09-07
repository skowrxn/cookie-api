package pl.skowrxn.cookie.admin.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdminExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(AdminExceptionHandler.class);

    @ExceptionHandler(CookieScanException.class)
    public ProblemDetail handleCookieScanException(CookieScanException ex) {
        logger.error("Cookie scan error: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), ex.getMessage());
        problemDetail.setTitle("Cookie Scan Error");
        return problemDetail;
    }

}
