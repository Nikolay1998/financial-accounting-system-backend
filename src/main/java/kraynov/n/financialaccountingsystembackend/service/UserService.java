package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.model.UserDTO;

public interface UserService {
    UserDTO add(UserDTO userDTO);

    UserDTO getByName(String username);

    UserDTO getAuthenticatedUser();
}
