package kraynov.n.financialaccountingsystembackend.security.impl;

import kraynov.n.financialaccountingsystembackend.model.UserDTO;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SimpleContextHolderFacade implements ContextHolderFacade {
    @Override
    public UserDTO getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (UserDTO) authentication.getPrincipal();
        }
        return null;
    }
}
