package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;

public interface UserDAO {
    UserDetailsDto getByName(String username);

    UserDetailsDto save(UserDetailsDto userDTO);
}
