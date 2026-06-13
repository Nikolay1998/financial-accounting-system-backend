package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.UserDao;
import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;
import kraynov.n.financialaccountingsystembackend.exception.UsernameAlreadyInUseException;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserSimpleService implements UserService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserDao userDao;

    private final ContextHolderFacade contextHolderFacade;

    public UserSimpleService(
            UserDao userDao,
            ContextHolderFacade contextHolderFacade
    ) {
        this.userDao = userDao;
        this.contextHolderFacade = contextHolderFacade;
    }

    @Override
    public UserDetailsDto add(UserDetailsDto userDto) {
        LOGGER.debug("Start adding user {}", userDto);
        if (getByName(userDto.getUsername()) != null) {
            throw new UsernameAlreadyInUseException(String.format("Username %s already in use", userDto.getUsername()));
        }
        return userDao.save(userDto);
    }

    @Override
    public UserDetailsDto getByName(String username) {
        return userDao.getByName(username);
    }

    @Override
    public UserDetailsDto getAuthenticatedUser() {
        return contextHolderFacade.getAuthenticatedUser();
    }
}
