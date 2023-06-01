package kraynov.n.financialaccountingsystembackend.controller;

import kraynov.n.financialaccountingsystembackend.model.UserDTO;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleUserDTO;
import kraynov.n.financialaccountingsystembackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/user")
public class RegistrationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @CrossOrigin
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO add(@RequestBody SimpleUserDTO userDTO) {
        UserDTO encryptedUser = new SimpleUserDTO(UUID.randomUUID().toString(), userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()));
        return userService.add(encryptedUser);
    }
}
