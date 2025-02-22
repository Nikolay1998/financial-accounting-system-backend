package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.UserDAO;
import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;
import kraynov.n.financialaccountingsystembackend.exception.UsernameAlreadyInUseException;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserSimpleService implements UserService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserDAO userDAO;

    private final ContextHolderFacade contextHolderFacade;

    public UserSimpleService(UserDAO userDAO, ContextHolderFacade contextHolderFacade) {
        this.userDAO = userDAO;
        this.contextHolderFacade = contextHolderFacade;
    }

    @Override
    public UserDetailsDto add(UserDetailsDto userDTO) {
        LOGGER.debug("Start adding user {}", userDTO);
        if (getByName(userDTO.getUsername()) != null) {
            throw new UsernameAlreadyInUseException(String.format("Username %s already in use", userDTO.getUsername()));
        }
        return userDAO.save(userDTO);
    }

    @Override
    public UserDetailsDto getByName(String username) {
        return userDAO.getByName(username);
    }

    @Override
    public UserDetailsDto getAuthenticatedUser() {
        return contextHolderFacade.getAuthenticatedUser();
    }
}
