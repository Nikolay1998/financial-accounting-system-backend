package kraynov.n.financialaccountingsystembackend.logger;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

public class SimpleRequestLoggingFilter extends AbstractRequestLoggingFilter {

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return logger.isDebugEnabled();
    }

    @Override
    public void beforeRequest(
            HttpServletRequest request,
            String message
    ) {
        // do nothing
    }

    @Override
    public void afterRequest(
            HttpServletRequest request,
            String message
    ) {
        logger.trace(message);
    }

}
