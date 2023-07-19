package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.UserDAO;
import kraynov.n.financialaccountingsystembackend.model.UserDTO;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

public class UserSimpleService implements UserService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserDAO userDAO;

    private final ContextHolderFacade contextHolderFacade;

    public UserSimpleService(UserDAO userDAO, ContextHolderFacade contextHolderFacade) {
        this.userDAO = userDAO;
        this.contextHolderFacade = contextHolderFacade;
    }

    @Override
    public UserDTO add(UserDTO userDTO) {
        LOGGER.debug("Start adding user {}", userDTO);
        if (getByName(userDTO.getUsername()) != null) {
            LOGGER.warn("Provided username {} already in use", userDTO.getUsername());
            throw new BadCredentialsException("Provided username already in use");
        }
        return userDAO.save(userDTO);
    }

    @Override
    public UserDTO getByName(String username) {
        return userDAO.getByName(username);
    }

    @Override
    public UserDTO getAuthenticatedUser() {
        return contextHolderFacade.getAuthenticatedUser();
    }
}
