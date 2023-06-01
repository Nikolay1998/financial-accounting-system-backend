package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.model.UserDTO;

public interface UserDAO {
    UserDTO getByName(String username);

    UserDTO save(UserDTO userDTO);
}
