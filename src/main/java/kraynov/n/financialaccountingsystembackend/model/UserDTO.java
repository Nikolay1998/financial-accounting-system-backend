package kraynov.n.financialaccountingsystembackend.model;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDTO extends UserDetails {
    String getId();
}
