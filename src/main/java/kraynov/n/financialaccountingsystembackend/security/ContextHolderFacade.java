package kraynov.n.financialaccountingsystembackend.security;

import kraynov.n.financialaccountingsystembackend.model.UserDetailsDto;

public interface ContextHolderFacade {

    UserDetailsDto getAuthenticatedUser();

    UserDetailsDto getAuthenticatedUserOrThrowException();
}
