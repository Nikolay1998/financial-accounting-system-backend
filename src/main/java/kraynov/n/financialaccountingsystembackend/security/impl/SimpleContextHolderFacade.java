package kraynov.n.financialaccountingsystembackend.security.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import kraynov.n.financialaccountingsystembackend.model.UserDTO;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;

public class SimpleContextHolderFacade implements ContextHolderFacade {

    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleContextHolderFacade.class);

    @Override
    public UserDTO getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (UserDTO) authentication.getPrincipal();
        }
        return null;
    }

    @Override
    public UserDTO getAuthenticatedUserOrThrowException() {
        UserDTO userDTO = getAuthenticatedUser();
        if (userDTO == null) {
            LOGGER.error("Can't find authenticated user");
            throw new IllegalStateException("Can't find authenticated user");
        }
        return userDTO;
    }


}
