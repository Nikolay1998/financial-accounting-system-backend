package kraynov.n.financialaccountingsystembackend.security.impl;

import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;
import kraynov.n.financialaccountingsystembackend.exception.NotAuthenticatedException;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SimpleContextHolderFacade implements ContextHolderFacade {

    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleContextHolderFacade.class);

    @Override
    public UserDetailsDto getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (UserDetailsDto) authentication.getPrincipal();
        }
        return null;
    }

    @Override
    public UserDetailsDto getAuthenticatedUserOrThrowException() {
        UserDetailsDto userDTO = getAuthenticatedUser();
        if (userDTO == null) {
            throw new NotAuthenticatedException("Can't find authenticated user");
        }
        return userDTO;
    }


}
