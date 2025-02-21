package kraynov.n.financialaccountingsystembackend.security;

import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;

public interface ContextHolderFacade {

    UserDetailsDto getAuthenticatedUser();

    UserDetailsDto getAuthenticatedUserOrThrowException();
}
