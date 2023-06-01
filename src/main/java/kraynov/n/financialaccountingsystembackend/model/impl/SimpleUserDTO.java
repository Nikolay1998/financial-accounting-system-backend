package kraynov.n.financialaccountingsystembackend.model.impl;

import kraynov.n.financialaccountingsystembackend.model.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class SimpleUserDTO implements UserDTO {
    private final String id;
    private final String password;
    private final String userName;

    public SimpleUserDTO(String id, String userName, String password) {
        this.id = id;
        this.password = password;
        this.userName = userName;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getId() {
        return id;
    }

}
