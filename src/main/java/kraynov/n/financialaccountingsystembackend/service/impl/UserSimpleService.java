package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.UserDAO;
import kraynov.n.financialaccountingsystembackend.model.UserDTO;
import kraynov.n.financialaccountingsystembackend.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;

public class UserSimpleService implements UserService {
    private final UserDAO userDAO;

    public UserSimpleService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDTO add(UserDTO userDTO) {
        if (getByName(userDTO.getUsername()) != null) {
            throw new BadCredentialsException("Provided username already in use");
        }
        return userDAO.save(userDTO);
    }

    @Override
    public UserDTO getByName(String username) {
        return userDAO.getByName(username);
    }
}
