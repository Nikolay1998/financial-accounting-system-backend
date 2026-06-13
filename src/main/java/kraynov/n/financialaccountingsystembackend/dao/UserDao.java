package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;

public interface UserDao {
    UserDetailsDto getByName(String username);

    UserDetailsDto save(UserDetailsDto userDto);
}
