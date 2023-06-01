package kraynov.n.financialaccountingsystembackend.security;

import kraynov.n.financialaccountingsystembackend.model.UserDTO;

public interface ContextHolderFacade {
    UserDTO getAuthenticatedUser();
}
