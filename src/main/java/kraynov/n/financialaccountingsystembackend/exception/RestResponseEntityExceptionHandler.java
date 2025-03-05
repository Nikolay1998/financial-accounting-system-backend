package kraynov.n.financialaccountingsystembackend.exception;

import kraynov.n.financialaccountingsystembackend.to.ErrorResponseTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String INTERNAL_SERVER_TEXT = "Internal server error, please try again later.";
    public static final String NOT_ENOUGH_FUNDS_TEXT = "Not enough funds to process the transaction. Check your input or consider setting overdraft property on some of your nodes.";
    public static final String FORBIDDEN_OPERATION_TEXT = "You don't have permission to perform this action.";
    public static final String SESSION_HAS_EXPIRED = "Your session has expired, please re-login.";
    public static final String USERNAME_ALREADY_IN_USE = "Provided username already in use.";
    public static final String INVALID_OPERATION_FORMAT = "Can't execute operation, reason: %s.";

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    protected ResponseEntity<Object> handleUsernameAlreadyInUseException(UsernameAlreadyInUseException ex, WebRequest request) {
        log.info("UsernameAlreadyInUseException on request: {}, {}", request, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponseTO(USERNAME_ALREADY_IN_USE));
    }

    @ExceptionHandler(NotAuthenticatedException.class)
    protected ResponseEntity<Object> handleNotAuthenticated(NotAuthenticatedException ex, WebRequest request) {
        log.warn("NotAuthenticatedException on request: {}, {}", request, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseTO(SESSION_HAS_EXPIRED));
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    protected ResponseEntity<Object> handleForbiddenOperationException(ForbiddenOperationException ex, WebRequest request) {
        log.warn("ForbiddenOperationException on request: {}, {}", request, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponseTO(FORBIDDEN_OPERATION_TEXT));
    }


    @ExceptionHandler(InsufficientFundsException.class)
    protected ResponseEntity<Object> handleInsufficientFundsException(InsufficientFundsException ex, WebRequest request) {
        log.info("InsufficientFundsException on request: {}, {}", request, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponseTO(NOT_ENOUGH_FUNDS_TEXT));
    }

    @ExceptionHandler(InvalidOperationException.class)
    protected ResponseEntity<Object> handleInvalidOperationException(InvalidOperationException ex, WebRequest request) {
        log.info("InvalidOperationException on request: {}, {}", request, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponseTO(
                        String.format(INVALID_OPERATION_FORMAT, ex.reason()))
                );
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<Object> handleGeneralException(RuntimeException ex, WebRequest request) {
        log.error("Internal error on request: {}", request, ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseTO(INTERNAL_SERVER_TEXT));
    }
}
