package com.github.damian_git_99.backend.user;

import com.github.damian_git_99.backend.security.AuthenticatedUser;
import com.github.damian_git_99.backend.user.dto.UserRequest;
import com.github.damian_git_99.backend.user.dto.UserResponse;
import com.github.damian_git_99.backend.user.models.User;
import com.github.damian_git_99.backend.user.exceptions.UserNotFoundException;
import com.github.damian_git_99.backend.user.services.UserService;
import com.github.damian_git_99.backend.utils.BindingResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    ResponseEntity<Map<String, Object>> signUp(@RequestBody @Valid UserRequest request
            , BindingResult result) {
        if (result.hasErrors()) {
            var error =  BindingResultUtils.getFirstError(result);

            return ResponseEntity
                    .badRequest()
                    .body(error);
        }

        userService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/info")
    ResponseEntity<UserResponse> getUserDetails(Authentication authentication) {
        AuthenticatedUser  auth = (AuthenticatedUser) authentication.getPrincipal();

        User user = userService.findById(auth.getId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        UserResponse userResponse = UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        return ResponseEntity.ok(userResponse);
    }

}
