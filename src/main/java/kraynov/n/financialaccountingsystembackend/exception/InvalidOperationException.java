package kraynov.n.financialaccountingsystembackend.exception;

public class InvalidOperationException extends RuntimeException {
    private final String reason;

    public InvalidOperationException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public String reason() {
        return reason;
    }
}
