package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;

public interface UserService {
    UserDetailsDto add(UserDetailsDto userDTO);

    UserDetailsDto getByName(String username);

    UserDetailsDto getAuthenticatedUser();
}
