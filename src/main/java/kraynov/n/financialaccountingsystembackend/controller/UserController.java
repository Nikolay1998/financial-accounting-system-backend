package kraynov.n.financialaccountingsystembackend.controller;

import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;
import kraynov.n.financialaccountingsystembackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(
            UserService userService,
            PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @CrossOrigin
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDetailsDto add(@RequestBody UserDetailsDto userDto) {
        UserDetailsDto encryptedUser = new UserDetailsDto(
                UUID.randomUUID().toString(), userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword())
        );
        return userService.add(encryptedUser);
    }

    @CrossOrigin
    @GetMapping(path = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDetailsDto authenticate() {
        return userService.getAuthenticatedUser();
    }
}
