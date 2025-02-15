package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.model.UserDetailsDto;

public interface UserDAO {
    UserDetailsDto getByName(String username);

    UserDetailsDto save(UserDetailsDto userDTO);
}
