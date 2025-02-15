package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.model.UserDetailsDto;

public interface UserService {
    UserDetailsDto add(UserDetailsDto userDTO);

    UserDetailsDto getByName(String username);

    UserDetailsDto getAuthenticatedUser();
}
